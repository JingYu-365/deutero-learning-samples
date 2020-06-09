package me.jkong.pay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author JKong
 * @version v1.0
 * @description 支付提供者
 * @date 2020-06-08 22:52.
 */
@EnableDiscoveryClient
@SpringBootApplication
public class PayApplication {
    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class, args);
    }
}