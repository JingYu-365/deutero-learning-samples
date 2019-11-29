package me.jkong.socket.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * @author JKong
 * @version v1.0
 * @description UDP server
 * @date 2019/11/29 11:12.
 */
public class UdpServer {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket(2366);
        DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
        socket.receive(packet);

        byte[] data = packet.getData();
        System.out.println(new String(data));

        String msg = "hello udp client";
        packet = new DatagramPacket(msg.getBytes(),msg.length(),packet.getAddress(),packet.getPort());
        socket.send(packet);

        socket.close();
    }
}