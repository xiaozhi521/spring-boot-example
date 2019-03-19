package com.mqf.study;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.internet.MimeMessage;
import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootTaskApplicationTests {


    @Autowired
    JavaMailSender javaMailSender;
    @Test
    public void contextLoads() {
        SimpleMailMessage message = new SimpleMailMessage();
        //邮件设置发送者
        message.setFrom("mu_qing_feng@163.com");
        //这是主题
        message.setSubject("施主，我看咱俩有缘！");
        //设置内容
        message.setText("<a href='https://www.uc123.com/' value='缘分送到，勿念！'>缘分送到，勿念！</a>");
        //设置发给谁
        message.setTo("839257729@qq.com");
        javaMailSender.send(message);
    }

    @Test
    public void contextLoads2() throws Exception{
        //设置复杂的邮件
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);

        //邮件设置发送者
        helper.setFrom("mu_qing_feng@163.com");
        //这是主题
        helper.setSubject("施主，我看咱俩有缘！");
        //设置内容
        helper.setText("<a href='https://www.uc123.com/' value='缘分送到，勿念！'>缘分送到，勿念！</a>");
        //设置发给谁
        helper.setTo("1160948933@qq.com");

        //设置文件
        helper.addAttachment("a.jpg",new File("D:\\图片\\a.jpg"));
        javaMailSender.send(mimeMessage);
    }
}
