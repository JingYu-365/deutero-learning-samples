package me.jkong.netty.only_single_thread;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author JKong
 * @version v1.0
 * @description TODO
 * @date 2019/10/26 15:24.
 */
public class Reactor implements Runnable {

    private final Selector selector;
    private final ServerSocketChannel serverSocketChannel;
    private static final int SOCKET_SERVER_PORT = 2365;

    public Reactor(int port) throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(true);

        SelectionKey sk = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        sk.attach(new Acceptor());
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                if (selector.select() > 0) {
                    Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                    while (keyIterator.hasNext()) {
                        dispatch(keyIterator.next());
                        keyIterator.remove();
                    }
                }
            }
        } catch (IOException e) {
            // do something
            e.printStackTrace();
        }
    }

    /**
     * 事件分发处理
     *
     * @param sk selection key
     */
    private void dispatch(SelectionKey sk) {
        Runnable r = (Runnable) sk.attachment();
        if (r != null) {
            r.run();
        }
    }

    /**
     * 事件接收器
     */
    private class Acceptor implements Runnable {

        @Override
        public void run() {
            try {
                SocketChannel socketChannel = serverSocketChannel.accept();
                if (socketChannel != null) {
                    new EventHandler(selector, socketChannel);
                }
            } catch (IOException e) {
                // do something
                e.printStackTrace();
            }

        }
    }


    /**
     * Test
     */
    public static void main(String[] args) throws IOException {
        new Reactor(SOCKET_SERVER_PORT);
    }
}