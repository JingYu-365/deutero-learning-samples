package me.jkong.consumer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import me.jkong.consumer.handler.RequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author jkong
 * @date 2018/8/15
 */
public class Consumer {
    private static Logger logger = LoggerFactory.getLogger(Consumer.class);
    public static ClassPathXmlApplicationContext CONTEXT;

    private void startServer(int port) {
        EventLoopGroup managerGroup = new NioEventLoopGroup(2);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap server = new ServerBootstrap();
        server.group(managerGroup, workerGroup).channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) {
                        ChannelPipeline p = channel.pipeline();
                        p.addLast("requestDecoder", new HttpRequestDecoder());
                        p.addLast("responseEncoder", new HttpResponseEncoder());
                        p.addLast("aggregator", new HttpObjectAggregator(6291456));
                        p.addLast("idleStateTimeout",
                                new IdleStateHandler(30, 30, 30));
                        p.addLast("sasRequestHandler", new RequestHandler());
                    }
                });

        server.option(ChannelOption.SO_BACKLOG, 1024)
                .option(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.SO_REUSEADDR, true);

        ChannelFuture future = server.bind(port);

        initDubboConfig();

        future.channel().closeFuture().syncUninterruptibly();
        try {
            managerGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage());
            }
        }
    }

    private void initDubboConfig() {
        CONTEXT = new ClassPathXmlApplicationContext("classpath:consumer.xml");
        CONTEXT.start();
    }

    private static Consumer getInstance() {
        return new Consumer();
    }

    public static void main(String[] args) {
        Consumer.getInstance().startServer(4321);
    }
}
