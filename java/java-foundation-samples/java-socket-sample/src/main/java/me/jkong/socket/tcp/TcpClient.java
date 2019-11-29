package me.jkong.socket.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author JKong
 * @version v1.0
 * @description TCP CLIENT
 * @date 2019/11/29 10:44.
 */
public class TcpClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("127.0.0.1", 2365));

        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("hello tcp server".getBytes());

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        System.out.println(reader.readLine());
    }
}