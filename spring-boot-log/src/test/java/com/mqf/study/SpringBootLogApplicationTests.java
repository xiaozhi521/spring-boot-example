package com.mqf.study;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootLogApplicationTests {

	Logger logger = LoggerFactory.getLogger(getClass());
	@Test
	public void contextLoads() {
		//F:\MavenLibrary\repository\org\springframework\boot\spring-boot\2.1.1.RELEASE\spring-boot-2.1.1.RELEASE.jar!\org\springframework\boot\logging\logback\base.xml
		//日志的级别由低到高	trace > debug > info > warn > error
		logger.trace("这是trace日志。。。");
		logger.debug("这是 debug 日志。。。");
		//Spring boot 默认是info 级别,没有指定级别就用SpringBoot的默认规定的级别：root 级别
		logger.info("这是 info 日志。。。");
		logger.warn("这是 warn 日志。。。");
		logger.error("这是 error 日志。。。");

	}

}

