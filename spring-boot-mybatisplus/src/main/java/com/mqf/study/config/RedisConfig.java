package com.mqf.study.config;

import com.mqf.study.beans.User;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.net.UnknownHostException;

/**
 * @ClassName RedisConfig
 * @Description TODO
 * @Author mqf
 * @Date 2019/2/14 15:59
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<Object, User> myRedisTemplate(
            RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<Object, User> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<User> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<User>(User.class);
        template.setDefaultSerializer(jackson2JsonRedisSerializer);
        return template;
    }
}
