package com.mqf.study.service.impl;

import com.mqf.study.beans.User;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserRabbitServiceImpl
 * @Description TODO
 * @Author li hongzhi
 * @Date 2019/2/18 16:47
 */
@Service
public class UserRabbitServiceImpl {

    @RabbitListener(queues = {"mqf.news"})
    public void userReceive(User user) {
        System.out.println("监听 RabbitMQ 发送的消息：" + user);
    }

    @RabbitListener(queues = {"mqf"})
    public void userReceive2(Message message) {
        System.out.println("监听 RabbitMQ 发送的消息：" + message);
    }
}
