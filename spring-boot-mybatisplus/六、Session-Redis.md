##  Session—redis 解决分布式系统中session共享问题

### 1、添加maven
```xml
<!-- redis session -->
<dependency>
    <groupId>org.springframework.session</groupId>
    <artifactId>spring-session-data-redis</artifactId>
</dependency>
```
### 2.1、使用 application.properties 方式
```properties
# SPRING SESSION REDIS (RedisSessionProperties)
# Session store type.
spring.session.store-type=redis
#java.time.Duration
## Session timeout. If a duration suffix is not specified, seconds will be used.
server.servlet.session.timeout=2592000ms
## 清理过期的回话
spring.session.redis.cleanup-cron=0 * * * * *
## session 刷新模式
spring.session.redis.flush-mode=on_save
## 用于存储 session 的名称空间
spring.session.redis.namespace=spring:session
```
### 2.2 使用 java 配置类方式
maxInactiveIntervalInSeconds: 设置Session失效时间，使用Redis Session之后，原Boot的server.session.timeout属性不再生效
```java
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 864000*30)
public class RedisSessionConfig{

}
```

**2.1与2.2两种方式都配置，启动程序2.2方式生效！**

