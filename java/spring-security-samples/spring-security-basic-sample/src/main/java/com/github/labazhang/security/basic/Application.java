package com.github.labazhang.security.basic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * @author laba zhang
 */
@SpringBootApplication
@MapperScan("com.github.labazhang.security.basic.mapper")
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        prePostEnabled = true
)      // 开启注解支持权限控制
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
