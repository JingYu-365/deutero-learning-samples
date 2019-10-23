package me.jkong.nio.in_and_out;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author JKong
 * @version v1.0
 * @description TODO
 * @date 2019/10/22 19:37.
 */
public class NioInPut {
    public static void main(String[] args) {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream("nio_in_put.jkong");
            FileChannel fileChannel = inputStream.getChannel();

            ByteBuffer buffer = ByteBuffer.allocate(1024);

            // channel.read(buffer): 将数据读入buffer
            // channel.write(buffer)：将数据写出到 buffer
            fileChannel.read(buffer);

            // 将 buffer 反转
            buffer.flip();

            // 将数据读出，另一种简单的方式：buffer.array() 就可以将buffer中的数据全部转为 byte[]
            byte[] bytes = new byte[1024];
            for (int i = 0; i < buffer.limit(); i++) {
                bytes[i] = buffer.get(i);
            }
            System.out.println(new String(bytes));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}