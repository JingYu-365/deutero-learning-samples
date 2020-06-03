package me.jkong.dynamic.proxy.jdk.interceptorwithmethod;

import java.lang.reflect.InvocationTargetException;

/**
 * @author JKong
 */
public class InterceptorImpl implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws InvocationTargetException, IllegalAccessException {
        if (invocation.getMethod().getName().equals("work")) {
            System.out.println("真的假的");
            return invocation.proceed();
        } else {
            return null;
        }

    }
}