package me.jkong.java.spi.interfaces;

/**
 * 定义SPI接口（扩展点）
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/5/27 9:46.
 */
public interface ISpi {
    /**
     * invoke 方法需要子类实现
     */
    void invoke();
}