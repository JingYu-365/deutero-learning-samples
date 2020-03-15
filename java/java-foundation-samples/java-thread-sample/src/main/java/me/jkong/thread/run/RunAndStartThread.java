package me.jkong.thread.run;

/**
 * @author JKong
 * @version v1.0
 * @description 调用线程的run方法和start方法的区别
 * @date 2020/3/3 7:47 下午.
 */
public class RunAndStartThread {
    public static void main(String[] args) {
        Runnable runnable = () -> {
            System.out.println(Thread.currentThread().getName());
        };

        runnable.run();
        new Thread(runnable).start();
        new Thread(runnable).run();
    }
}