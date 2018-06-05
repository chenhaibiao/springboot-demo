package com.springboot.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消费者
 *
 * @author hb
 * @create 2018-06-05 13:39
 **/
@Component
@RabbitListener(queues = "hello")
public class MsgConsumer {

    private static final Logger logger = LoggerFactory.getLogger(MsgConsumer.class);

    @RabbitHandler
    public void process(String msg) {
        logger.debug("msg: {}", msg);
    }
}
