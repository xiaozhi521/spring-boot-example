package com.mqf.study;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mqf.study.mapper")
public class SpringBootMybatisplusApplication {

    private static final Logger logger = LoggerFactory.getLogger(SpringBootMybatisplusApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMybatisplusApplication.class, args);
        logger.info("========================启动完毕========================");

    }

}

