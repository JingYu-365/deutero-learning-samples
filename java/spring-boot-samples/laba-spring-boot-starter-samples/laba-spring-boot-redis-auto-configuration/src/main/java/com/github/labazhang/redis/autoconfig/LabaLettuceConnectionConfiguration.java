package com.xinfago.redis.autoconfig;

import io.lettuce.core.RedisClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

/**
 * Redis connection configuration using Lettuce.
 *
 * @author laba zhang
 */
@ConditionalOnClass(RedisClient.class)
public class LabaLettuceConnectionConfiguration {

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory(RedisStandaloneConfiguration redisStandaloneConfiguration) {
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisStandaloneConfiguration redisStandaloneConfiguration(LabaRedisProperties labaRedisProperties) {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setUsername(labaRedisProperties.getUsername());
        configuration.setPassword(labaRedisProperties.getPassword());
        configuration.setDatabase(labaRedisProperties.getDatabase());
        configuration.setHostName(labaRedisProperties.getHost());
        configuration.setPort(labaRedisProperties.getPort());
        return configuration;
    }
}
