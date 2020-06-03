package me.jkong.dynamic.proxy.jdk.basic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author JKong
 */
public class TargetProxy implements InvocationHandler {

    private Object target;

    public TargetProxy(Object target) {
        this.target = target;
    }

    /**
     * 缺点   代理需要做的事情不是很灵活。直接在这里面写死了。
     *
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("代理在前面做事情了");
        try {
            return method.invoke(target, args);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    public static Object getProxyObject(Object target) {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new TargetProxy(target));
    }
}