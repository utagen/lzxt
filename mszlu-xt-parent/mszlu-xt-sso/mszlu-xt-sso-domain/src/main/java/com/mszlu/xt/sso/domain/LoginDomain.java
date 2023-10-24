package com.mszlu.xt.sso.domain;

import com.mszlu.xt.common.constants.RedisKey;
import com.mszlu.xt.common.model.BusinessCodeEnum;
import com.mszlu.xt.common.model.CallResult;
import com.mszlu.xt.common.utils.JwtUtil;
import com.mszlu.xt.model.enums.LoginType;
import com.mszlu.xt.model.params.LoginParam;
import com.mszlu.xt.model.params.UserParam;
import com.mszlu.xt.sso.dao.data.User;
import com.mszlu.xt.sso.domain.repository.LoginDomainRepository;
import lombok.val;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * 专门处理和登录相关的操作
 */
public class LoginDomain {
    private LoginDomainRepository loginDomainRepository;

    private LoginParam loginParam;

    public static final String secretKey = "mszlu!@#$%xtsso&^#$#@@";

    public LoginDomain(LoginDomainRepository loginDomainRepository, LoginParam loginParam) {
        this.loginDomainRepository = loginDomainRepository;
        this.loginParam = loginParam;
    }

    public CallResult<Object> buildQrConnectUrl() {
        String url = this.loginDomainRepository.buildQrUrl();
        return CallResult.success(url);
    }

    public CallResult<Object> checkWxLoginCallBackBiz() {
        //检查state是否是合法的
        //csrf的检测
        String state = loginParam.getState();
        //去redis检测是否state为key的值存在，如果不存在，证明不合法
        boolean isVerify = loginDomainRepository.checkState(state);
        if (!isVerify) {
            return CallResult.fail(BusinessCodeEnum.CHECK_BIZ_NO_RESULT.getCode(), "参数不合法");
        }
        return CallResult.success();
    }

