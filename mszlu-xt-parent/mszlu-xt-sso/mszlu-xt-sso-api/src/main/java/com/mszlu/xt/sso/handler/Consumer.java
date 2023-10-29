package com.mszlu.xt.sso.handler;

import com.mszlu.xt.sso.dao.mongo.data.UserLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@RocketMQMessageListener(topic = "xt_log_sso_login",consumerGroup = "login_group")
@Slf4j
@Component
public class Consumer implements RocketMQListener<UserLog> {
    @Override
    public void onMessage(UserLog message) {
        log.info("接收到的消息:{}",message);
    }
}
