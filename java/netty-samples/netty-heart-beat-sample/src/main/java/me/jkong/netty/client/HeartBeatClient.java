package me.jkong.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author JKong
 * @version v1.0
 * @description heart beat client
 * @date 2019/10/21 12:48.
 * @tips: https://blog.csdn.net/TheLudlows/article/details/83870765
 */
public class HeartBeatClient {

    private Bootstrap bootstrap;

    public HeartBeatClient(Bootstrap bootstrap) {
        this.bootstrap = bootstrap;
        this.bootstrap.channel(NioSocketChannel.class)
                .group(new NioEventLoopGroup())
                .handler(new HeartBeatClientInitializer(this));
    }

    public static void main(String[] args) {
        HeartBeatClient client = new HeartBeatClient(new Bootstrap());
        client.connect();
    }


    protected void connect() {
        ChannelFuture channelFuture = bootstrap
                .connect(new InetSocketAddress("127.0.0.1", 2365));

        channelFuture.addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("connect to server success");
            } else {
                System.out.println("connect to server failed, try again!");
                connect();
            }
        });
    }
}