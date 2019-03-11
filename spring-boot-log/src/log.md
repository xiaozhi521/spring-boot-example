日志的级别由低到高	trace > debug > info > warn > error
Spring boot 默认是info 级别,没有指定级别就用SpringBoot的默认规定的级别：root 级别
日志门面： SLF4J；
日志实现：Logback；
**SpringBoot选用 SLF4j和logback；**
### 使用 slf4j 
#### 1、系统使用 [SLF4j](https://logback.qos.ch/manual/index.html)
以后开发的时候，日志记录方法的调用，不应该来直接调用日志的实现类，而是调用日志抽象层里面的方法；
给系统里面导入slf4j的jar和 logback的实现jar 
```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWorld1 {

  public static void main(String[] args) {

    Logger logger = LoggerFactory.getLogger("chapters.introduction.HelloWorld1");
    logger.debug("Hello world.");

  }
}
```

**如何让系统中所有的日志都统一到slf4j?**
1、将系统中其他日志框架先排除出去；
2、用中间包来替换原有的日志框架；
3、我们导入slf4j其他的实现

#### 2、SpringBoot日志关系
```xml
        <dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter</artifactId>
			<version>2.1.1.RELEASE</version>
		</dependency>
		
```
SpringBoot使用它来做日志功能:
```xml
        <dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-logging</artifactId>
            <version>2.1.1.RELEASE</version>
        </dependency>
```
#### 3、切换日志框架
slf4j+log4j的方式:
```xml
<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
			<exclusions>
			    <exclusion>
			      	<artifactId>logback‐classic</artifactId>
			      	<groupId>ch.qos.logback</groupId>
			    </exclusion>
			    <exclusion>
			      	<artifactId>log4j‐over‐slf4j</artifactId>
					<groupId>org.slf4j</groupId>
			    </exclusion>
		  	</exclusions>
		</dependency>

		<dependency>
			  <groupId>org.slf4j</groupId>
			  <artifactId>slf4j‐log4j12</artifactId>
		</dependency>
```
切换为log4j2
```xml
<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring‐boot‐starter‐web</artifactId>
            <exclusions>
                <exclusion>
                    <artifactId>spring‐boot‐starter‐logging</artifactId>
                    <groupId>org.springframework.boot</groupId>
                </exclusion>
            </exclusions>
        </dependency>

		<dependency>
			  <groupId>org.springframework.boot</groupId>
			  <artifactId>spring-boot-starter-log4j2</artifactId>
		</dependency>
```

#### 4、日志的高级功能 [日志文档](https://docs.spring.io/spring-boot/docs/2.2.0.BUILD-SNAPSHOT/reference/html/spring-boot-features.html#boot-features-logging)
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