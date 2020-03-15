package me.jkong.spring.ioc.overview.dependency.lookup;

import me.jkong.spring.ioc.overview.annotation.Super;
import me.jkong.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * @author JKong
 * @version v1.0
 * @description 依赖查询demo
 * @date 2020-03-14 23:08.
 */
public class DependencyLookupDemo {
    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:META-INFO/dependency-lookup-context.xml");

        // 根绝注解查找Bean 集合
        lookupCollectionByAnnotation(beanFactory);

        // 根据类型查找bean集合
        lookupCollectionByBeanType(beanFactory);

        // 根据Bean的类型查找
        lookupByBeanTypeInRealTime(beanFactory);

        // 根据Bean名称查找
        lookupByBeanNameInLazyTime(beanFactory);
        lookupByBeanNameInRealTime(beanFactory);
    }

    private static void lookupCollectionByAnnotation(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> useBeanMap = (Map)listableBeanFactory.getBeansWithAnnotation(Super.class);
            System.out.println(useBeanMap);
        }
    }

    private static void lookupCollectionByBeanType(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> userBeanMap = listableBeanFactory.getBeansOfType(User.class);
            System.out.println(userBeanMap);
        }
    }

    private static void lookupByBeanTypeInRealTime(BeanFactory beanFactory) {
        User user = beanFactory.getBean(User.class);
        System.out.println(user);
    }

    private static void lookupByBeanNameInLazyTime(BeanFactory beanFactory) {
        ObjectFactory<User> factory = (ObjectFactory<User>) beanFactory.getBean("objectFactory");
        User user = factory.getObject();
        System.out.println(user);
    }

    private static void lookupByBeanNameInRealTime(BeanFactory beanFactory) {
        User user = (User) beanFactory.getBean("user");
        System.out.println(user);
    }
}