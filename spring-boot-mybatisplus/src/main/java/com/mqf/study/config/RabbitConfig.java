package com.mqf.study.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName RabbitConfig
 * @Description TODO
 * @Author li hongzhi
 * @Date 2019/2/18 15:42
 */
@Configuration
public class RabbitConfig {


    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}
