package me.jkong.thread.create.wrongways;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author JKong
 * @version v1.0
 * @description 使用timer实现线程创建及执行
 * @date 2020/3/3 8:40 上午.
 */
public class TimerTaskDemo {
    public static void main(String[] args) {
        try {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                }
            }, 1000, 100);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}