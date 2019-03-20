package com.mqf.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

/**
 * @ClassName SecurityConfig
 * @Description TODO
 * @Author mqf
 * @Date 2019/3/19 19:13
 * https://docs.spring.io/spring-security/site/docs/current/guides/html5/helloworld-boot.html#creating-your-spring-security-configuration
 */
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
        http.formLogin().loginPage("/userLogin").usernameParameter("username").passwordParameter("pwd").failureUrl("/login-error").failureUrl("/error");
        //1、/login 会来到登录页
        //2、重定向到/login?error 表示登录失败
        //3、更多详细规定
        //4、默认 post 形式的 /login 代表处理登录
        //5、一旦定时loginPage： 那么 loginPage 的post请求就是登录

        //开启自动配置注销功能
//        http.logout().logoutSuccessUrl("/login");
        http.logout().logoutSuccessUrl("/userLogin");

        //开启记住我功能
        http.rememberMe().rememberMeParameter("rememberMe");
        //登录成功以后，将cookie发给浏览器保存，以后访问页面带上这个cookie，只要通过检查就可以免登录
        //点击注销，会删除cookie
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
        //这个是jdbc使用
//        auth.userDetailsService(userDetailsService()).passwordEncoder(new Pbkdf2PasswordEncoder());
    }


    public static void main(String[] args) {
        String pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder().encode("123456");
        System.out.println(pbkdf2PasswordEncoder);
    }
}
