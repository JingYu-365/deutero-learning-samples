package me.jkong.loadrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 使用配置类指定负载策略，定义了此类之后，需要在启动类中添加以下配置：
 * {@code @RibbonClient(name = "CLOUD-PROVIDER-PAY", configuration = MyLoadBalanceRule.class)}
 * 1. 指定此负载策略对哪个服务起作用
 * 2. 指定负载策略配置类
 *
 * @author JKong
 * @version v1.0
 * @description 指定负载策略
 * @date 2020-06-10 21:59.
 */
@Configuration
public class LoadBalanceRuleInUse {

    @Bean
    public IRule myRule() {//定义为随机

        return new RandomRule();
    }
}