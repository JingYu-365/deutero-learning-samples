package me.jkong.dubbo.spi.interfaces;


import org.apache.dubbo.common.extension.ExtensionLoader;
import org.junit.jupiter.api.Test;

/**
 * Dubbo SPI test
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/5/27 10:26.
 */
class RobotTest {

    @Test
    void sayHello() {
        ExtensionLoader<Robot> extensionLoader = ExtensionLoader.getExtensionLoader(Robot.class);
        Robot optimusPrime = extensionLoader.getExtension("optimusPrime");
        optimusPrime.sayHello();
        Robot bumblebee = extensionLoader.getExtension("bumblebee");
        bumblebee.sayHello();
    }
}