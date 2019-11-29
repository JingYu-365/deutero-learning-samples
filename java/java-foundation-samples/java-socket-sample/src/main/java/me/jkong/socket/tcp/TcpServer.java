package me.jkong.socket.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author JKong
 * @version v1.0
 * @description TCP SERVER
 * @date 2019/11/29 10:44.
 */
public class TcpServer {
    public static void main(String[] args) throws IOException {

        ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress(2365));
        Socket socket = server.accept();
        InputStream inputStream = socket.getInputStream();

        byte[] buf = new byte[1024];
        int length = inputStream.read(buf);
        System.out.println(new String(buf, 0, length));

        String msg = "hello tcp client";
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(msg.getBytes());

        inputStream.close();
        outputStream.close();
        socket.close();
    }
}