package com.mqf.study.config;


import com.mqf.study.service.HelloService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {

    @Bean
    public HelloService initHelloService(){
        System.out.println("加载了helloService 组件");
        return new HelloService();
    }
}
