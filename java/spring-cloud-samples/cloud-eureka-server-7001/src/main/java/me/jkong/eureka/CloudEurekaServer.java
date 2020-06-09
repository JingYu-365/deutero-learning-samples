package me.jkong.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka 服务端
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/6/9 13:09.
 */
@SpringBootApplication
@EnableEurekaServer
public class CloudEurekaServer {
    public static void main(String[] args) {
        SpringApplication.run(CloudEurekaServer.class, args);
    }
}