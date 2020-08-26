package me.jkong.dynamic.proxy.jdk.interceptorwithtarget;

import me.jkong.dynamic.proxy.jdk.Target;
import me.jkong.dynamic.proxy.jdk.TargetImpl;


/**
 * @author JKong
 */
public class Client {

    public static void main(String[] args) {
        Target target = (Target) new InterceptorImpl().register(new TargetImpl());
        target.work();
    }
}