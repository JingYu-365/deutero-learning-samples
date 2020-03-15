package me.jkong.dynamic.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author JKong
 * @version v1.0
 * @description JDK 原生动态代理实现
 * @date 2020/3/4 4:46 下午.
 */
public class JdkDynamicProxyDemo {
    public static void main(String[] args) {
        ElectricCar car = new ElectricCar();
        // 1.获取对应的ClassLoader
        ClassLoader classLoader = car.getClass().getClassLoader();
        // 2.获取ElectricCar 所实现的所有接口
        Class<?>[] interfaces = car.getClass().getInterfaces();
        // 3.设置一个来自代理传过来的方法调用请求处理器，处理所有的代理对象上的方法调用
        InvocationHandler invocationHandler = new InvocationHandlerImpl(car);
        /*
		  4.根据上面提供的信息，创建代理对象 在这个过程中，
             a.JDK会通过根据传入的参数信息动态地在内存中创建和.class 文件等同的字节码
             b.然后根据相应的字节码转换成对应的class，
             c.然后调用newInstance()创建实例
		 */
        Object proxyInstance = Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);

        Rechargable rechargable = (Rechargable) proxyInstance;
        rechargable.recharge();

        Vehicle vehicle = (Vehicle) proxyInstance;
        vehicle.drive();
    }
}