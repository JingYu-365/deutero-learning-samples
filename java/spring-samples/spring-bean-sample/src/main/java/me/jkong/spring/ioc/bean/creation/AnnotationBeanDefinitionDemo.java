package me.jkong.spring.ioc.bean.creation;

import me.jkong.spring.ioc.overview.domain.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

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
        applicationContext.refresh();

        // 按照类型依赖查找
        System.out.println("Config 类型的所有 Bean 数量：" + applicationContext.getBeansOfType(Config.class).size());
        System.out.println("User 类型的所有 Bean 数量：" + applicationContext.getBeansOfType(User.class).size());


        // 关闭 context
        applicationContext.close();
    }


    // 使用 @Component 注入 Bean 对象
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