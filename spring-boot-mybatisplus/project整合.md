# SpringBoot与MyBatisPlus整合

###  一、创建SpringBoot项目，在创建项目时直接加入 **Web** 、**MySQL** 依赖

### 二、添加必须依赖

[myBatisPlus](https://mp.baomidou.com/guide/quick-start.html#配置) 及其 [代码生成器](https://mp.baomidou.com/guide/generator.html) 选用最新版本   3.0.7.1 ,模板引擎使用 freemarker

```xml
		<!--mybatis-plus-->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.0.7.1</version>
        </dependency>
        <!-- mp 代码生成器依赖-->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-generator</artifactId>
            <version>3.0.7.1</version>
        </dependency>
        <!-- freemarker 模板引擎 -->
        <dependency>
            <groupId>org.freemarker</groupId>
            <artifactId>freemarker</artifactId>
            <version>2.3.23</version>
        </dependency>
```

完整的 pom.xml 链接 ：[https://github.com/xiaozhi521/spring-boot-example/blob/master/spring-boot-mybatisplus/pom.xml](https://github.com/xiaozhi521/spring-boot-example/blob/master/spring-boot-mybatisplus/pom.xml)

###  三、myBatisPlus 代码生成器

mysql 数据库驱动使用新版本 ： com.mysql.cj.jdbc.Driver 

mysql 数据库驱动旧版本： com.mysql.jdbc.Driver 



mySql Url 使用 ： 后缀添加 ?serverTimezone=UTC 或者 &serverTimezone=UTC，否则会抛出以下异常

```xml
java.sql.SQLException: The server time zone value 'ÖÐ¹ú±ê×¼Ê±¼ä' is unrecognized or represents more than one time zone. You must configure either the server or JDBC driver (via the serverTimezone configuration property) to use a more specifc time zone value if you want to utilize time zone support.
```



MpGenerator.java 代码

```java
package com.mqf.study.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

public class MpGenerator {
    public static void main(String[] args) {
        //1. 全局配置
        GlobalConfig config = new GlobalConfig();
        config.setActiveRecord(true) // 是否开启AR模式
                .setAuthor("mqf") // 作者
                .setOutputDir("E:\\idea\\spring-boot-example\\spring-boot-mybatisplus\\src\\main\\java") // 生成路径
                .setFileOverride(true)  // 文件覆盖
                .setIdType(IdType.AUTO) // 主键策略
                .setServiceName("%sService")  // 设置生成的service接口的名字的首字母是否为I
                // IEmployeeService
                .setBaseResultMap(true)
                .setBaseColumnList(true);
        
        //2. 数据源配置
        DataSourceConfig dsConfig  = new DataSourceConfig();
        dsConfig.setDbType(DbType.MYSQL)  // 设置数据库类型
//                .setDriverName("com.mysql.jdbc.Driver")
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setUrl("jdbc:mysql://localhost:3306/mp?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=UTC")
                .setUsername("root")
                .setPassword("");

        //3. 策略配置
        StrategyConfig stConfig = new StrategyConfig();
        stConfig.setCapitalMode(true) //全局大写命名
//                .setDbColumnUnderline(true)  // 指定表名 字段名是否使用下划线
                .setNaming(NamingStrategy.underline_to_camel) // 数据库表映射到实体的命名策略
                .setTablePrefix("")
                .setInclude("user");  // 生成的表

        //4. 包名策略配置
        PackageConfig pkConfig = new PackageConfig();
        pkConfig.setParent("com.mqf.study")
                .setMapper("mapper")
                .setService("service")
                .setController("controller")
                .setEntity("beans")
                .setXml("mapper");

        //5. 整合配置
        AutoGenerator ag = new AutoGenerator();

        ag.setGlobalConfig(config)
                .setDataSource(dsConfig)
                .setStrategy(stConfig)
                .setPackageInfo(pkConfig);
        // 切换为 freemarker 模板引擎 默认是velocity
        ag.setTemplateEngine(new FreemarkerTemplateEngine());

        //6. 执行
        ag.execute();
    }
}
```



启动代码生成器！！！

**注意：生成后一定记得在spring boot项目中添加mybatis的包扫描路径，或@Mapper注解：** 

```java
@SpringBootApplication
@MapperScan("com.mqf.study.mapper")
public class SpringBootMybatisplusApplication {
    private static final Logger logger = LoggerFactory.getLogger(SpringBootMybatisplusApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMybatisplusApplication.class, args);
        logger.info("========================启动完毕========================");
    }
}
```

或者

```java
@Mapper
public interface UserMapper extends BaseMapper<User> {}
```

为了方便，我们在spring boot项目中添加mybatis的包扫描路径。

### 四、添加SpingBoot 数据源配置

```pr
server.port=8080

#mysql
spring.datasource.url=jdbc:mysql://localhost:3306/mp?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
#mybatis-plus
mybatis-plus.mapper-locations=classpath*:com/mqf/study/mapper/*.xml  
mybatis-plus.type-aliases-package=com.mqf.study.beans
```

### 五、添加控制器

```java
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping("/{id}")
    @ResponseBody
    public User getUserById(@PathVariable("id") Integer id){
        User user = userService.getById(id);
        return user;
    }
}
```

### 六、启动项目、测试

测试地址：http://localhost:8080/user/1

测试结果： {"id":1,"name":"Jone","age":18,"email":"test1@baomidou.com"}



