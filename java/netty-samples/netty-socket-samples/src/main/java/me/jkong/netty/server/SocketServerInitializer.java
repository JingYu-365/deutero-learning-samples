package me.jkong.netty.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * @author JKong
 * @version v1.0
 * @description channel initializer
 * @date 2019/10/18 9:35.
 */
public class SocketServerInitializer extends ChannelInitializer<SocketChannel> {
    /**
     * Encoder and decoder should appear in pairs
     * and guarantee the addition of handler order
     *
     * @param ch channel
     * @throws Exception ex
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // decoder
        pipeline.addLast(new LengthFieldBasedFrameDecoder(
                Integer.MAX_VALUE,
                0,
                4,
                0,
                4));
        // encoder
        pipeline.addLast(new LengthFieldPrepender(4));
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        pipeline.addLast(new SocketServerHandler());
    }
}