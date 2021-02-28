package com.github.labazhang.spring.ioc.overview.container;

import com.github.labazhang.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

import java.util.Map;

/**
 * @author JKong
 * @version v1.0
 * @description 通过 {@link BeanFactory} 实现 IOC容器
 * @date 2020-03-15 17:17.
 */
public class BeanFactoryAsIocContainerDemo {
    public static void main(String[] args) {
        // 创建Bean 容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 定义配置文件读取
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        // 加载配置文件
        String location = "classpath:META-INF/dependency-lookup-context.xml";
        int beanDefinitions = reader.loadBeanDefinitions(location);
        System.out.println("Bean 配置数量：" + beanDefinitions);

        lookupCollectionByBeanType(beanFactory);
    }


    private static void lookupCollectionByBeanType(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> userBeanMap = listableBeanFactory.getBeansOfType(User.class);
            System.out.println(userBeanMap);
        }
    }
}