package com.mszlu.xt.sso.mq;

import com.mszlu.xt.sso.dao.mongo.data.UserLog;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RocketMQTest {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Test
    public void testSend() {
        UserLog userLog = new UserLog();
        userLog.setNewer(true);
        userLog.setSex(1);
        userLog.setUserId(1000L);
        userLog.setLastLoginTime(System.currentTimeMillis());
        userLog.setRegisterTime(System.currentTimeMillis());
        //同步发送，同时发送的消息会自动转为JSON字符串
        rocketMQTemplate.convertAndSend("xt_log_sso_login", userLog);
    }
}
