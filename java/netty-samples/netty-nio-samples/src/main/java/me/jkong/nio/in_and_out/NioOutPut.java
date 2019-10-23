package me.jkong.nio.in_and_out;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author JKong
 * @version v1.0
 * @description TODO
 * @date 2019/10/23 15:41.
 */
public class NioOutPut {

    public static void main(String[] args) {

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream("nio_out_put.jkong");
            FileChannel channel = fileOutputStream.getChannel();

            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put("this is a nio output test".getBytes());

            buffer.flip();
            channel.write(buffer);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}