package me.jkong.netty.only_single_thread;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * @author JKong
 * @version v1.0
 * @description event handler
 * @date 2019/10/26 16:06.
 */
public class EventHandler implements Runnable {
    private final SocketChannel sc;
    private final SelectionKey sk;
    private ByteBuffer input = ByteBuffer.allocate(Integer.MAX_VALUE);
    private ByteBuffer output = ByteBuffer.allocate(Integer.MAX_VALUE);
    private static final int READING = 0, SENDING = 1;
    private int state = READING;

    public EventHandler(Selector selector, SocketChannel socketChannel) throws IOException {
        sc = socketChannel;
        sc.configureBlocking(false);
        // Optionally try first read now
        sk = socketChannel.register(selector, 0);
        sk.attach(this);
        sk.interestOps(SelectionKey.OP_READ);
        selector.wakeup();
    }

    @Override
    public void run() {
        try {
            if (state == READING) {
                read();
            } else if (state == SENDING) {
                send();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void read() throws IOException {
        sc.read(input);
        if (inputIsComplete()) {
            process();
            state = SENDING;
            // Normally also do first write now
            sk.interestOps(SelectionKey.OP_WRITE);
        }
    }

    private void process() {
        System.out.println(new String(input.array()));
    }

    private boolean inputIsComplete() {
        return true;
    }


    private void send() throws IOException {
        sc.write(output);
        if (outputIsComplete()) {
            sk.cancel();
        }
    }

    private boolean outputIsComplete() {
        return true;
    }
}