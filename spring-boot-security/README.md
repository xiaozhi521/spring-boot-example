## 一、引入SpringSecurity
  ```xml
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-security</artifactId>
  </dependency>
  ```
## 二、编写SpringDecurity的配置

### 1、登录、认证、授权

继承 WebSecurityConfigurerAdapter.java ,重写 configure(HttpSecurity http) 方法

- `.permitAll()`方法允许为与基于表单的登录相关联的所有URL授予对所有用户的访问权限。 
- ``.antMatchers("/level1/**").hasRole("VIP1")`` 只有路径为 ``/level1/**``并且是``"VIP1"``的用户可访问

```java
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //定制请求的授权规则
        http.authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/level1/**").hasRole("VIP1")
                .antMatchers("/level2/**").hasRole("VIP2")
                .antMatchers("/level3/**").hasRole("VIP3");
        //开启自动配置登录功能，如果没有登录，没有权限就会来到登录页面
        http.formLogin();
        //1、/login 会来到登录页
        //2、重定向到/login?error 表示登录失败
    }
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("zhangsan").password("123456").roles("VIP1","VIP2")
//                .and()
//                .withUser("lisi").password("123456").roles("VIP2","VIP3")
//                .and()
//                .withUser("wangwu").password("123456").roles("VIP1","VIP3")
//                .and().passwordEncoder(new PasswordEncoderConfig());

        auth.inMemoryAuthentication()
                .passwordEncoder(new Pbkdf2PasswordEncoder())
                .withUser("zhangsan").password(new Pbkdf2PasswordEncoder().encode("123456")).roles("VIP1","VIP2")
                .and()
                .withUser("lisi").password(new Pbkdf2PasswordEncoder().encode("123456")).roles("VIP2","VIP3")
                .and()
                .withUser("wangwu").password(new Pbkdf2PasswordEncoder().encode("123456")).roles("VIP1","VIP3");
//        auth.userDetailsService(userDetailsService()).passwordEncoder(new Pbkdf2PasswordEncoder());
    }
}
```

####  1.1、http.formLogin() ，开启自动配置登录功能，如果没有登录，没有权限就会来到登录页面，默认借助SpringBoot自带的登录页面，访问路径：

- /login 会来到登录页
- 重定向到/login?error 表示登录失败

#### 1.2、public void configure(AuthenticationManagerBuilder auth) throws Exception {} 方法实现登录授权功能，

在 4.* + 升级到 5.0以上版本 会出现 一下错误

```
java.lang.IllegalArgumentException: There is no PasswordEncoder mapped for the id "null"
    at org.springframework.security.crypto.password.DelegatingPasswordEncoder$UnmappedIdPasswordEncoder.matches(DelegatingPasswordEncoder.java:233)
    at org.springframework.security.crypto.password.DelegatingPasswordEncoder.matches(DelegatingPasswordEncoder.java:196) 
```

##### 1.2.1、为了解决这个问题，查看[文档](https://docs.spring.io/spring-security/site/docs/5.1.4.RELEASE/reference/htmlsingle/#core-services-password-encoding), 这里给出了两种方法解决：

SecurityConfig.configure(AuthenticationManagerBuilder auth) 修改前的写法：

```java
auth.inMemoryAuthentication()
                .withUser("zhangsan").password("123456").roles("VIP1","VIP2")
                .and()
                .withUser("lisi").password("123456").roles("VIP2","VIP3")
                .and()
                .withUser("wangwu").password("123456").roles("VIP1","VIP3");
```

#### ###### 方法一、自己实现 PasswordEncoder 接口 ,PasswordEncoderConfig.java

```java
public class PasswordEncoderConfig  implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        return charSequence.toString();
}

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return s.equals(charSequence.toString());
    }
}
```

SecurityConfig.configure(AuthenticationManagerBuilder auth) 修改后的写法：

```java
auth.inMemoryAuthentication()
                .withUser("zhangsan").password("123456").roles("VIP1","VIP2")
                .and()
                .withUser("lisi").password("123456").roles("VIP2","VIP3")
                .and()
                .withUser("wangwu").password("123456").roles("VIP1","VIP3")
                .and().passwordEncoder(new PasswordEncoderConfig());
```

###### 方法二、使用官方提供的几种加密方式

**1、BCryptPasswordEncoder**

`BCryptPasswordEncoder`实现使用广泛支持的[bcrypt](https://en.wikipedia.org/wiki/Bcrypt)算法来散列密码。为了使它更能抵抗密码破解，bcrypt故意慢。与其他自适应单向函数一样，应调整大约需要1秒钟来验证系统上的密码。 

```
BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
String result = encoder.encode("myPassword");
assertTrue(encoder.matches("myPassword", result));
```

**2、Pbkdf2PasswordEncoder**

`Pbkdf2PasswordEncoder`实现使用[PBKDF2](https://en.wikipedia.org/wiki/PBKDF2)算法来散列密码。为了打败密码破解，PBKDF2是一种故意慢的算法。与其他自适应单向函数一样，应调整大约需要1秒钟来验证系统上的密码。当需要FIPS认证时，该算法是一个不错的选择。

```
// Create an encoder with all the defaults
Pbkdf2PasswordEncoder encoder = new Pbkdf2PasswordEncoder();
String result = encoder.encode("myPassword");
assertTrue(encoder.matches("myPassword", result));
```

**3、SCryptPasswordEncoder**

`SCryptPasswordEncoder`实现使用[scrypt](https://en.wikipedia.org/wiki/Scrypt)算法来散列密码。为了在自定义硬件上破解密码破解scrypt是一种故意慢的算法，需要大量内存。与其他自适应单向函数一样，应调整大约需要1秒钟来验证系统上的密码。

```
// Create an encoder with all the defaults
SCryptPasswordEncoder encoder = new SCryptPasswordEncoder();
String result = encoder.encode("myPassword");
assertTrue(encoder.matches("myPassword", result));
```

**加密之后的文件**

比未加密之前多了```.passwordEncoder(new Pbkdf2PasswordEncoder())```,密码未加密前的写法是```.password("123456")```,加密后的写法是```.password(new Pbkdf2PasswordEncoder().encode("123456"))```

```java
auth.inMemoryAuthentication()
                .passwordEncoder(new Pbkdf2PasswordEncoder())
                .withUser("zhangsan").password(new Pbkdf2PasswordEncoder().encode("123456")).roles("VIP1","VIP2")
                .and()
                .withUser("lisi").password(new Pbkdf2PasswordEncoder().encode("123456")).roles("VIP2","VIP3")
                .and()
                .withUser("wangwu").password(new Pbkdf2PasswordEncoder().encode("123456")).roles("VIP1","VIP3");
```

### 2、注销、权限控制

#### 2.1、开启自动注销配置注销功能

使用`WebSecurityConfigurerAdapter`时，会自动应用注销功能。默认情况下，访问URL `/logout`将通过以下方式记录用户：

- 使HTTP会话无效
- 清理已配置的任何RememberMe身份验证
- 清除`SecurityContextHolder`
- 重定向到`/login?logout`

SecurityConfig.configure(HttpSecurity http) 添加：

```
 //开启自动配置注销功能
http.logout();
```

welcome.html

```html
 <div>
     <form th:action="@{/logout}" method="post">
         <input type="submit" value="注销">
     </form>
</div>
```

#### 2.2、权限控制

**更改 ```pom.xml```  --> ``` spring-boot-starter-parent``` (Ctrl + 鼠标左击) --> ```spring-boot-dependencies```(Ctrl + 鼠标左击) --> 查找 ``thymeleaf`` 或者是 ``thymeleaf-extras-springsecurity``**

##### 2.2.1、引入pom.xml

```xml
<properties>
        <java.version>1.8</java.version>
        <thymeleaf-extras-springsecurity5.version>3.0.4.RELEASE</thymeleaf-extras-springsecurity5.version>
    </properties>

<dependency>
    <groupId>org.thymeleaf.extras</groupId>
    <artifactId>thymeleaf-extras-springsecurity5</artifactId>
</dependency>
```

##### 2.2.2、[查看文档](https://docs.spring.io/spring-security/site/docs/current/guides/html5/helloworld-boot.html#exploring-the-secured-application)，修改 ``welcome.html``

- 添加 `` xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity5"``
- ``sec:authentication=""``：身份验证
  - name ： 用户名	
  - principal.authorities ：权限
- ``sec:authorize="isAuthenticated()"``：身份授权
  - hasRole(str) ：拥有某个权限

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" xmlns="http://www.w3.org/1999/xhtml"
	  xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity5">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1 align="center">欢迎光临武林秘籍管理系统</h1>
<div th:fragment="logout" class="logout" sec:authorize="!isAuthenticated()">
    <h2 align="center">游客您好，如果想查看武林秘籍 <a th:href="@{/userLogin}">请登录</a></h2>
</div>
<div th:fragment="logout" class="logout" sec:authorize="isAuthenticated()">
	<h3>Logged in user: <span sec:authentication="name"></span> </h3>
	<h3>Roles: <span sec:authentication="principal.authorities"></span></h3>
    <div>
        <form th:action="@{/logout}" method="post">
            <input type="submit" value="注销">
        </form>
    </div>
</div>
<hr>

<div sec:authorize="hasRole('VIP1')">
	<h3>普通武功秘籍</h3>
	<ul>
		<li><a th:href="@{/level1/1}">罗汉拳</a></li>
		<li><a th:href="@{/level1/2}">武当长拳</a></li>
		<li><a th:href="@{/level1/3}">全真剑法</a></li>
	</ul>
</div>
<div sec:authorize="hasRole('VIP2')">
	<h3>高级武功秘籍</h3>
	<ul>
		<li><a th:href="@{/level2/1}">太极拳</a></li>
		<li><a th:href="@{/level2/2}">七伤拳</a></li>
		<li><a th:href="@{/level2/3}">梯云纵</a></li>
	</ul>
</div>
<div sec:authorize="hasRole('VIP3')">
	<h3>绝世武功秘籍</h3>
	<ul>
		<li><a th:href="@{/level3/1}">葵花宝典</a></li>
		<li><a th:href="@{/level3/2}">龟派气功</a></li>
		<li><a th:href="@{/level3/3}">独孤九剑</a></li>
	</ul>
</div>
</body>
</html>
```



### 三、记住我

```http.rememberMe();``` 开启记住我功能 

### 四、定制登录页

``http.formLogin() ``默认启动的 url 地址是``/login``，重定向到``/login?error`` 表示登录失败

#### 4.1、自定义的登录页面 路径为 ``/userLogin``

```java
/**
     * 登陆页
     * @return
     */
    @GetMapping("/userLogin")
    public String loginPage() {
        return prefix+"login";
    }
