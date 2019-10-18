package me.jkong.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author JKong
 * @version v1.0
 * @description server
 * @date 2019/10/17 7:34.
 */
public class MainServer {
    public static void main(String[] args) throws InterruptedException {

        // NioEventLoopGroup extents ScheduledExecutorService
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap()
                    .group(boss, worker)
                    // handler 方法是为 boss 添加处理器
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .channel(NioServerSocketChannel.class)
                    // childHandler 方法是为 worker 添加处理器
                    .childHandler(new ServerInitializer());

            ChannelFuture future = serverBootstrap.bind(2365).sync();
            future.channel().closeFuture().sync();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}