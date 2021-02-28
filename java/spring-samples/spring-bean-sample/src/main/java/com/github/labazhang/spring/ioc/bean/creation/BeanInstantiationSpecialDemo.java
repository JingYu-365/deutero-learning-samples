package com.github.labazhang.spring.ioc.bean.creation;

import com.github.labazhang.spring.ioc.bean.factory.DefaultUserFactory;
import com.github.labazhang.spring.ioc.bean.factory.UserFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Iterator;
import java.util.ServiceLoader;

import static java.util.ServiceLoader.load;

/**
 * @author JKong
 * @version v1.0
 * @description Bean 实例化Demo
 * @date 2020-03-16 21:01.
 */
public class BeanInstantiationSpecialDemo {
    public static void main(String[] args) {
        String location = "classpath:META-INF/special-bean-instantiation-context.xml";
        BeanFactory beanFactory = new ClassPathXmlApplicationContext(location);

        ServiceLoader<UserFactory> serviceLoader =
                beanFactory.getBean("userFactoryServiceLoader", ServiceLoader.class);
        demoServiceLoader(serviceLoader);

        demoServiceLoader();

        // 通过 AutowireCapableBeanFactory#createBean(java.lang.Class, int, boolean)
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(location);
        AutowireCapableBeanFactory autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
        DefaultUserFactory userFactory = autowireCapableBeanFactory.createBean(DefaultUserFactory.class);
        System.out.println(userFactory.createUser());
        applicationContext.close();

    }

    private static void demoServiceLoader(ServiceLoader<UserFactory> serviceLoader) {
        Iterator<UserFactory> userFactoryIterator = serviceLoader.iterator();
        while (userFactoryIterator.hasNext()) {
            System.out.println(userFactoryIterator.next().createUser());
        }
    }

    private static void demoServiceLoader() {
        ServiceLoader<UserFactory> serviceLoader =
                load(UserFactory.class, Thread.currentThread().getContextClassLoader());
        demoServiceLoader(serviceLoader);
    }
}