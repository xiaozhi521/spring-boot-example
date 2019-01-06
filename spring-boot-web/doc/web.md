### 一、SpringBoot 自动配置原理
    
    xxxxAutoConfiguration：帮我们给容器中自动配置组件；
    xxxxProperties:配置类来封装配置文件的内容；
    
### 二、SpringBoot 对静态资源的映射规则
1) 、所有 /webjars/** ，都去 classpath:/META-INF/resources/webjars/ 找资源；== webjars：以jar包的方式引入静态资源；
[http://www.webjars.org/](http://www.webjars.org/)
```java
    WebMvcAuotConfiguration：
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!this.resourceProperties.isAddMappings()) {
            logger.debug("Default resource handling disabled");
            return;
        }
        Integer cachePeriod = this.resourceProperties.getCachePeriod();
        if (!registry.hasMappingForPattern("/webjars/**")) {
            customizeResourceHandlerRegistration(
                    registry.addResourceHandler("/webjars/**")
                            .addResourceLocations(
                                    "classpath:/META-INF/resources/webjars/")
                    .setCachePeriod(cachePeriod));
        }
        String staticPathPattern = this.mvcProperties.getStaticPathPattern();
        //静态资源文件夹映射
        if (!registry.hasMappingForPattern(staticPathPattern)) {
            customizeResourceHandlerRegistration(
                    registry.addResourceHandler(staticPathPattern)
                            .addResourceLocations(
                                    this.resourceProperties.getStaticLocations())
                    .setCachePeriod(cachePeriod));
        }
    }
```
2) 、"/**" 访问当前项目的任何资源，都去（静态资源的文件夹）找映射 == 
```xml
    "classpath:/META-INF/resources/", 
    "classpath:/resources/",
    "classpath:/static/", 
    "classpath:/public/" 
    "/"：当前项目的根路径
```
localhost:8080/hello ===  去静态资源文件夹里面找hello

```java
    //静态资源文件夹映射
    if (!registry.hasMappingForPattern(staticPathPattern)) {
        customizeResourceHandlerRegistration(
                registry.addResourceHandler(staticPathPattern)
                        .addResourceLocations(
                                this.resourceProperties.getStaticLocations())
                .setCachePeriod(cachePeriod));
    }
```

3) 、欢迎页； 静态资源文件夹下的所有index.html页面；被"/**"映射；== localhost:8080/   找index页面
```java
    //配置欢迎页映射
    @Bean
    public WelcomePageHandlerMapping welcomePageHandlerMapping(ResourceProperties resourceProperties) {
        return new WelcomePageHandlerMapping(resourceProperties.getWelcomePage(),
                this.mvcProperties.getStaticPathPattern());
    }
```
4) 、所有的 **/favicon.ico  都是在静态资源文件下找
```java
    //配置喜欢的图标
    @Configuration
    @ConditionalOnProperty(value = "spring.mvc.favicon.enabled", matchIfMissing = true)
    public static class FaviconConfiguration {

        private final ResourceProperties resourceProperties;

        public FaviconConfiguration(ResourceProperties resourceProperties) {
            this.resourceProperties = resourceProperties;
        }

        @Bean
        public SimpleUrlHandlerMapping faviconHandlerMapping() {
            SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
            mapping.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
            //所有  **/favicon.ico 
            mapping.setUrlMap(Collections.singletonMap("**/favicon.ico",
                    faviconRequestHandler()));
            return mapping;
        }

        @Bean
        public ResourceHttpRequestHandler faviconRequestHandler() {
            ResourceHttpRequestHandler requestHandler = new ResourceHttpRequestHandler();
            requestHandler
                    .setLocations(this.resourceProperties.getFaviconLocations());
            return requestHandler;
        }
    }
```

### 三、模板引擎
JSP、Velocity、Freemarker、Thymeleaf
SpringBoot推荐的Thymeleaf；语法简单，功能强大；
#### 1、引入thymeleaf
```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>
    <!-- 切换thymeleaf版本  -->
    <properties>
		<thymeleaf.version>3.0.9.RELEASE</thymeleaf.version>
		<!-- 布局功能的支持程序  thymeleaf3主程序  layout2以上版本 -->
		<!-- thymeleaf2   layout1-->
		<thymeleaf-layout-dialect.version>2.2.2</thymeleaf-layout-dialect.version>
  </properties>
```
#### 2、Thymeleaf使用
```java
    @ConfigurationProperties(prefix = "spring.thymeleaf")
    public class ThymeleafProperties {

        private static final Charset DEFAULT_ENCODING = Charset.forName("UTF-8");
    
        private static final MimeType DEFAULT_CONTENT_TYPE = MimeType.valueOf("text/html");
    
        public static final String DEFAULT_PREFIX = "classpath:/templates/";
    
        public static final String DEFAULT_SUFFIX = ".html";
        //只要我们把HTML页面放在classpath:/templates/，thymeleaf就能自动渲染；
    }
```







