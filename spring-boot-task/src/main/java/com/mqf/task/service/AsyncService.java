package com.mqf.task.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @ClassName AsyncService
 * @Description TODO
 * @Author mqf
 */
@Component
public class AsyncService {

    //异步注解
    @Async
    public void hello(){
        try {
            Thread.sleep(3000);
        }catch (Exception e){
            System.out.println(e);
        }
        System.out.println("异步数据处理。。。。");
    }
}
