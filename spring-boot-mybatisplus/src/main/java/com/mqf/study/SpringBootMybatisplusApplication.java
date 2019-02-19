package com.mqf.study;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan("com.mqf.study.mapper")
@EnableCaching
@EnableRabbit  //开启监听rabbitmq 内容
public class SpringBootMybatisplusApplication {

    private static final Logger logger = LoggerFactory.getLogger(SpringBootMybatisplusApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMybatisplusApplication.class, args);
        logger.info("========================启动完毕========================");

    }

}

