package com.github.labazhang.spring.ioc.bean.creation;

import com.github.labazhang.spring.ioc.bean.factory.DefaultUserFactory;
import com.github.labazhang.spring.ioc.bean.factory.UserFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author JKong
 * @version v1.0
 * @description 外部Bean交给BeanFactory托管
 * @date 2020-03-17 20:47.
 */
public class SingletonBeanRegistrationDemo {
    public static void main(String[] args) {
        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        // 创建一个对象，并将对象交给BeanFactory来托管
        UserFactory userFactory = new DefaultUserFactory();
        beanFactory.registerSingleton("userFactory", userFactory);

        // 启动spring 应用上下文
        applicationContext.refresh();

        // 依赖查找
        UserFactory userFactory2 = applicationContext.getBean(UserFactory.class);
        System.out.println("userFactory == userFactory2: " + (userFactory == userFactory2));

        // 关闭beanfactory容器
        applicationContext.close();
    }
}