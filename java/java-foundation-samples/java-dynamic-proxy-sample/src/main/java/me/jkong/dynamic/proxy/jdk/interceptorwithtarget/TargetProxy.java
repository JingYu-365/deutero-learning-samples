package me.jkong.dynamic.proxy.jdk.interceptorwithtarget;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
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
        MethodName methodName = this.interceptor.getClass().getAnnotation(MethodName.class);
        if (methodName == null) {
            throw new NullPointerException("拦截器注解方法名字为空");
        }
        // 验证当前方法是否为需要拦截的方法
        String name = methodName.value();
        if (name.equals(method.getName())) {
            return interceptor.intercept(new Invocation(target, method, args));
        }
        return method.invoke(target, args);
    }

    /**
     * 获取代理对象的时候顺便把拦截逻辑对象也传过来
     *
     * @param target      目标类
     * @param interceptor 拦截器
     * @return 代理对象
     */
    public static Object getProxyObject(Object target, Interceptor interceptor) {
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new TargetProxy(target, interceptor)
        );
    }
}