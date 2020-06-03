package me.jkong.dynamic.proxy.jdk.interceptorwithtarget;


import java.lang.reflect.InvocationTargetException;

/**
 * @author JKong
 */
@MethodName("work")
public class InterceptorImpl implements Interceptor {

    @Override
    public Object register(Object target) {
        return TargetProxy.getProxyObject(target, this);
    }

    @Override
    public Object intercept(Invocation invocation) throws InvocationTargetException, IllegalAccessException {
        if (invocation.getMethod().getName().equals("work")) {
            System.out.println("真的假的");
            return invocation.proceed();
        } else {
            return invocation.proceed();
        }

    }
}