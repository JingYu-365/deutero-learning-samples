package me.jkong.spring.ioc.overview.container;

import me.jkong.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Map;

/**
 * @author JKong
 * @version v1.0
 * @description 通过 {@link ApplicationContext} 实现 IOC容器
 * @date 2020-03-15 17:17.
 */
public class ApplicationContextAsIocContainerDemo {
    public static void main(String[] args) {
        // 创建Bean 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 将当前类作为配置中心
        applicationContext.register(ApplicationContextAsIocContainerDemo.class);
        // 启动应用上下文 ： refresh applicationContext
        applicationContext.refresh();

        lookupCollectionByBeanType(applicationContext);

        // 关闭应用上下文
        applicationContext.close();
    }

    @Bean
    public User user() {
        User user = new User();
        user.setAge(123);
        user.setName("asd");
        return user;
    }



    private static void lookupCollectionByBeanType(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> userBeanMap = listableBeanFactory.getBeansOfType(User.class);
            System.out.println(userBeanMap);
        }
    }
}