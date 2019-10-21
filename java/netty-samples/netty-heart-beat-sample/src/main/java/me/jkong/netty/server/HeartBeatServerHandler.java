package me.jkong.netty.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.io.IOException;

/**
 * @author JKong
 * @version v1.0
 * @description 心跳处理器
 * @date 2019/10/21 12:57.
 */
public class HeartBeatServerHandler extends SimpleChannelInboundHandler {

    /**
     * 连续超过N次未收到client的ping消息，那么关闭该通道，等待client重连
     */
    private static final int MAX_UN_REC_PING_TIMES = 3;
    /**
     * 当前失败次数
     */
    private int failTimes = 0;
    private static final String PING = "PING";
    private static final String PONG = "PONG";


    /**
     * IdleState 是一个枚举类型
     * READER_IDLE  读空闲: 即当在指定的时间间隔内没有从 Channel 读取到数据时, 会触发一个 READER_IDLE 的 IdleStateEvent 事件
     * WRITER_IDLE  写空闲: 即当在指定的时间间隔内没有数据写入到 Channel 时, 会触发一个 WRITER_IDLE 的 IdleStateEvent 事件.
     * ALL_IDLE     读写都空闲: 即当在指定的时间间隔内没有读或写操作时, 会触发一个 ALL_IDLE 的 IdleStateEvent 事件.
     * 所说的事件，也就是触发userEventTriggered()方法。
     *
     * @param ctx 上下文
     * @param evt 接收数据
     * @throws Exception 异常
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent stateEvent = (IdleStateEvent) evt;

            IdleState state = stateEvent.state();
            // READER_IDLE: 在指定的时间内，未收到客户端发来得心跳包或者数据，将触发读空闲事件。
            if (state == IdleState.READER_IDLE) {
                if (failTimes > MAX_UN_REC_PING_TIMES) {
                    System.out.println("15秒内没有收到" + ctx.channel().remoteAddress() + "PING ,即将关闭连接！");
                    ctx.close();
                }
            } else {
                // 失败计数器加1
                failTimes++;
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg != null && msg.equals(PING)) {
            System.out.println("客户端" + ctx.channel().remoteAddress() + PING);
            ctx.writeAndFlush(PONG);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println(ctx.channel().remoteAddress() + "客户端已连接");

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "客户端已断开");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof IOException) {
            System.out.println("client " + ctx.channel().remoteAddress() + "强制关闭连接");
        }
    }
}