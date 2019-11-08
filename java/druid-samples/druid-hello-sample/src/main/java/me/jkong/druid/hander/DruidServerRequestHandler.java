package me.jkong.druid.hander;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @author JKong
 * @version v1.0
 * @description FullHttpRequest Process Handler
 * @date 2019/11/8 11:30.
 */
public class DruidServerRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {

    }
}