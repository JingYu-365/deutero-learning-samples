package com.xinfago.properties.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * test for Configuration
 *
 * @author laba zhang
 */
@Configuration
public class MyConfig {

    @Bean(value = "myService")
    public MyService myService() {
        return new MyService();
    }
}
