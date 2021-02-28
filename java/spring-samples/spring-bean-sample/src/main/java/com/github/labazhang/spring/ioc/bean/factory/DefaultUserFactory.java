package com.github.labazhang.spring.ioc.bean.factory;

import com.github.labazhang.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author JKong
 * @version v1.0
 * @description 用户工厂
 * @date 2020-03-16 21:03.
 */
public class DefaultUserFactory implements UserFactory, InitializingBean, DisposableBean {

    @Override
    public User createUser() {
        User user = new User();
        user.setName("JKong234");
        user.setAge(25);
        return user;
    }

    // 基于@PostConstruct
    @PostConstruct
    public void init() {
        System.out.println("bean initialization.");
    }

    @Override
    public void initUserFactory() {
        System.out.println("自定义初始化方法：init user factory.");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean#afterPropertiesSet()：init user factory");
    }

    // =============== 销毁Bean ===============

    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean#destroy(): destroy user factory.");
    }

    @PreDestroy
    public void beforeDestroy() {
        System.out.println("自定义销毁方法：destroy user factory.");
    }

    public void myDestroy() {
        System.out.println("bean destroy.");
    }
}