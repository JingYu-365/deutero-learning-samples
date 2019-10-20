package me.jkong.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author JKong
 * @version v1.0
 * @description chat client
 * @date 2019/10/20 21:49.
 */
public class ChatClient {
    public static void main(String[] args) {
        EventLoopGroup worker = new NioEventLoopGroup();

        try {

            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(worker)
                    .channel(NioSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .handler(new ChatClientInitializer());

            Channel channel = bootstrap.connect("127.0.0.1", 2365).sync().channel();

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            for(;;) {
                channel.writeAndFlush(br.readLine() + "\r\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            worker.shutdownGracefully();
        }
    }
}