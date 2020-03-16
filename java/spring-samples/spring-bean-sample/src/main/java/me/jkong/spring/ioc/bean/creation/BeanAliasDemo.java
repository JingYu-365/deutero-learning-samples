package me.jkong.spring.ioc.bean.creation;

import me.jkong.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author JKong
 * @version v1.0
 * @description 验证Bean别名
 * @date 2020-03-15 21:18.
 */
public class BeanAliasDemo {
    public static void main(String[] args) {
        String location = "classpath:META-INF/bean-definition-context.xml";
        BeanFactory beanFactory = new ClassPathXmlApplicationContext(location);
        User user = beanFactory.getBean("user", User.class);
        User userJkong = beanFactory.getBean("userJkong", User.class);

        System.out.println(user == userJkong);
    }
}