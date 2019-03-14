#### 1、Spring Loaded
Spring 官方提供的热部署程序，实现修改类文件的热部署
- 下载 Spring Loaded [项目地址](https:/github.com/spring-projects/spring-loaded)
- 运行时添加参数：
    java -javaagent:E:/idea/spring-boot-example/spring-boot-mybatisplus/lib/springloaded-1.2.5.RELEASE.jar -noverify SomeJavaClass

#### 2、JRebel   
- 收费的一个热部署软件
- 安装插件使用即可

#### 3、Spring Boot Devtools （官府推荐）
- 引入依赖
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <optional>true</optional>
    </dependency>
</dependencies>
```
- idea 使用 Ctrl + F9