---
SpringBoot Monitor 与 Management
---

[TOC]

### 前言

   - Spring Boot Actuator会自动配置所有已启用的端点以通过HTTP公开。
   - 默认约定是使用端点id作为URL路径，前缀为/actuator。
   - 例如，health暴露为 ``/actuator/health``。
   - 提示：Actuator本身支持Spring MVC，Spring WebFlux和Jersey。

     

**通过引入spring-boot-starter-actuator，可以使用Spring Boot为我们提供的准生产环境下的应用监控和管理功能。我们可以通过HTTP，JMX，SSH协议来进行操作，自动得到审计、健康及指标信息等**

### 一、整合监控和管理端点步骤

1.  引入spring-boot-starter-actuator
2. 通过http方式访问监控端点
3. 可进行shutdown（POST 提交，此端点默认关闭）

### 二、[监控和管理端点-中文文档](https://springcloud.cc/spring-boot.html#production-ready-endpoints-exposing-endpoints)（SpringBoot 2.1.3.RELEASE）

`include`属性列出了公开的端点的ID。`exclude`属性列出了不应公开的端点的ID。`exclude`属性优先于`include`属性。`include`和`exclude`属性都可以配置端点ID列表。

例如，要停止通过JMX公开所有端点并仅显示`health`和`info`端点，请使用以下属性：

```
management.endpoints.jmx.exposure.include=health,info
```

`*`可用于选择所有端点。例如，要通过HTTP公开除`env`和`beans`端点之外的所有内容，请使用以下属性：

```
management.endpoints.web.exposure.include=*
management.endpoints.web.exposure.exclude=env,beans
```



`*`在YAML中具有特殊含义，因此如果要包含（或排除）所有端点，请务必添加引号，如以下示例所示：

```
management:
  endpoints:
    web:
      exposure:
        include: "*"
```







| 序号 | 端点名           | 描述                                                         |
| :--: | :--------------- | ------------------------------------------------------------ |
|  1   | **autoconfig**   | **所有自动配置信息**                                         |
|  2   | **auditevents**  | **审计事件**                                                 |
|  3   | **beans**        | **所有Bean的信息**                                           |
|  4   | **configprops**  | **所有配置属性， 显示所有`@ConfigurationProperties`的整理列表 ** |
|  5   | **dump**         | **线程状态信息**                                             |
|  6   | **env**          | **当前环境信息**                                             |
|  7   | **health**       | **应用健康状况**                                             |
|  8   | **info**         | **当前应用信息**                                             |
|  9   | **metrics**      | **应用的各项指标**                                           |
|  10  | **threaddump **  | **执行线程转储 **                                            |
|  11  | shutdown         | 允许应用程序正常关闭                                         |
|  12  | sessions         | 允许从支持Spring Session的会话存储中检索和删除用户会话。使用Spring Session对响应式Web应用程序的支持时不可用 |
|  13  | scheduledtasks   | 显示应用程序中的计划任务                                     |
|  14  | mappings         | 显示所有`@RequestMapping`路径的整理列表                      |
|  15  | liquibase        | 显示已应用的任何Liquibase数据库迁移                          |
|  16  | loggers          | 显示和修改应用程序中记录器的配置                             |
|  17  | integrationgraph | 显示Spring Integration图表                                   |
|  18  | httptrace        | 显示HTTP跟踪信息（默认情况下，最后100个HTTP请求 - 响应交换   |
|  19  | flyway           | 显示已应用的任何Flyway数据库迁移                             |
|  20  | conditions       | 显示在配置和自动配置类上评估的条件以及它们匹配或不匹配的原因 |
|  21  | caches           | 暴露可用的缓存                                               |

如果应用程序是Web应用程序（Spring MVC，Spring WebFlux或Jersey），则可以使用以下附加端点： 

| 序号 | 端点名     | 描述                                                         |
| :--: | :--------- | :----------------------------------------------------------- |
|  1   | heapdump   | 返回`hprof`堆转储文件                                        |
|  2   | jolokia    | 通过HTTP公开JMX beans（当Jolokia在类路径上时，不适用于WebFlux） |
|  3   | logfile    | 返回日志文件的内容（如果已设置`logging.file`或`logging.path`属性）。支持使用HTTP `Range`标头来检索部分日志文件的内容 |
|  4   | prometheus | 以可由Prometheus服务器抓取的格式公开指标                     |



###  三、定制端点信息

**定制端点一般通过endpoints+端点名+属性名来设置。**

-  修改端点id（endpoints.beans.id=mybeans）
- 开启远程应用关闭功能（endpoints.shutdown.enabled=true）
-  关闭端点（endpoints.beans.enabled=false）
- 开启所需端点
  -  endpoints.enabled=false
  -  endpoints.beans.enabled=true
-  定制端点访问根路径
  - management.context-path=/manage
-  关闭http端点
  -  management.port=-1

### 四、自定义端点信息

#### 1、自定义管理端点的前缀

例如，您的应用程序可能已将`/actuator`用于其他目的。您可以使用`management.endpoints.web.base-path`属性更改管理端点的前缀，如以下示例所示：

```
management.endpoints.web.base-path=/manage
```

前言中 ``/actuator/health``  就要变为 ``/manage/health``

#### 2、要将端点映射到其他路径

可以使用`management.endpoints.web.path-mapping`属性。

以下示例将`/actuator/health`重新映射为`/healthcheck`：

```
management.endpoints.web.base-path=/
management.endpoints.web.path-mapping.health=healthcheck
```

####  3、自定义Management Server端口

使用默认HTTP端口公开管理端点是基于云的部署的明智选择。但是，如果您的应用程序在您自己的数据中心内运行，您可能更喜欢使用不同的HTTP端口公开端点。

您可以设置`management.server.port`属性以更改HTTP端口，如以下示例所示：

```
management.server.port=8081
```

####    4、配置管理特定的SSL （Secure Sockets Layer ，在传输层对网络连接进行加密 ）

配置为使用自定义端口时，还可以使用各种`management.server.ssl.*`属性为管理服务器配置自己的SSL。 

- 设置可以让主应用程序使用HTTPS时管理服务器通过HTTP可用

```
server.port=8443
server.ssl.enabled=true
server.ssl.key-store=classpath:store.jks
server.ssl.key-password=secret
management.server.port=8080
management.server.ssl.enabled=false
```

- 或者主服务器和管理服务器都可以使用SSL但具有不同的密钥库 

```
server.port=8443
server.ssl.enabled=true
server.ssl.key-store=classpath:main.jks
server.ssl.key-password=secret
management.server.port=8080
management.server.ssl.enabled=true
management.server.ssl.key-store=classpath:management.jks
management.server.ssl.key-password=secret
```

####  5、自定义管理服务器地址

您可以通过设置`management.server.address`属性来自定义管理端点可用的地址。如果您只想在内部或面向运行的网络上侦听或仅侦听来自`localhost`的连接，这样做非常有用。

**注意:仅当端口与主服务器端口不同时，才能侦听不同的地址**

以下示例`application.properties`不允许远程管理连接： 

```
management.server.port=8081
management.server.address=127.0.0.1
```

####  6、禁用HTTP端点的两种方式

##### 6.1、不想通过HTTP公开端点，可以将管理端口设置为`-1`，可以如下设置：

```xml
management.server.port=-1
```

##### 6.2、使用`management.endpoints.web.exposure.exclude`属性来实现

```
management.endpoints.web.exposure.exclude=*
```