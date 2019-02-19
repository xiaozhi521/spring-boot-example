package com.mqf.study;

import com.mqf.study.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName RabbitMQTest
 * @Description TODO
 * @Author li hongzhi
 * @Date 2019/2/18 15:27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMQTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Test
    public void createdExchange(){
        //创建exchange
//        amqpAdmin.declareExchange(new DirectExchange("amqpAdmin.exchange"));

//        amqpAdmin.declareQueue(new Queue("amqpAdmin.queue",true));

        amqpAdmin.declareBinding(new Binding("amqpAdmin.queue",Binding.DestinationType.QUEUE,"amqpAdmin.exchange","amqpAdmin",null));
    }

    /**
     *  单点，点对点
     */
    @Test
    public void contextLoads() {
        Map map = new HashMap();
        map.put("math",100);
        map.put("name","mqf");
        map.put("sex","男");
        rabbitTemplate.convertAndSend("exchange.direct","mqf.news",map);
    }

    //接收数据
    @Test
    public void receive(){
        Object object = rabbitTemplate.receiveAndConvert("mqf.news");
        System.out.println(object.getClass());
        System.out.println(object);
    }

    //广播
    @Test
    public void fanout(){
        rabbitTemplate.convertAndSend("exchange.fanout","",userService.getUserById(1L));
    }
}
