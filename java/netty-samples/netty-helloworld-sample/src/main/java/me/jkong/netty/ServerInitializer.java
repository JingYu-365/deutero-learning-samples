package me.jkong.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;


/**
 * @author JKong
 * @version v1.0
 * @description Channel 初始化器
 * @date 2019/10/17 7:47.
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {
    /**
     * 每一次连接请求，都将会初始化 pipeline
     * todo 验证为什么所有的请求不都使用同一个pipeline?
     *
     * @param socketChannel channel
     * @throws Exception
     */
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        System.out.println("... init channel ...");
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("httpServerCodec", new HttpServerCodec());
        pipeline.addLast("httpServerHandler", new HttpServerHandler());
    }
}