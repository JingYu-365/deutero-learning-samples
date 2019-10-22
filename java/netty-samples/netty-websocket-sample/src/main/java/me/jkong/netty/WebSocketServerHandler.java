package me.jkong.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;

/**
 * @author JKong
 * @version v1.0
 * @description web socket server handler
 * @date 2019/10/22 18:53.
 */
public class WebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        //一定要输出msg的text，不然得不到正确消息
        System.out.println("服务端收到的消息:" + msg.text());

        // 其实这里writeAndFlush（）里面传入的是object类型，说明无论传什么参数都不会报错
        // 但是我们这里是要传一个TextWebSocketFrame对象，所以不能传单独的字符串，传了也传不出去
        // 因为我们只用了TextWebSocketFrame
        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器的时间：" + LocalDateTime.now()));
    }

    /**
     * web客户连接后，会打印这行
     * @param ctx channel 上下文
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        // id表示唯一，有长有短，长的asLongText，唯一。短的asShortText()不唯一
        System.out.println("handlerAdded的 ID："+ctx.channel().id().asShortText());
    }

    /**
     * 一个很有趣的现象，如果客户端刷新一下，实际上会调用这个方法，因为连接断了，新建了一个连接
     * @param ctx channel 上下文
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved的 ID："+ctx.channel().id().asLongText());
    }
}