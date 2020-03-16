package me.jkong.spring.ioc.overview.dependency.injection;

import me.jkong.spring.ioc.overview.repository.UserRepository;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;

/**
 * @author JKong
 * @version v1.0
 * @description 依赖查询demo
 * @date 2020-03-14 23:08.
 */
public class DependencyInjectionDemo {
    public static void main(String[] args) {
        // 1. 配置 XML 配置文件
        // 2. 启动 Spring 应用上下文
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:META-INFO/dependency-injection-context.xml");
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:META-INF/dependency-injection-context.xml");

        // 自定义bean
        UserRepository userRepository = beanFactory.getBean(UserRepository.class);
        System.out.println(userRepository.getUsers());

        // 依赖注入（內建依赖）
        System.out.println(userRepository.getBeanFactory());
        // userRepository.getBeanRepository -> DefaultListableBeanFactory
        // System.out.println(userRepository.getBeanFactory() == beanFactory);

        ObjectFactory userFactory = userRepository.getObjectFactory();
        System.out.println(userFactory.getObject() == beanFactory);

        // 容器內建 Bean
        Environment applicationContextBean = beanFactory.getBean(Environment.class);
        System.out.println(applicationContextBean.getActiveProfiles());

    }

}