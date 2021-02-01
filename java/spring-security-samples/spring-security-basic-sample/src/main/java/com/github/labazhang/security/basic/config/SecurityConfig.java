package com.github.labazhang.security.basic.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.formLogin() // 表单登录
//                .and()
//                .authorizeRequests() // 认证配置
//                .anyRequest() // 任何请求
//                .authenticated(); // 都需要身份验证
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        auth.inMemoryAuthentication().passwordEncoder(encoder)
                .withUser("laba").password(encoder.encode("laba")).roles("admin");
    }
}