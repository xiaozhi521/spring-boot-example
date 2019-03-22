## [Redis -https://docs.spring.io/spring-boot/docs/2.1.2.RELEASE/reference/htmlsingle/#boot-features-caching-provider-redis](https://docs.spring.io/spring-boot/docs/2.1.2.RELEASE/reference/htmlsingle/#boot-features-caching-provider-redis)
### 一、开启基于注解的缓存 

    @EnableCaching

### 二、标注缓存注解

- Cache ：缓存接口，定义缓存操作。实现有：RedisCache、EhCacheCache、

- ConcurrentMapCache等
- CacheManager ： 缓存管理器，管理各种缓存（Cache）组件
- **@Cacheable ： 主要针对方法配置，能够根据方法的请求参数对其结果进行缓存**
- **@CacheEvict ： 清空缓存**
- **@CachePut ：保证方法被调用，又希望结果被缓存。**
- **@EnableCaching  ： 开启基于注解的缓存**
- keyGenerator ： 缓存数据时key生成策略
- serialize ： 缓存数据时value序列化策略




#### 2.1、@Cacheable  
    将方法运行的结果进行缓存，以后再有相同的数据，直接从缓存中取出，不用调用方法。
    CacheManager 管理多个 Cache 组件，对缓存的真正CRUD 操作在 cache 组件中，每一个缓存组件有自己唯一一个名字。

##### **属性**：
    cacheNames / value : 指定缓存组件的名字；将方法的返回结果放在哪个缓存中，是数组的方式，可以指定多个
    key ： 缓存数据使用的key，可以用他来指定；默认是使用方法参数的值。spEL 表达式。与keyGenerator 二选一使用
    keyGenerator ： key的生成器，可以自己指定key的生成器的组件id；与key二选一使用
    cacheManager ：指定缓存管理器；或者cacheResolver 指定获取解析器
    condition ：指定复合条件的情况下才缓存
    unless ：否定缓存，当 unless指定的条件为 true，方法的返回值就不会缓存；可以获取到结果进行判断 ，unless =  “#result == null”
    sync ：是否使用异步
    
    
**注意：condition 的优先级高于 unless**

##### **原理**：
    1、自动配置类：CacheAutoConfiguration
    2、缓存的配置类 @Import(CacheConfigurationImportSelector.class)
    
    
```java
static class CacheConfigurationImportSelector implements ImportSelector {

        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {
            CacheType[] types = CacheType.values();
            String[] imports = new String[types.length];
            for (int i = 0; i < types.length; i++) {
                imports[i] = CacheConfigurations.getConfigurationClass(types[i]);
            }
            return imports;
        }

    }
```
        org.springframework.boot.autoconfigure.cache.GenericCacheConfiguration
        org.springframework.boot.autoconfigure.cache.JCacheCacheConfiguration
        org.springframework.boot.autoconfigure.cache.EhCacheCacheConfiguration
        org.springframework.boot.autoconfigure.cache.HazelcastCacheConfiguration
        org.springframework.boot.autoconfigure.cache.InfinispanCacheConfiguration
        org.springframework.boot.autoconfigure.cache.CouchbaseCacheConfiguration
        org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration
        org.springframework.boot.autoconfigure.cache.CaffeineCacheConfiguration
        org.springframework.boot.autoconfigure.cache.SimpleCacheConfiguration
        org.springframework.boot.autoconfigure.cache.NoOpCacheConfiguration
    3、默认配置类：SimpleCacheConfiguration
    4、给容器中注册了一个 CacheManager ： ConcurrentMapCacheManager
    

```java
@Bean
public ConcurrentMapCacheManager cacheManager() {
    ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager();
    List<String> cacheNames = this.cacheProperties.getCacheNames();
    if (!cacheNames.isEmpty()) {
        cacheManager.setCacheNames(cacheNames);
    }
    return this.customizerInvoker.customize(cacheManager);
}
```
    
    5、可以获取和创建 ConcurrentMapCache 类型的缓存组件，他的作用将数据保存在 ConcurrentMap 中

##### **运行流程**： @Cacheable
    1、方法运行之前，先去查询Cache（缓存组件），按照cacheNames 指定的名字获取；
        （CacheManager先获取相应的缓存），第一次获取缓存如果没有Cache组件会自动创建
    2、去Cache中查找缓存的内容，使用一个key，默认是方法的参数；
        key 是按照某种策略生成的： 默认是使用 keyGenerator 生成的;
        默认使用SimpleKeyGenerator生成key;
            如果没有参数：key = new SimpleKey();
            如果有一个参数： key = 参数值
            如果有多个参数： key = new SimpleKey(params);
    3、没有缓存就调用目标方法
    4、将目标方法返回的结果，放进缓存中
##### **核心**：
    1、使用CacheManager【ConcurrentMapCacheManager】按照名字得到cache【ConcurrentMap】
    2、key 使用 keyGenerator 生成的，默认使用SimpleKeyGenerator生成key
    
