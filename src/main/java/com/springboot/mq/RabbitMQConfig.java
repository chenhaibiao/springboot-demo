package com.springboot.mq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ配置
 *
 * @author hb
 * @create 2018-06-05 13:35
 **/
@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue queue() {
        return new Queue("hello");
    }
}
