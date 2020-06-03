package me.jkong.dynamic.proxy.jdk.interceptor;

public class InterceptorImpl implements Interceptor {
    @Override
    public void doOtherThings() {
        System.out.println("还可以灵活地做其他事情");
    }
}