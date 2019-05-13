2.安装RabbitMQ
下载运行rabbitmq-server-3.6.5 ，需要其他版本或者32位系统的，可以去官网下载。

依旧可以不改变默认进行安装。

需要注意：默认安装的RabbitMQ 监听端口是5672

3.配置
激活 RabbitMQ's Management Plugin

使用RabbitMQ 管理插件，可以更好的可视化方式查看Rabbit MQ 服务器实例的状态。

打开命令窗口：

输入命令：

    "D:\Program Files\RabbitMQ Server\rabbitmq_server-3.6.5\sbin\rabbitmq-plugins.bat" enable rabbitmq_management

这样，就安装好插件了，是不是能使用了呢？别急，需要重启服务才行，使用命令：
    
    net stop RabbitMQ && net start RabbitMQ

创建用户，密码，绑定角色

使用rabbitmqctl控制台命令（位于C:\Program Files\RabbitMQ Server\rabbitmq_server-3.6.5\sbin>）来创建用户，密码，绑定权限等。

注意：安装路径不同的请看仔细啊。

rabbitmq的用户管理包括增加用户，删除用户，查看用户列表，修改用户密码。

查看已有用户及用户的角色：

    rabbitmqctl.bat list_users

新增一个用户：

    rabbitmqctl.bat add_user username password

rabbitmq用户角色可分为五类：超级管理员, 监控者, 策略制定者, 普通管理者以及其他。

(1) 超级管理员(administrator)

可登陆管理控制台(启用management plugin的情况下)，可查看所有的信息，并且可以对用户，策略(policy)进行操作。

(2) 监控者(monitoring)

可登陆管理控制台(启用management plugin的情况下)，同时可以查看rabbitmq节点的相关信息(进程数，内存使用情况，磁盘使用情况等) 

(3) 策略制定者(policymaker)

可登陆管理控制台(启用management plugin的情况下), 同时可以对policy进行管理。

(4) 普通管理者(management)

仅可登陆管理控制台(启用management plugin的情况下)，无法看到节点信息，也无法对策略进行管理。

(5) 其他的

无法登陆管理控制台，通常就是普通的生产者和消费者。


我们也给 eric 变成 “超级管理员” 角色：

    rabbitmqctl.bat set_user_tags username administrator

当然，除了上面的administrator 还有 monitoring、policymaker、management、自定义名称 ，对应上面介绍到的不同的角色。

像我们人一样，我们角色除了是公司的员工，还是父母的孩子、子女的爸妈等，用户也可以同时具有多个角色,设置方式:

    rabbitmqctl.bat  set_user_tags  username tag1 tag2 ...


现在总觉得guest 这个不安全（它的默认密码是guest）,想更改密码，好办：

    rabbitmqctl change_password userName newPassword

有的人也许会说，我就是看guest不爽，老子新增了administrator用户了，就是想干掉它，可以：

    rabbitmqctl.bat delete_user username

使用浏览器打开 http://localhost:15672 访问Rabbit Mq的管理控制台，使用刚才创建的账号登陆系统：




Docker 中国 镜像加速 ： https://www.docker-cn.com/registry-mirror




###  SpringBoot 整合 RabbitMQ
#### 1、 引入pom.xml
   ```xml
<!-- RabbitMQ-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-amqp</artifactId>
        </dependency>
   ```
#### 2、自动配置
1) RabbitAutoConfiguration

2) 有自动配置了连接工厂 ConnectionFactory

3) RabbitProperties 封装了 rabbitmq 的配置

    ```xml
        spring.application.name=spirng-boot-rabbitmq
        spring.rabbitmq.host=localhost
        spring.rabbitmq.port=5672
        spring.rabbitmq.username=guest
        spring.rabbitmq.password=guest
    ```
4) test.java.com.mqf.study.RabbitMQTest， RabbitTemplate 给RabbitMQ 发送和接收消息

     ```java
        @Autowired
        private RabbitTemplate rabbitTemplate;
    
        @Autowired
        private UserService userService;
    
        /**
         *  单点，点对点
         */
        @Test
        public void contextLoads() {
            Map map = new HashMap();
            map.put("math",100);
            map.put("name","mqf");
            map.put("sex","男");
            rabbitTemplate.convertAndSend("exchange.direct","mqf.news",map);
        }
    
        //接收数据
        @Test
        public void receive(){
            Object object = rabbitTemplate.receiveAndConvert("mqf.news");
            System.out.println(object.getClass());
            System.out.println(object);
        }
    
        //广播
        @Test
        public void fanout(){
            rabbitTemplate.convertAndSend("exchange.fanout","",userService.getUserById(1L));
        }
     ```
5) AmqpAdmin : RabbitMQ 系统管理组件,创建和删除 Queue，Exchange，Binding

     ```java
        @Autowired
        private AmqpAdmin amqpAdmin;
    
        @Test
        public void createdExchange(){
            //创建exchange
    //        amqpAdmin.declareExchange(new DirectExchange("amqpAdmin.exchange"));
    
    //        amqpAdmin.declareQueue(new Queue("amqpAdmin.queue",true));
    
            amqpAdmin.declareBinding(new Binding("amqpAdmin.queue",Binding.DestinationType.QUEUE,"amqpAdmin.exchange","amqpAdmin",null));
        }
     ```
     
6) @RabbitListener + @EnableRabbit ：开启监听rabbitmq 内容


     ```java
        @SpringBootApplication
        @MapperScan("com.mqf.study.mapper")
        @EnableCaching
        @EnableRabbit  //开启监听rabbitmq 内容
        public class SpringBootMybatisplusApplication {}
     ```
    ````java
    @Service
    public class UserRabbitServiceImpl {
    
        @RabbitListener(queues = {"mqf.news"})
        public void userReceive(User user) {
            System.out.println("监听 RabbitMQ 发送的消息：" + user);
        }
    
        @RabbitListener(queues = {"mqf"})
        public void userReceive2(Message message) {
            System.out.println("监听 RabbitMQ 发送的消息：" + message);
        }
    }
    ````