package me.jkong.thread.create.wrongways;

/**
 * @author JKong
 * @version v1.0
 * @description 使用lambda表达式实现线程方式
 * @date 2020/3/3 8:48 上午.
 */
public class LambdaThreadDemo {
    public static void main(String[] args) {
        new Thread(()-> System.out.println("lambda thread.")).start();
    }
}