package me.jkong.dynamic.proxy.jdk.interceptorwithmethod;

import java.lang.reflect.InvocationTargetException;

/**
 * @author JKong
 */
public interface Interceptor {
    Object intercept(Invocation invocation) throws InvocationTargetException, IllegalAccessException;
}