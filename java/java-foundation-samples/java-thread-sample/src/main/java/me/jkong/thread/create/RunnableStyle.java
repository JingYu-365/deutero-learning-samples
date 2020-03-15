package me.jkong.thread.create;

/**
 * @author JKong
 * @version v1.0
 * @description 实现Runnable接口创建线程
 * @date 2020/3/3 8:27 上午.
 */
public class RunnableStyle implements Runnable {
    public static void main(String[] args) {
        new Thread(new RunnableStyle()).start();
    }
    public void run() {
        System.out.println("this is implements Runnable.");
    }
}