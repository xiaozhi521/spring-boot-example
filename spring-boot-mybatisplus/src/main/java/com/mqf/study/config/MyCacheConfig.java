package com.mqf.study.config;


import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.Arrays;

@Configuration
public class MyCacheConfig {

    /**
     * {@code @Bean("customBeanName")}. 默认是方法名
     * @return
     */
    @Bean
    public KeyGenerator getKeyGenerator(){
         return new KeyGenerator(){
            @Override
            public Object generate(Object target, Method method, Object... params) {
                return method.getName() + "[" + Arrays.asList(params).toString() + "]";
            }
        };
    }
}
