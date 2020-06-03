package me.jkong.dynamic.proxy.jdk.interceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理类
 *
 * @author JKong
 */
public class TargetProxy implements InvocationHandler {

    private Object target;

    private Interceptor interceptor;

    public TargetProxy(Object target, Interceptor interceptor) {
        this.interceptor = interceptor;
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        interceptor.doOtherThings();
        return method.invoke(target, args);
    }

    /**
     * 获取代理对象的时候顺便把拦截逻辑对象也传过来
     *
     * @param interceptor
     * @return
     * @paramarget
     */
    public static Object getProxyObject(Object target, Interceptor interceptor) {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new TargetProxy(target, interceptor));
    }


}