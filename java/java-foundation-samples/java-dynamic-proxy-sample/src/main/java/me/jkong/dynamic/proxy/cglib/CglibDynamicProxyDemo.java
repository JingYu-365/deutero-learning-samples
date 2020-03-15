package me.jkong.dynamic.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;

import java.security.spec.ECField;

/**
 * @author JKong
 * @version v1.0
 * @description 通过cglib实现动态代理
 * @date 2020/3/4 7:38 下午.
 */
public class CglibDynamicProxyDemo {
    public static void main(String[] args) {
        HackerInterceptor hacker = new HackerInterceptor();

        //cglib 中加强器，用来创建动态代理
        Enhancer enhancer = new Enhancer();
        //设置要创建动态代理的类
        enhancer.setSuperclass(Programmer.class);
        // 设置回调，这里相当于是对于代理类上所有方法的调用，都会调用CallBack，而Callback则需要实行intercept()方法进行拦截
        enhancer.setCallback(hacker);

        Programmer programmer = (Programmer) enhancer.create();
        programmer.code();
    }
}