package me.jkong.netty.all_multi_thread;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

class MultiThreadReactor implements Runnable {

    /**
     * subReactors集合, 一个selector代表一个subReactor
     */
    private Selector[] selectors = new Selector[2];
    private int next = 0;
    private final ServerSocketChannel serverSocket;

    /**
     * Reactor初始化
     *
     * @param port port
     * @throws IOException e
     */
    MultiThreadReactor(int port) throws IOException {
        selectors[0] = Selector.open();
        selectors[1] = Selector.open();
        serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(port));
        // 非阻塞
        serverSocket.configureBlocking(false);


        // 分步处理,第一步,接收accept事件
        SelectionKey sk =
                serverSocket.register(selectors[0], SelectionKey.OP_ACCEPT);
        // attach callback object, Acceptor
        sk.attach(new Acceptor());
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                for (int i = 0; i < 2; i++) {
                    selectors[i].select();
                    Set selected = selectors[i].selectedKeys();
                    Iterator it = selected.iterator();
                    while (it.hasNext()) {
                        // Reactor负责dispatch收到的事件
                        dispatch((SelectionKey) (it.next()));
                    }
                    selected.clear();
                }

            }
        } catch (IOException ex) {
            // do something
            ex.printStackTrace();
        }
    }

    private void dispatch(SelectionKey k) {
        Runnable r = (Runnable) (k.attachment());
        // 调用之前注册的callback对象
        if (r != null) {
            r.run();
        }
    }


    class Acceptor { // ...
        public synchronized void run() throws IOException {
            // 主selector负责accept
            SocketChannel connection = serverSocket.accept();
            if (connection != null) {
                // 选个subReactor去负责接收到的connection
//                new Handler(selectors[next], connection);
            }
            if (++next == selectors.length) {
                next = 0;
            }
        }
    }
}