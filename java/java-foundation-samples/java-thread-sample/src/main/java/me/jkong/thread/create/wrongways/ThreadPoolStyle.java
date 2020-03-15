package me.jkong.thread.create.wrongways;

import javafx.concurrent.Task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author JKong
 * @version v1.0
 * @description 使用线程池，实际上最终还是使用实现Runnable创建的线程。
 * @date 2020/3/3 8:35 上午.
 */
public class ThreadPoolStyle {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 1000; i++) {
            executorService.submit(new MyTask());
        }
        if (!executorService.isShutdown()) {
            executorService.shutdown();
        }
    }
}
class MyTask implements Runnable {
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }
}