```

#### 4.2、``SecurityConfig.java``  自定义登录页面

- ```http.formLogin();``` 更改为 ```http.formLogin().loginPage("/userLogin").usernameParameter("username").passwordParameter("pwd").failureUrl("/login-error").failureUrl("/error");```
  - ```.usernameParameter("username") ```  ： 匹配  ```login.html```  的用户名
  - ```.passwordParameter("pwd")```   ： 匹配  ```login.html```  的密码
- ```http.logout()``` 更改为 ```http.logout().logoutSuccessUrl("/userLogin");``` ,退出登录默认重定向的路径是 ```/login```，我们需要手动设置为 ```/userLogin```
- ```http.rememberMe()```   更改为 ```http.rememberMe().rememberMeParameter("rememberMe");```   
  - ```.rememberMeParameter("rememberMe") ```  ： 匹配  ```login.html```  的记住我

```
protected void configure(HttpSecurity http) throws Exception {
        //定制请求的授权规则
        http.authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/level1/**").hasRole("VIP1")
                .antMatchers("/level2/**").hasRole("VIP2")
                .antMatchers("/level3/**").hasRole("VIP3");
        //开启自动配置登录功能，如果没有登录，没有权限就会来到登录页面
      http.formLogin().loginPage("/userLogin").usernameParameter("username").passwordParameter("pwd").failureUrl("/login-error").failureUrl("/error");
        //1、/login 会来到登录页
        //2、重定向到/login?error 表示登录失败
        //3、更多详细规定
        //4、默认 post 形式的 /login 代表处理登录
        //5、一旦定时loginPage： 那么 loginPage 的post请求就是登录

        //开启自动配置注销功能
        http.logout().logoutSuccessUrl("/userLogin");

        //开启记住我功能
        http.rememberMe().rememberMeParameter("rememberMe");
        //登录成功以后，将cookie发给浏览器保存，以后访问页面带上这个cookie，只要通过检查就可以免登录
        //点击注销，会删除cookie
    }
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .passwordEncoder(new Pbkdf2PasswordEncoder())
                .withUser("zhangsan").password(new Pbkdf2PasswordEncoder().encode("123456")).roles("VIP1","VIP2")
                .and()
                .withUser("lisi").password(new Pbkdf2PasswordEncoder().encode("123456")).roles("VIP2","VIP3")
                .and()
                .withUser("wangwu").password(new Pbkdf2PasswordEncoder().encode("123456")).roles("VIP1","VIP3");
    }
}
```

#### 4.3、设置自定义 ```login.html``` 页面

```html
<div align="center">
	<form th:action="@{userLogin}" method="post">
		用户名:<input name="username"/><br>
		密码:<input name="pwd"><br/>
		<input type="checkbox" name="rememberMe"/>记住我<br/>
		<input type="submit" value="登陆">
	</form>
</div>
</body>
```









