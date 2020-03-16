package me.jkong.spring.ioc.bean.creation;

import me.jkong.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author JKong
 * @version v1.0
 * @description Bean 实例化Demo
 * @date 2020-03-16 21:01.
 */
public class BeanInstantiationDemo {
    public static void main(String[] args) {
        String location = "classpath:META-INF/bean-instantiation-context.xml";
        BeanFactory beanFactory = new ClassPathXmlApplicationContext(location);

        // 通过静态方法实例化Bean
        User user = beanFactory.getBean("user-instantiation-by-static-method", User.class);
        // 通过Bean工厂实例化Bean
        User userInstantiationByFactory = beanFactory.getBean("user-instantiation-by-factory", User.class);
        // 通过 FactoryBean 实例化Bean
        User userInstantiationByFactoryBean = beanFactory.getBean("user-instantiation-by-factory-bean",User.class);


        System.out.println(user);
        System.out.println(userInstantiationByFactory);
        System.out.println(userInstantiationByFactoryBean);
        System.out.println(user == userInstantiationByFactory);
        System.out.println(user == userInstantiationByFactoryBean);
    }
}