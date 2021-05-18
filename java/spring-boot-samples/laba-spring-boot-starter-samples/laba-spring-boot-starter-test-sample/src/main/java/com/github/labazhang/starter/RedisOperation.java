package com.xinfago.starter;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

/**
 * Redis operation
 *
 * @author laba zhang
 */
@Service
public class RedisOperation {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisOperation(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void redisStarterTest() {
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.opsForValue().set("key", "value:laba");
    }
}
