package me.jkong.dynamic.proxy.jdk.interceptor;

import me.jkong.dynamic.proxy.jdk.Target;
import me.jkong.dynamic.proxy.jdk.TargetImpl;

/**
 * 测试客户端
 *
 * @author JKong
 */
public class Client {

    public static void main(String[] args) {
        Target target = new TargetImpl();
        target.work();
        System.out.println("-----------------------------");
        Interceptor interceptor = new InterceptorImpl();
        Target target1 = (Target) TargetProxy.getProxyObject(new TargetImpl(), interceptor);
        target1.work();

        System.out.println("-----------------------------");
        Interceptor interceptor1 = new Interceptor() {
            @Override
            public void doOtherThings() {
                System.out.println("换个拦截方式？");
            }
        };
        Target target2 = (Target) TargetProxy.getProxyObject(new TargetImpl(), interceptor1);
        target2.work();
    }
}