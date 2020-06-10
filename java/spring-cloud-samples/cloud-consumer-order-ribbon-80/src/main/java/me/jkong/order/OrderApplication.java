package me.jkong.order;

import me.jkong.loadrule.LoadBalanceRuleInUse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

/**
 * @author JKong
 * @version v1.0
 * @description Order 启动类
 * @date 2020-06-09 20:48.
 */
@EnableDiscoveryClient
@SpringBootApplication
@RibbonClient(name = "CLOUD-PROVIDER-PAY", configuration = LoadBalanceRuleInUse.class)
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}