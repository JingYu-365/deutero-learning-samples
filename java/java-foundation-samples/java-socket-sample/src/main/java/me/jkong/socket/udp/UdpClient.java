package me.jkong.socket.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 * @author JKong
 * @version v1.0
 * @description udp client
 * @date 2019/11/29 11:12.
 */
public class UdpClient {
    public static void main(String[] args) throws IOException {

        DatagramSocket socket = new DatagramSocket();
        String msg = "hello udp server";
        DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.length(),
                new InetSocketAddress("127.0.0.1", 2366));
        socket.send(packet);

        byte[] bytes = new byte[100];
        packet = new DatagramPacket(bytes, bytes.length);
        socket.receive(packet);
        System.out.println(new String(bytes));

    }
}