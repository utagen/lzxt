package com.mszlu.xt.web.domain.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MqService {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    //延迟等级 RocketMQ不支持任意时间的延时，只支持以下几个固定的延时等级  "1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h"
    public void sendDelayedMessage(String topic,Object data,int delay) {
        MessageBuilder<Object> messageBuilder = MessageBuilder.withPayload(data);
        rocketMQTemplate.syncSend(topic, messageBuilder.build(), 3000, delay);
        log.info("发送了延时消息...{}", new DateTime().toString("HH:mm:ss"));
    }
}
