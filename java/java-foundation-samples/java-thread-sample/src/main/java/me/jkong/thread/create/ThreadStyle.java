package me.jkong.thread.create;

/**
 * @author JKong
 * @version v1.0
 * @description TODO
 * @date 2020/3/3 8:29 上午.
 */
public class ThreadStyle extends Thread {
    @Override
    public void run() {
        System.out.println("this is extends Thread.");
    }

    public static void main(String[] args) {
        new ThreadStyle().start();
    }
}