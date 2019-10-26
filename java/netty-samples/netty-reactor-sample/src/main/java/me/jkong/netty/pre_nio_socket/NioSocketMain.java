package me.jkong.netty.pre_nio_socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author JKong
 * @version v1.0
 * @description Nio socket
 * @date 2019/10/26 14:14.
 * @tips: 1. telnet 127.0.0.1 2365
 * 2. input your content
 */
public class NioSocketMain {

    private static final int SOCKET_SERVER_PORT = 2365;

    public static void main(String[] args) throws IOException {
        server();
    }

    private static void server() throws IOException {
        // 获取通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 设置非阻塞
        serverSocketChannel.configureBlocking(false);
        // 绑定端口
        serverSocketChannel.bind(new InetSocketAddress(SOCKET_SERVER_PORT));
        // 获取Selector选择器
        Selector selector = Selector.open();
        // 将通道注册到选择器
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        // 采用轮询的方式，查询获取“准备就绪”的注册过的操作
        while (selector.select() > 0) {
            // 获取当前选择器中所有注册的选择键（“已经准备就绪的操作”）
            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
            while (keyIterator.hasNext()) {
                SelectionKey selectionKey = keyIterator.next();
                // 根据key注册的事件，选择不同处理
                dispatch(selectionKey);
                keyIterator.remove();
            }
        }
        // 关闭通道
        serverSocketChannel.close();
    }

    /**
     * 事件分发
     *
     * @param selectionKey selection key
     * @throws IOException
     */
    private static void dispatch(SelectionKey selectionKey) throws IOException {
        switch (selectionKey.interestOps()) {
            case SelectionKey.OP_ACCEPT: {
                System.out.println("accept");
                processAcceptEvent(selectionKey);
                break;
            }
            case SelectionKey.OP_READ: {
                System.out.println("read");
                processReadEvent(selectionKey);
                break;
            }
            default:
                throw new UnsupportedOperationException();
        }
    }


    /**
     * 处理读事件
     *
     * @param selectionKey selection key
     * @throws IOException
     */
    private static void processReadEvent(SelectionKey selectionKey) throws IOException {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int len;
        while ((len = socketChannel.read(byteBuffer)) != 0) {
            byteBuffer.flip();
            System.out.println(new String(byteBuffer.array(), 0, len));
            byteBuffer.clear();
        }
        socketChannel.close();
    }

    /**
     * 处理 accept 事件
     *
     * @param selectionKey selection key
     * @throws IOException
     */
    private static void processAcceptEvent(SelectionKey selectionKey)
            throws IOException {
        SocketChannel socketChannel = ((ServerSocketChannel) selectionKey.channel()).accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(selectionKey.selector(), SelectionKey.OP_READ);
    }
}