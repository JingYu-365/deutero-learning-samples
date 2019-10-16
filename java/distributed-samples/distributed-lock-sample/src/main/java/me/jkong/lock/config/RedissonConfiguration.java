package me.jkong.lock.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author JKong
 * @version v1.0
 * @description TODO
 * @date 2019/10/16 12:37.
 */
@Configuration
public class RedissonConfiguration {

    @Bean
    public RedissonClient redisson() {
        Config config = new Config();
        //可以用"rediss://"来启用SSL连接
        config.setTransportMode(TransportMode.EPOLL);
        config.useClusterServers()
                .addNodeAddress("redis://127.0.0.1:7181");
        return Redisson.create(config);
    }
}