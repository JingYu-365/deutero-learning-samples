package me.jkong.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author JKong
 * @version v1.0
 * @description Order 启动类
 * @date 2020-06-09 20:48.
 */
@EnableDiscoveryClient
@SpringBootApplication
public class OrderApplicationZk {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplicationZk.class, args);
    }
}