package me.jkong.java.spi.interfaces;

import org.junit.jupiter.api.Test;

import java.util.ServiceLoader;


/**
 * 测试单个扩展点
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/5/27 10:19.
 */
class ISpiTest {

    @Test
    void invoke() {
        ServiceLoader<ISpi> serviceLoader = ServiceLoader.load(ISpi.class);
        serviceLoader.forEach(ISpi::invoke);
    }
}