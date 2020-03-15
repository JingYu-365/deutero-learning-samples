package me.jkong.thread.create;

/**
 * @author JKong
 * @version v1.0
 * @description 即集成Thread也实现Runnable方法，最后执行的为集成Thread类重写的run方法
 * @date 2020/3/3 8:30 上午.
 */
public class BothRunnableThread {

    public static void main(String[] args) {
        new Thread(new Runnable() {
            public void run() {
                System.out.println("implements Runnable.");
            }
        }) {
            @Override
            public void run() {
                System.out.println("extends Thread.");
            }
        }.start();
    }
}