#### 2.1、@CacheEvict
    key : 指定要清除的数据
    allEntriese：默认 false；如果为 true，指定清除这个缓存中所有的数据
    beforeInvocation：缓存的清除是否在方法之前执行；
                    默认 false，代表缓存清除操作是在方法执行之后执行，如果出现异常，就不会清除

            		
### 三、SpringBoot 整合Rdis
#### 1、添加 maven
```xml
<!--redis-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
    <version>2.0.6.RELEASE</version>
</dependency>
```
#### 2、application.properties
```properties
#redis
#ip
spring.redis.host=127.0.0.1
#使用redis那个库
spring.redis.database=5
#端口
spring.redis.port=6379
#密码
spring.redis.password=qwertyuiop@123
#连接超时时间
spring.redis.timeout=2000ms
#最大连接数
spring.redis.jedis.pool.max-active=200
#最大空闲连接数
spring.redis.jedis.pool.max-idle=50
#获取连接时的最大等待毫秒数
spring.redis.jedis.pool.max-wait=5000ms
#最小空闲连接数
spring.redis.jedis.pool.min-idle=10
```
#### 3、SpringBootMybatisplusApplication.java 加入 @EnableCaching 注解
```java
@SpringBootApplication
@MapperScan("com.mqf.study.mapper")
@EnableCaching     //开启缓存注解
public class SpringBootMybatisplusApplication {

    private static final Logger logger = LoggerFactory.getLogger(SpringBootMybatisplusApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMybatisplusApplication.class, args);
        logger.info("========================启动完毕========================");

    }

}
```
#### 4、service 层使用缓存
##### 4.1、简单使用redis
```java
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Cacheable(cacheNames = "user",key="#id")
//    @Cacheable(/*value = "user", */ key="#id")
    public User getUserById(Long id){
        User user = new User();
        user = user.selectById(id);
        return user;
    }

    /**
     * @CachePut : 既调用方法，有更新缓存数据
     * @return
     */
    @Override
    @CachePut(value = "user",key = "#result.id")
    public User updateUser(User user) {
        baseMapper.updateById(user);
        return user;
    }

    @Override
    @CachePut(value = "user", key = "#result.id")
    public User insertUser(User user) {
        baseMapper.insert(user);
        return user;
    }

    @Override
    @CacheEvict(value = "user", key = "#id")
    public int deleteUser(Long id) {
        int delete = baseMapper.deleteById(id);
        return delete;
    }

    @Override
    //定义复杂的缓存规则
    @Caching(
        cacheable = {
            @Cacheable(value = "user",  key = "#name")
        },
        put = {
            @CachePut(value = "user", key = "#result.id"),
            @CachePut(value = "user", key = "#result.email")
        }
    )
    public User getUserByName(String name) {
        List<User> userList = baseMapper.selectList(new QueryWrapper<User>().like("name", name));
        return userList.get(0);
    }

}
```

##### 4.2、使用自定义的key
RedisConfig.java


```java
@Configuration
public class RedisConfig {

   @Bean
   public RedisTemplate<Object, User> myRedisTemplate(
           RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
       RedisTemplate<Object, User> template = new RedisTemplate<>();
       template.setConnectionFactory(redisConnectionFactory);
       Jackson2JsonRedisSerializer<User> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<User>(User.class);
       template.setDefaultSerializer(jackson2JsonRedisSerializer);
       return template;
   }
}
```

UserServiceImpl.java


```java
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    //不缓存 id 为 1 记录
    @Cacheable(value = "emp",keyGenerator = "getKeyGenerator",condition = "#id==1",unless = "#id==2")
    public User getUserById(Long id){
        User user = new User();
        user = user.selectById(id);
        return user;
    }
}
```

##### 4.3、抽取公共配置
UserServiceImpl.java
```java
@Service
@CacheConfig(cacheNames="user") //抽取公共配置
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    //不缓存 id 为 1 记录
    @Cacheable(/*value = "user", */ key="#id")
    public User getUserById(Long id){
        User user = new User();
        user = user.selectById(id);
        return user;
    }

    /**
     * @CachePut : 既调用方法，有更新缓存数据
     * @return
     */
    @Override
    @CachePut(/*value = "user", */key = "#result.id")
    public User updateUser(User user) {
        baseMapper.updateById(user);
        return user;
    }

    @Override
    @CachePut(/*value = "user", */ key = "#result.id")
    public User insertUser(User user) {
        baseMapper.insert(user);
        return user;
    }

    @Override
    @CacheEvict(/*value = "user", */key = "#id")
    public int deleteUser(Long id) {
        int delete = baseMapper.deleteById(id);
        return delete;
    }

    @Override
    //定义复杂的缓存规则
    @Caching(
        cacheable = {
            @Cacheable(/*value = "user", */ key = "#name")
        },
        put = {
            @CachePut(/*value = "user", */ key = "#result.id"),
            @CachePut(/*value = "user", */ key = "#result.email")
        }
    )
    public User getUserByName(String name) {
        List<User> userList = baseMapper.selectList(new QueryWrapper<User>().like("name", name));
        return userList.get(0);
    }

}
```


