### 1、开启基于注解的缓存

    @EnableCaching

### 2、标注缓存注解

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

            		
### SpringBoot 整合Rdis

