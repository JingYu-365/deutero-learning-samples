package me.jkong.dynamic.proxy.jdk.interceptorwithmethod;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author JKong
 */
public class TargetProxyTwo implements InvocationHandler {

    private Object target;

    private Interceptor interceptor;

    public TargetProxyTwo(Object target, Interceptor interceptor) {
        this.target = target;
        this.interceptor = interceptor;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return interceptor.intercept(new Invocation(target, method, args));
    }

    public static Object getProxyObj(Object target, Interceptor interceptor) {
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new TargetProxyTwo(target, interceptor)
        );
    }
}