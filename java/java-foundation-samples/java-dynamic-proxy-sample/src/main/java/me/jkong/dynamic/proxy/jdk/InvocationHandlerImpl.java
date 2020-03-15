package me.jkong.dynamic.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author JKong
 * @version v1.0
 * @description 动态代理类
 * @date 2020/3/4 4:44 下午.
 */
public class InvocationHandlerImpl implements InvocationHandler {

    private ElectricCar car;

    public InvocationHandlerImpl(ElectricCar car) {
        this.car = car;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before execute method.");

        method.invoke(car, args);

        System.out.println("after execute method.");
        return null;
    }
}