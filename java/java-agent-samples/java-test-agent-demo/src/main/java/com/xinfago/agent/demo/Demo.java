package com.xinfago.agent.demo;

/**
 * 用来测试
 *
 * @author Zhang Duanhe
 * @since 2021/10/11
 */
public class Demo {

    /**
     * 执行指令：
     * java -javaagent:D:\test_git\deutero-learning-samples\java\java-agent-samples\java-test-agent-demo\target\java-test-agent-1.0-SNAPSHOT.jar=zhang -jar D:\test_git\deutero-learning-samples\java\java-agent-samples\java-test-agent-demo\target\java-test-agent-demo-1.0-SNAPSHOT.jar
     */
    public static void main(String[] args) {
        sayHello();
        sayHello2("hello world -2 !!!");
        sayHello3("hello world -3 !!!");
    }

    public static void sayHello() {
        try {
            Thread.sleep(2000);
            System.out.println("hello world -1 !!!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sayHello2(String hello) {
        try {
            Thread.sleep(1000);
            System.out.println(hello);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void sayHello3(String hello) {
        try {
            Thread.sleep(1000);
            System.out.println(hello);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
