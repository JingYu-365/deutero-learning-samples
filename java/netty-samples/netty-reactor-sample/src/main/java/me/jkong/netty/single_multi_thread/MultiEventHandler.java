package me.jkong.netty.single_multi_thread;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author JKong
 * @version v1.0
 * @description TODO
 * @date 2019/10/26 16:56.
 */
public class MultiEventHandler implements Runnable {
    private final SocketChannel channel;
    private final SelectionKey selectionKey;
    private ByteBuffer input = ByteBuffer.allocate(Integer.MAX_VALUE);
    private ByteBuffer output = ByteBuffer.allocate(Integer.MAX_VALUE);
    private static final int READING = 0, SENDING = 1;
    private int state = READING;


    private ExecutorService pool = Executors.newFixedThreadPool(2);
    private static final int PROCESSING = 3;

    MultiEventHandler(Selector selector, SocketChannel c) throws IOException {
        channel = c;
        c.configureBlocking(false);
        // Optionally try first read now
        selectionKey = channel.register(selector, 0);

        //将Handler作为callback对象
        selectionKey.attach(this);

        //第二步,注册Read就绪事件
        selectionKey.interestOps(SelectionKey.OP_READ);
        selector.wakeup();
    }

    private boolean inputIsComplete() {
        return false;
    }

    private boolean outputIsComplete() {

        return false;
    }

    private void process() {
    }

    @Override
    public void run() {
        try {
            if (state == READING) {
                read();
            } else if (state == SENDING) {
                send();
            }
        } catch (IOException ex) { /* ... */ }
    }


    private synchronized void read() throws IOException {
        // ...
        channel.read(input);
        if (inputIsComplete()) {
            state = PROCESSING;
            //使用线程pool异步执行
            pool.execute(new Processer());
        }
    }

    private void send() throws IOException {
        channel.write(output);

        //write完就结束了, 关闭select key
        if (outputIsComplete()) {
            selectionKey.cancel();
        }
    }

    private synchronized void processAndHandOff() {
        process();
        state = SENDING;
        // or rebind attachment
        //process完,开始等待write就绪
        selectionKey.interestOps(SelectionKey.OP_WRITE);
    }

    class Processer implements Runnable {
        @Override
        public void run() {
            processAndHandOff();
        }
    }
}