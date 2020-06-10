package me.jkong.loadrule;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.Server;

/**
 * @author JKong
 * @version v1.0
 * @description 自定义实现负载策略 {@link com.netflix.loadbalancer.RoundRobinRule}
 * @date 2020-06-10 22:23.
 */
public class MyLoadBalanceRule extends AbstractLoadBalancerRule {

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    @Override
    public Server choose(Object key) {

        // TODO: 2020-06-10 实现自定义的负载策略
        return null;
    }
}