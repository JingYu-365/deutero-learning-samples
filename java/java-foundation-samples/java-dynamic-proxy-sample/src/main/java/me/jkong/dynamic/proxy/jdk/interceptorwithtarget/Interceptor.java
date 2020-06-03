package me.jkong.dynamic.proxy.jdk.interceptorwithtarget;

import java.lang.reflect.InvocationTargetException;

public interface Interceptor {

    /**
     * 将需要被代理的类注册到代理中
     *
     * @param target 目标对象
     * @return o
     */
    Object register(Object target);

    /**
     * 拦截参数
     *
     * @param invocation e
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    Object intercept(Invocation invocation) throws InvocationTargetException, IllegalAccessException;
}