#### 日志的高级功能 [日志文档](https://docs.spring.io/spring-boot/docs/2.2.0.BUILD-SNAPSHOT/reference/html/spring-boot-features.html#boot-features-logging)
logback.xml 默认被日志框架识别，不能被springboot框架识别，需要将文件名更改为**logback-spring.xml**.


[**logback-spring.xml**](https://docs.spring.io/spring-boot/docs/2.2.0.BUILD-SNAPSHOT/reference/html/spring-boot-features.html#boot-features-logback-extensions): 
```xml
    <layout class="ch.qos.logback.classic.PatternLayout">
            <springProfile name="dev">
                <pattern>
                    %d{yyyy-MM-dd HH:mm:ss.SSS}  ---> [%thread] %-5level %logger{50} - %msg%n
                </pattern>
            </springProfile>
            <springProfile name="active">
                <pattern>
                    %d{yyyy-MM-dd HH:mm:ss.SSS}  ==== [%thread] %-5level %logger{50} - %msg%n
                </pattern>
            </springProfile>
        </layout>
```
**logback.xml**
```xml
    <layout class="ch.qos.logback.classic.PatternLayout">
        <pattern>
            %d{yyyy-MM-dd HH:mm:ss.SSS}  ---> [%thread] %-5level %logger{50} - %msg%n
        </pattern>
    </layout>
```