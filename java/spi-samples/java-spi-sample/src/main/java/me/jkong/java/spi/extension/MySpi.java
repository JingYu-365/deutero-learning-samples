package me.jkong.java.spi.extension;

import me.jkong.java.spi.interfaces.ISpi;

/**
 * {@link ISpi} 扩展实现
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/5/27 9:49.
 */
public class MySpi implements ISpi {
    @Override
    public void invoke() {
        System.out.println("this is my SPI.");
    }
}