package me.jkong.provider.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import me.jkong.provider.util.http.HttpResponseProcessor;

/**
 * @author JKong
 * @version v1.0
 * @description handler
 * @date 2019/9/24 8:53.
 */
public class RequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) {
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 异常处理
        Channel channel = ctx.channel();
        if (channel.isActive()) {
            ctx.writeAndFlush(HttpResponseProcessor.getInternalErrorResponse());
        }
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }
}
