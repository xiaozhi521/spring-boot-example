SpringBoot Task



[TOC]




# 一、Async： 异步任务
## 1、AsyncService.java 方法名上加 @Async 注解

```java
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
```
## 2、 AsyncController.java

```java
@RestController
public class AsyncController {

   @Autowired
   AsyncService asyncService;

   @GetMapping("/hello")
   public String hello() {
       asyncService.hello();
       return "success";
   }
}
```

## 3、开启异步 @EnableAsync

```java
@EnableAsync  //开启异步
@SpringBootApplication
public class SpringBootTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootTaskApplication.class, args);
    }

}
```

# 二、定时任务

Spring 提供了异步执行任务调度的方式，提供 TaskExcetor、TaskScheduler 接口。
两个注解 @EnableScheduling 、 @Scheduled
cron 表达式：

| 字段 |        允许值         |  允许的特殊符号  |
| :--: | :-------------------: | :--------------: |
|  秒  |         0-59          |     ， - * /     |
|  分  |         0-59          |     ， - * /     |
| 小时 |         0-23          |     ， - * /     |
| 日期 |         1-31          | ， - * ? / L W C |
| 月份 |         1-12          |     ， - * /     |
| 星期 | 0-7或SUN-SAT 0,7是SUN | ， - * ? / L C # |

| 特殊字段 |              含义              |
| :------: | :----------------------------: |
|  **，**  |            **枚举**            |
|  **-**   |            **区间**            |
|  *****   |            **任意**            |
|  **/**   |            **步长**            |
|  **?**   |      **日、星期冲突匹配**      |
|  **L**   |            **最后**            |
|  **W**   |           **工作日**           |
|  **C**   | **和calendar联系后计算过的值** |
|  **#**   |    **星期 4#2 第2个星期4**     |

## 1、创建 ScheduledService.java类

**@Scheduled  定时任务注解**

```java
@Service
public class ScheduledService {

    /**
     *  second , minute , hour, day of month , month , day of week
     *
    @Scheduled(cron = "0/4 * * * * MON-FRI") // 每4秒钟执行一次
    public void hello(){
        System.out.println("hello,Spring boot...");
    }
}
```

## 2、添加@EnableScheduling，开启定时任务功能

```java
@EnableAsync  //开启异步任务
@EnableScheduling //开启定时任务功能
@SpringBootApplication
public class SpringBootTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootTaskApplication.class, args);
    }

}
```

