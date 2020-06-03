package me.jkong.dynamic.proxy.jdk.interceptorwithmethod;

import me.jkong.dynamic.proxy.jdk.Target;
import me.jkong.dynamic.proxy.jdk.TargetImpl;

/**
 * @author JKong
 */
public class Client {

    public static void main(String[] args) {
        Target target = (Target) TargetProxyTwo.getProxyObj(new TargetImpl(), new InterceptorImpl());
        target.work();
    }
}