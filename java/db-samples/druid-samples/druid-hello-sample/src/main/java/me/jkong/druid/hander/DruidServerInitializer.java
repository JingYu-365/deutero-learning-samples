package me.jkong.druid.hander;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author JKong
 * @version v1.0
 * @description handler 初始化器
 * @date 2019/11/8 11:25.
 */
public class DruidServerInitializer extends ChannelInitializer {
    private static final int NETTY_SERVER_TRANSFER_PACKET_LIMIT_SIZE = 6291456;
    private static final int NETTY_SERVER_IDLE_TIMEOUT_SECONDS = 30;

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
        p.addLast("requestDecoder", new HttpRequestDecoder());
        p.addLast("responseEncoder", new HttpResponseEncoder());
        p.addLast("aggregator", new HttpObjectAggregator(NETTY_SERVER_TRANSFER_PACKET_LIMIT_SIZE));
        p.addLast("idleStateTimeout", new IdleStateHandler(
                NETTY_SERVER_IDLE_TIMEOUT_SECONDS,
                NETTY_SERVER_IDLE_TIMEOUT_SECONDS,
                NETTY_SERVER_IDLE_TIMEOUT_SECONDS));
        p.addLast("druidServerRequestHandler", new DruidServerRequestHandler());
    }
}