package com.mqf.study.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @ClassName RedisSessionConfig
 * @Description TODO
 * @Author mqf
 * @Date 2019/3/15 13:23
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 864000*30)
public class RedisSessionConfig{

}
