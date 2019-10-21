package me.jkong.netty.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author JKong
 * @version v1.0
 * @description heart beat client handler
 * @date 2019/10/21 19:29.
 */
public class HeartBeatClientHandler extends SimpleChannelInboundHandler {

    private static final String PING = "PING";
    private static final String PONG = "PONG";

    private HeartBeatClient client;

    public HeartBeatClientHandler(HeartBeatClient heartBeatClient) {
        this.client = heartBeatClient;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg.equals(PONG)) {
            System.out.println("receive form server PONG");
        }
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.ALL_IDLE) {
                ctx.writeAndFlush(PING);
                System.out.println("send PING");
            }
        }
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        System.err.println("客户端与服务端断开连接,断开的时间为：" + (new Date()).toString());
        // 定时线程 断线重连
        final EventLoop eventLoop = ctx.channel().eventLoop();
        eventLoop.schedule(() -> client.connect(), 2, TimeUnit.SECONDS);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}