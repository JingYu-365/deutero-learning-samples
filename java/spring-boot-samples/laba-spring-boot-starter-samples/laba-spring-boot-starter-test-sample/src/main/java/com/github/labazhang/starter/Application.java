package com.xinfago.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);

        RedisOperation redisOperation = ctx.getBean(RedisOperation.class);
        redisOperation.redisStarterTest();
        System.out.println("success!");
    }
}
