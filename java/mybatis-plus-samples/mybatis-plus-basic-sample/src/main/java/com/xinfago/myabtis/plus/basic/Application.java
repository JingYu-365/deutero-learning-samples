package com.xinfago.myabtis.plus.basic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhdh
 */
@SpringBootApplication
@MapperScan("com.xinfago.myabtis.plus.basic.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}