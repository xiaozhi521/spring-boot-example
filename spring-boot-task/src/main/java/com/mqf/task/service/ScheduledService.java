package com.mqf.task.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @ClassName ScheduledService
 * @Description TODO
 * @Author mqf
 * @Date 2019/3/15 15:13
 */
@Service
public class ScheduledService {

    /**
     *  second , minute , hour, day of month , month , day of week
     *  0 * * * * MON-FRI
     *  【0 0/5 14 18 * * ?】 每天14点整和18点整，每隔5分钟执行一次
     *  【0 15 10 ? * 1-6】 每个月的周一到周六10点15分执行一次
     *  【0 0 2 ? * 6L】 每个月的最后一个周六2点执行一次
     *  【0 0 2 LW * ?】 每个与的最后一个工作日2点执行一次
     *  【0 0 2-4 ? * 1#1】 每个月的第一个周一2点到4点，每个整点执行一次
     */
//    @Scheduled(cron = "0 * * * * MON-FRI") //每分钟执行一次
//    @Scheduled(cron = "0,1,2,3,4 * * * * MON-FRI") //每分钟的0到4秒执行
//    @Scheduled(cron = "0-4 * * * * MON-FRI") // 每分钟的0到4秒执行
    @Scheduled(cron = "0/4 * * * * MON-FRI") // 每4秒钟执行一次
    public void hello(){
        System.out.println("hello,Spring boot...");
    }
}
