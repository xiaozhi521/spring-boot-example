## SpringBoot 默认支持两种技术来和 ElasticSearch 交互

### 1）Jest 实现（默认不生效）
需要导入 io.searchbox.client.JestClient 工具包
```xml
<!-- https://mvnrepository.com/artifact/io.searchbox/jest -->
<dependency>
    <groupId>io.searchbox</groupId>
    <artifactId>jest</artifactId>
    <version>6.3.1</version>
</dependency>
```


### 2）SpringData ElasticSearch（系统默认实现）
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-elasticsearch</artifactId>
</dependency>
```

1) ElasticsearchAutoConfiguration（Clinet） 节点信息 clusterName ，clusterNodes
2) ElasticsearchTemplate 操作 ES
3) 编写ElasticsearchRepository 的子接口来操作 ES