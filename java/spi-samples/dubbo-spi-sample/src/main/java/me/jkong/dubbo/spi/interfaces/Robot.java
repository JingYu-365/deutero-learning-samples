package me.jkong.dubbo.spi.interfaces;

import org.apache.dubbo.common.extension.SPI;

@SPI
public interface Robot {
    void sayHello();
}