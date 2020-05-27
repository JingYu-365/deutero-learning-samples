package me.jkong.java.spi.interfaces;

import org.junit.jupiter.api.Test;

import java.util.ServiceLoader;

/**
 * 测试单个扩展点，多个扩展
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/5/27 10:19.
 */
class RobotTest {

    @Test
    void sayHello() {
        ServiceLoader<Robot> serviceLoader = ServiceLoader.load(Robot.class);
        serviceLoader.forEach(Robot::sayHello);
    }
}