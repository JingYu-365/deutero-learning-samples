package me.jkong.netty.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author JKong
 * @version v1.0
 * @description TODO
 * @date 2019/10/21 19:27.
 */
public class HeartBeatClientInitializer extends ChannelInitializer<SocketChannel> {

    private HeartBeatClient heartBeatClient;

    public HeartBeatClientInitializer(HeartBeatClient heartBeatClient) {
        this.heartBeatClient = heartBeatClient;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new IdleStateHandler(5,6,7, TimeUnit.SECONDS))
                .addLast(new StringDecoder())
                .addLast(new StringEncoder())
                .addLast(new HeartBeatClientHandler(heartBeatClient));
    }
}