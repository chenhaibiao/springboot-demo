package com.springboot.mq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 生产者
 *
 * @author hb
 * @create 2018-06-05 13:38
 **/
@Component
public class MsgProducer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(final String msg) {
        amqpTemplate.convertAndSend("hello", msg);
    }
}
