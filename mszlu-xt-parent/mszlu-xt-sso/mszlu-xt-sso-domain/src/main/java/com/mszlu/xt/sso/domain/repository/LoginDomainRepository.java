package com.mszlu.xt.sso.domain.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.mszlu.xt.common.constants.RedisKey;
import com.mszlu.xt.common.wx.config.WxOpenConfig;
import com.mszlu.xt.model.params.LoginParam;
import com.mszlu.xt.model.params.UserParam;
import com.mszlu.xt.sso.dao.UserMapper;
import com.mszlu.xt.sso.dao.data.User;
import com.mszlu.xt.sso.dao.mongo.data.UserLog;
import com.mszlu.xt.sso.domain.LoginDomain;
import com.mszlu.xt.sso.domain.UserDomain;
import com.mszlu.xt.sso.domain.thread.InviteThread;
import com.mszlu.xt.sso.domain.thread.LogThread;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class LoginDomainRepository {

    @Autowired
    public StringRedisTemplate redisTemplate;
    @Autowired
    private LogThread logThread;
    @Autowired
    public WxMpService wxMpService;
    @Autowired
    public WxMpService wxMpServiceGzh;
    @Autowired
    private WxOpenConfig wxOpenConfig;
    @Autowired
    private UserDomainRepository userDomainRepository;
    @Autowired
    public InviteThread inviteThread;

    public LoginDomain createDomain(LoginParam loginParam) {
        return new LoginDomain(this, loginParam);
    }

    public boolean checkState(String state) {
        Boolean isValid = redisTemplate.hasKey(RedisKey.WX_STATE_KEY + state);
        return isValid != null && isValid;
    }

    public String buildQrUrl() {
//        String csrfKey = wxOpenConfig.getCsrfKey();
//        String time = new DateTime().toString("yyyyMMddHHmmss");
//        csrfKey = csrfKey + "_" + time;
        String csrfKey = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(RedisKey.WX_STATE_KEY + csrfKey, "1", 60, TimeUnit.SECONDS);
        //将csrfKey放入redis中，并设置有效期，在检查state是否合法时从redis中获取
        String url = wxMpService.buildQrConnectUrl(wxOpenConfig.getRedirectUrl(), wxOpenConfig.getScope(), csrfKey);
        return url;
    }

    public String buildGzhUrl() {
        String csrfKey = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(RedisKey.WX_STATE_KEY + csrfKey, "1", 60, TimeUnit.SECONDS);
        String url = wxMpServiceGzh.buildQrConnectUrl(wxOpenConfig.mobileRedirectUrl, WxConsts.OAuth2Scope.SNSAPI_USERINFO, csrfKey);
        return url;
    }
    public UserDomain createUserDomain(UserParam userParam) {
        return userDomainRepository.createDomain(userParam);
    }


    public void recordLoginLog(UserLog userLog) {
        //同步发送，同时发送的消息会自动转为JSON字符串
        //为了避免同步发送出现的问题，改为异步发送，但是可能会丢数据
        //不希望丢数据
//        log.info("记录日志开始时间:{}", new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
        logThread.recordLog("xt_log_sso_login", userLog);
    }
}
