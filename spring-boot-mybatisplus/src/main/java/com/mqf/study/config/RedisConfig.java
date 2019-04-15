package com.mqf.study.config;

import com.mqf.study.beans.User;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

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


    /**
     * 自定义 redis 连接
     * @return
     */
//  @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration("127.0.0.1", 6379);
        config.setDatabase(5);
        config.setPassword("qwertyuiop@123");
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(200);
//        spring.redis.jedis.pool.max-active=200
//        spring.redis.jedis.pool.max-idle=50
        jedisPoolConfig.setMaxIdle(50);
//        spring.redis.jedis.pool.max-wait=5000ms
        jedisPoolConfig.setMaxWaitMillis(5000);
//        spring.redis.jedis.pool.min-idle=10
        jedisPoolConfig.setMinIdle(5000);
        JedisConnectionFactory jedisConnectionFactory =  new JedisConnectionFactory(config);
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
        return jedisConnectionFactory;
    }
}
