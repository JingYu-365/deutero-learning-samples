package me.jkong.netty.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author JKong
 * @version v1.0
 * @description HeartBeatServerInitializer
 * @date 2019/10/21 12:54.
 */
public class HeartBeatServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new IdleStateHandler(5,6,7, TimeUnit.SECONDS))
                .addLast(new StringDecoder())
                .addLast(new StringEncoder())
                .addLast(new HeartBeatServerHandler());
    }
}