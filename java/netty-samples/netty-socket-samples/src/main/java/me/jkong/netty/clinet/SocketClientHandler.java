package me.jkong.netty.clinet;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @author JKong
 * @version v1.0
 * @description socket service handler
 * @date 2019/10/18 10:37.
 */
public class SocketClientHandler extends SimpleChannelInboundHandler<String> {
    /**
     * read msg
     *
     * @param ctx client channel context
     * @param msg received msg
     * @throws Exception e
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " | " + msg);

        // 停 1s 时间
        TimeUnit.SECONDS.sleep(1);
        ctx.writeAndFlush("client：" + LocalDateTime.now());
    }

    /**
     * channelActive可以让Client连接后，发送消息
     *
     * @param ctx channel context
     * @throws Exception e
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("hello server!");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}