package com.github.labazhang.spring.ioc.bean.creation;

import com.github.labazhang.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author JKong
 * @version v1.0
 * @description 根据注解进行Bean注入
 * @date 2020-03-15 21:40.
 */
// 使用 @Import 注解注入Bean
@Import(AnnotationBeanDefinitionDemo.Config.class)
public class AnnotationBeanDefinitionDemo {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(Config.class);

        // 通过 api 方式注入bean对象
        registerUserBeanUseApiType(applicationContext, "JKong123");
        registerUserBeanUseApiType(applicationContext);

        applicationContext.refresh();

        // 按照类型依赖查找
        System.out.println("Config 类型的所有 Bean 数量：" + applicationContext.getBeansOfType(Config.class).size());
        System.out.println("User 类型的所有 Bean 数量：" + applicationContext.getBeansOfType(User.class).size());

        // 关闭 context
        applicationContext.close();
    }

    private static void registerUserBeanUseApiType(BeanDefinitionRegistry registry, String beanName) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        beanDefinitionBuilder.addPropertyValue("name", "Jkong").addPropertyValue("age", 25);

        if (StringUtils.hasText(beanName)) {
            registry.registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());
        } else {
            BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinitionBuilder.getBeanDefinition(), registry);
        }
    }

    private static void registerUserBeanUseApiType(BeanDefinitionRegistry registry) {
        registerUserBeanUseApiType(registry, null);
    }


    /**
     * 使用 @Component 注入 Bean 对象
     */
    @Component
    public static class Config {
        // 使用 @Bean 注入Bean对象
        @Bean(name = {"user, jkong-user"})
        public User user() {
            User user = new User();
            user.setName("JKong365");
            user.setAge(25);
            return user;
        }
    }
}