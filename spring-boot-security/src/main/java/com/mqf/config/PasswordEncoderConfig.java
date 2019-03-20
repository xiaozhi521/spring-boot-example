package com.mqf.config;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @ClassName PasswordEncoderConfig
 * @Description TODO
 * @Author mqf
 * @Date 2019/3/19 20:17
 *  doc： https://docs.spring.io/spring-security/site/docs/5.1.4.RELEASE/reference/htmlsingle/#core-services-password-encoding
 * 解决问题： java.lang.IllegalArgumentException: There is no PasswordEncoder mapped for the id "null"
 *  新建java 类，实现 PasswordEncoder 接口
 */
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
