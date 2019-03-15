# SpringBoot   Task

### [Async： 异步任务](Async： 异步任务)
### Async： 异步任务

### Async： 异步任务
#### 1、AsyncService.java 方法名上加 @Async 注解
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
#### 2、 AsyncController.java
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

#### 3、开启异步 @EnableAsync
```java
@EnableAsync  //开启异步
@SpringBootApplication
public class SpringBootTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootTaskApplication.class, args);
    }

}
```