    public CallResult<Object> wxLoginCallBack() {
        String code = loginParam.getCode();
        try {
            //2. 下次进行登录的时候，如果refreshToken存在，可以直接获取accessToken，不需要用户重新授权
            //只有在可以获取到用户登录信息的时候才能生效,refresh_token无法用于用户登录
//            String refreshToken = loginDomainRepository.redisTemplate.opsForValue().get(RedisKey.REFRESH_TOKEN);
            String refreshToken = null;
                    WxMpOAuth2AccessToken wxMpOAuth2AccessToken = null;
                    String accessToken = null;
            if (refreshToken == null) {
                //1. 通过code获取到accessToken和refreshToken，
                wxMpOAuth2AccessToken = loginDomainRepository.wxMpService.oauth2getAccessToken(code);
                refreshToken = wxMpOAuth2AccessToken.getRefreshToken();
                String unionId = wxMpOAuth2AccessToken.getUnionId();
                // 需要保存refreshToken到redis里，过期时间设置为28天
                loginDomainRepository.redisTemplate.opsForValue().set(RedisKey.REFRESH_TOKEN + unionId, refreshToken, 28, TimeUnit.DAYS);
            } else {
                wxMpOAuth2AccessToken = loginDomainRepository.wxMpService.oauth2refreshAccessToken(refreshToken);
            }
            //3. 通过accessToken获取微信的用户信息（openid，unionId）
            WxMpUser wxMpUser = loginDomainRepository.wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, "zh_CN");
            String unionId = wxMpUser.getUnionId();
            //4. 需要判断unionId在数据库的user表中是否存在，存在就更新最后登录时间，不存在就进行注册
            User user = this.loginDomainRepository.createUserDomain(new UserParam()).findUserByUnionId(unionId);
            boolean isNew = false;
            if (user == null) {
                //注册
                user = new User();
                Long currentTime = System.currentTimeMillis();
                user.setNickname(wxMpUser.getNickname());
                user.setHeadImageUrl(wxMpUser.getHeadImgUrl());
                user.setSex(wxMpUser.getSex());
                user.setOpenid(wxMpUser.getOpenId());
                user.setLoginType(LoginType.WX.getCode());
                user.setCountry(wxMpUser.getCountry());
                user.setCity(wxMpUser.getCity());
                user.setProvince(wxMpUser.getProvince());
                user.setRegisterTime(currentTime);
                user.setLastLoginTime(currentTime);
                user.setUnionId(wxMpUser.getUnionId());
                user.setArea("");
                user.setMobile("");
                user.setGrade("");
                user.setName(wxMpUser.getNickname());
                user.setSchool("");
                this.loginDomainRepository.createUserDomain(new UserParam()).saveUser(user);
                isNew = true;
            }

            //5. 使用jwt技术，生成token，需要把token存储起来
            String token = JwtUtil.createJWT(7 * 24 * 60 * 60 * 1000, user.getId(), secretKey);
            System.out.println("存入之前的token是:" + RedisKey.TOKEN + token);
            loginDomainRepository.redisTemplate.opsForValue().set(RedisKey.TOKEN + token, String.valueOf(user.getId()), 7, TimeUnit.DAYS);
            System.out.println("生成的token是:" + token);
            //6. 因为是付费课程，所以账号只能在一端登录，如果用户在其他地方登录，需要将当前登录的用户踢下线
            String oldToken = loginDomainRepository.redisTemplate.opsForValue().get(RedisKey.LOGIN_USER_TOKEN + user.getId());
            if (oldToken != null) {
                //当前用户之前在某一个设备登录过
                loginDomainRepository.redisTemplate.delete(RedisKey.TOKEN + oldToken);
            }
            loginDomainRepository.redisTemplate.opsForValue().set(RedisKey.LOGIN_USER_TOKEN + user.getId(), token);
            //7. 把token返回给前端，存在cookie当中，下次请求的时候，从cookie中获取token
            HttpServletResponse response = loginParam.getResponse();
            Cookie cookie = new Cookie("t_token", token);
            cookie.setMaxAge(8 * 24 * 60 * 60);
            cookie.setPath("/");
            response.addCookie(cookie);
            //8. 给用户加积分，成就系统，任务系统
            //9. 需要记录日志，记录当前用户的登录行为，MQ+MongoDB进行日志记录
            //10. 更新用户的最后登录时间
            if (!isNew) {
                user.setLastLoginTime(System.currentTimeMillis());
                this.loginDomainRepository.createUserDomain(new UserParam()).updateUser(user);
            }
            return CallResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return CallResult.fail(BusinessCodeEnum.LOGIN_WX_NOT_USER_INFO.getCode(),"授权问题,无法获取用户信息");
        }
    }

    public String buildGzhUrl() {
        String url = this.loginDomainRepository.buildGzhUrl();
        return url;
    }

    public CallResult<Object> wxGzhLoginCallBack() {
        String code = loginParam.getCode();
        try {
            //2. 下次进行登录的时候，如果refreshToken存在，可以直接获取accessToken，不需要用户重新授权
            //只有在可以获取到用户登录信息的时候才能生效,refresh_token无法用于用户登录
//            String refreshToken = loginDomainRepository.redisTemplate.opsForValue().get(RedisKey.GZH_REFRESH_TOKEN);
            String refreshToken = null;
            WxMpOAuth2AccessToken wxMpOAuth2AccessToken = null;
            String accessToken = null;
            if (refreshToken == null) {
                //1. 通过code获取到accessToken和refreshToken，
                wxMpOAuth2AccessToken = loginDomainRepository.wxMpServiceGzh.oauth2getAccessToken(code);
                refreshToken = wxMpOAuth2AccessToken.getRefreshToken();
                String unionId = wxMpOAuth2AccessToken.getUnionId();
                // 需要保存refreshToken到redis里，过期时间设置为28天
                loginDomainRepository.redisTemplate.opsForValue().set(RedisKey.GZH_REFRESH_TOKEN + unionId, refreshToken, 28, TimeUnit.DAYS);
            } else {
                wxMpOAuth2AccessToken = loginDomainRepository.wxMpServiceGzh.oauth2refreshAccessToken(refreshToken);
            }
            //3. 通过accessToken获取微信的用户信息（openid，unionId）
            WxMpUser wxMpUser = loginDomainRepository.wxMpServiceGzh.oauth2getUserInfo(wxMpOAuth2AccessToken, "zh_CN");
            String unionId = wxMpUser.getUnionId();
            //4. 需要判断unionId在数据库的user表中是否存在，存在就更新最后登录时间，不存在就进行注册
            User user = this.loginDomainRepository.createUserDomain(new UserParam()).findUserByUnionId(unionId);
            boolean isNew = false;
            if (user == null) {
                //注册
                user = new User();
                Long currentTime = System.currentTimeMillis();
                user.setNickname(wxMpUser.getNickname());
                user.setHeadImageUrl(wxMpUser.getHeadImgUrl());
                user.setSex(wxMpUser.getSex());
                user.setOpenid(wxMpUser.getOpenId());
                user.setLoginType(LoginType.WX.getCode());
                user.setCountry(wxMpUser.getCountry());
                user.setCity(wxMpUser.getCity());
                user.setProvince(wxMpUser.getProvince());
                user.setRegisterTime(currentTime);
                user.setLastLoginTime(currentTime);
                user.setUnionId(wxMpUser.getUnionId());
                user.setArea("");
                user.setMobile("");
                user.setGrade("");
                user.setName(wxMpUser.getNickname());
                user.setSchool("");
                this.loginDomainRepository.createUserDomain(new UserParam()).saveUser(user);
                isNew = true;
            }

            //5. 使用jwt技术，生成token，需要把token存储起来
            String token = JwtUtil.createJWT(7 * 24 * 60 * 60 * 1000, user.getId(), secretKey);
            System.out.println("存入之前的token是:" + RedisKey.TOKEN + token);
            loginDomainRepository.redisTemplate.opsForValue().set(RedisKey.TOKEN + token, String.valueOf(user.getId()), 7, TimeUnit.DAYS);
            System.out.println("生成的token是:" + token);
            //6. 因为是付费课程，所以账号只能在一端登录，如果用户在其他地方登录，需要将当前登录的用户踢下线
            String oldToken = loginDomainRepository.redisTemplate.opsForValue().get(RedisKey.LOGIN_USER_TOKEN + user.getId());
            if (oldToken != null) {
                //当前用户之前在某一个设备登录过
                loginDomainRepository.redisTemplate.delete(RedisKey.TOKEN + oldToken);
            }
            loginDomainRepository.redisTemplate.opsForValue().set(RedisKey.LOGIN_USER_TOKEN + user.getId(), token);
            //7. 把token返回给前端，存在cookie当中，下次请求的时候，从cookie中获取token
            HttpServletResponse response = loginParam.getResponse();
            Cookie cookie = new Cookie("t_token", token);
            cookie.setMaxAge(8 * 24 * 60 * 60);
            cookie.setPath("/");
            response.addCookie(cookie);
            //8. 给用户加积分，成就系统，任务系统
            //9. 需要记录日志，记录当前用户的登录行为，MQ+MongoDB进行日志记录
            //10. 更新用户的最后登录时间
            if (!isNew) {
                user.setLastLoginTime(System.currentTimeMillis());
                this.loginDomainRepository.createUserDomain(new UserParam()).updateUser(user);
            }
            return CallResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return CallResult.fail(BusinessCodeEnum.LOGIN_WX_NOT_USER_INFO.getCode(),"授权问题,无法获取用户信息");
        }
    }
}
