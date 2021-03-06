package com.mqf.study;

import com.mqf.study.bean.Person;
import com.mqf.study.bean.Person2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *  SpringBoot 的单元测试
 *
 *  可以在测试期间
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootmqfApplicationTests {

    @Autowired
    Person person;
    @Autowired
    Person2 person2;
    @Test
    public void contextLoads2() {
        System.out.println(person2);
    }
    @Test
    public void contextLoads() {
        System.out.println(person);
    }

}

