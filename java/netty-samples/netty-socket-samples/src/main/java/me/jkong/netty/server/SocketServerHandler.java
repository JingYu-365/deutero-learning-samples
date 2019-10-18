package me.jkong.netty.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * @author JKong
 * @version v1.0
 * @description socket service handler
 * @date 2019/10/18 10:02.
 */
public class SocketServerHandler extends SimpleChannelInboundHandler<String> {
    /**
     * read msg
     *
     * @param ctx server channel handler
     * @param msg received msg
     * @throws Exception e
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " | " + msg);
        ctx.writeAndFlush("server: " + UUID.randomUUID().toString());
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}