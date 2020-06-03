package me.jkong.dynamic.proxy.jdk.basic;

import me.jkong.dynamic.proxy.jdk.Target;
import me.jkong.dynamic.proxy.jdk.TargetImpl;

/**
 * @author JKong
 */
public class Client {

    public static void main(String[] args) {
        Target target = new TargetImpl();
        target.work();
        System.out.println("-----------------------------");
        Target target1 = (Target) TargetProxy.getProxyObject(new TargetImpl());
        target1.work();
    }
}