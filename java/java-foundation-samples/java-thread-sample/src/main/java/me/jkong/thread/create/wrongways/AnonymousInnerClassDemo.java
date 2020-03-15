package me.jkong.thread.create.wrongways;

/**
 * @author JKong
 * @version v1.0
 * @description 使用内部类方式实现线程
 * @date 2020/3/3 8:47 上午.
 */
public class AnonymousInnerClassDemo {
    public static void main(String[] args) {
        new Thread() {
            @Override
            public void run() {
                System.out.println("thread inner class");
            }
        }.start();

        new Thread(new Runnable() {
            public void run() {
                System.out.println("runnable inner class");
            }
        }).start();
    }
}