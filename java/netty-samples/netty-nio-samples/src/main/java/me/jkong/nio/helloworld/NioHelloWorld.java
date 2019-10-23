package me.jkong.nio.helloworld;

import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 * @author JKong
 * @version v1.0
 * @description buffer 简单使用
 * @date 2019/10/23 15:26.
 */
public class NioHelloWorld {
    public static void main(String[] args) {
        // 指定buffer 大小
        IntBuffer buffer = IntBuffer.allocate(10);

        // buffer 中填充数据
        for (int i = 0; i < buffer.capacity(); i++) {
            int ranInt = new SecureRandom().nextInt(20);
            buffer.put(ranInt);
        }

        // 反转 buffer
        buffer.flip();

        // 输出 buffer 中数据
        // 判断 buffer 中是否还有数据，可以使用 buffer.hasRemaining() 或 buffer.remaining() > 0
        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }
    }
}