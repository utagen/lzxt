package com.mszlu.xt.sso.handler;

import com.alibaba.fastjson.JSON;
import com.mszlu.xt.common.login.UserThreadLocal;
import com.mszlu.xt.common.model.BusinessCodeEnum;
import com.mszlu.xt.common.model.CallResult;
import com.mszlu.xt.sso.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;


    //1. 实现登录拦截器，需要登录才能访问的接口都进行拦截
    //2. 要从cookie中拿到对应的token
    //3. 根据token去做对应的认证，认证通过，拿到userId
    //4. 通过ThreadLocal将userId放入其中，后续的接口都可以通过threadLocal拿到userId


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("-------------login interceptor start------------");
        log.info("request uri:{}", request.getRequestURI());
        log.info("request method:{}", request.getMethod());
        log.info("-------------login interceptor end------------");

        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            returnJson(response);
            return false;
        }
        String token = null;
        for (Cookie cookie : cookies) {
            String name = cookie.getName();
            if ("t_token".equals(name)) {
                token = cookie.getValue();
            }
        }

        if (StringUtils.isBlank(token)) {
            returnJson(response);
            return false;
        }
        Long userId = tokenService.checkToken(token);
        if (userId == null) {
            returnJson(response);
            return false;
        }
        UserThreadLocal.put(userId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //用完threadLocal之后，其中的数据要删除，防止内存泄漏
        UserThreadLocal.remove();
    }

    private void returnJson(HttpServletResponse response){
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            writer = response.getWriter();
            CallResult callResult = CallResult.fail(BusinessCodeEnum.NO_LOGIN.getCode(),"您的登录已失效，请重新登录");
            writer.print(JSON.toJSONString(callResult));
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if(writer != null){
                writer.close();
            }
        }
    }
}
