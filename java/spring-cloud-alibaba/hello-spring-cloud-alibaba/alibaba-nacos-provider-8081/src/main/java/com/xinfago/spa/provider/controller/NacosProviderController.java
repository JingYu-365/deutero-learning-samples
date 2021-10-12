package com.xinfago.spa.provider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Zhang Duanhe
 */
@RestController
public class NacosProviderController {

    @Value("${server.port}")
    private String port;

    @Value("${user.name}")
    private String userName;

    /**
     * 注入配置文件上下文
     */
    @Autowired
    private ConfigurableApplicationContext applicationContext;

    @GetMapping(value = "/test/{message}")
    public String test(@PathVariable String message) {
        return "Hello Nacos Discovery " + message + " i am from port " + port;
    }

    /**
     * 从上下文中读取配置
     *
     * @return str
     */
    @GetMapping(value = "/hi")
    public String sayHi() {
        String userName = applicationContext.getEnvironment().getProperty("user.name");
        System.out.println(userName);
        System.out.println(this.userName);
        // 通过上下文Context获取到的变量会实时更新，但是通过@Value注入的变量，不会实时更新。
        return "Hello " + this.userName;
    }
}