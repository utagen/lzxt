package com.mszlu.xt.common.cache;

import com.alibaba.fastjson.JSON;
import com.mszlu.xt.common.model.CallResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Component
@Aspect
@Slf4j
public class CacheAspect {
    @Autowired
    private StringRedisTemplate redisTemplate;

    //定义切点 Cache注解所在的位置就是切点
    @Pointcut("@annotation(com.mszlu.xt.common.cache.Cache)")
    public void pt() {
    }

    //定义通知
    //代表对切点标识的方法进行前后的切面处理
    @Around("pt()")
    public Object around(ProceedingJoinPoint pjp) {
        //1. 首先构建redis key
        //2. 通过redis进行查询，如果存在，有缓存，直接返回
        //3. 如果没有，调用方法，拿到返回值，进行缓存
        try {
            Signature signature = pjp.getSignature();
            //类名
            String className = pjp.getTarget().getClass().getSimpleName();
            //调用的方法名
            String methodName = signature.getName();

            Class[] parameterTypes = new Class[pjp.getArgs().length];
            Object[] args = pjp.getArgs();

            //参数
            String params = "";
            for (int i = 0; i < args.length; i++) {
                if (args[i] != null) {
                    params += JSON.toJSONString(args[i]);
                    parameterTypes[i] = args[i].getClass();
                } else {
                    parameterTypes[i] = null;
                }
            }

            if (StringUtils.isNotEmpty(params)) {
                //加密 以防出现key过长以及字符转义获取不到的情况
                params = DigestUtils.md5Hex(params);
            }
            Method method = pjp.getSignature().getDeclaringType().getMethod(methodName, parameterTypes);

            //获取Cache注解
            Cache annotation = method.getAnnotation(Cache.class);
            //缓存过期时间
            int time = annotation.time();
            //缓存名称
            String name = annotation.name();
            //先从redis获取
            String redisKey = name + "::" + className + "::" + methodName + "::" + params;
            String cacheValue = redisTemplate.opsForValue().get(redisKey);
            if (StringUtils.isNotEmpty(cacheValue)) {
                log.info("---{}-{}走了缓存---,", className, methodName);
                CallResult callResult = JSON.parseObject(cacheValue, CallResult.class);
                return callResult;
            }
            Object proceed = pjp.proceed();
            redisTemplate.opsForValue().set(redisKey, JSON.toJSONString(proceed), time, TimeUnit.SECONDS);
            log.info("---{}-{}存入缓存---", className, methodName);
            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return CallResult.fail();
    }
}
