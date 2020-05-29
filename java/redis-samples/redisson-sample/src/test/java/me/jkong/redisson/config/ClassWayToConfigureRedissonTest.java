package me.jkong.redisson.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.io.IOException;


/**
 * {@link ApiWayToConfigureRedisson} 测试
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/5/29 9:47.
 */
class ApiWayToConfigureRedissonTest {

    @Test
    void instanceAsync() {
        Config config = ApiWayToConfigureRedisson.singleInstanceAsync("127.0.0.1", 6379);
        Assertions.assertNotNull(config);
        RedissonClient redissonClient = Redisson.create(config);
        Assertions.assertNotNull(redissonClient);
    }
}