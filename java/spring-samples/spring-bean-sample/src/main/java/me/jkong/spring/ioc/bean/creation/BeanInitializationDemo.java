package me.jkong.spring.ioc.bean.creation;

import me.jkong.spring.ioc.bean.factory.DefaultUserFactory;
import me.jkong.spring.ioc.bean.factory.UserFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * @author JKong
 * @version v1.0
 * @description Bean 的实例化
 * @date 2020-03-16 22:19.
 */
@Configuration // configuration class
public class BeanInitializationDemo {


    public static void main(String[] args) {
        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册 configuration class 配置
        applicationContext.register(BeanInitializationDemo.class);

        // 启动spring 应用上下文
        applicationContext.refresh();

        // 非延迟初始化的Bean，会在上下文启动时完成初始化；延迟初始化的Bean，会在第一次被使用时，初始化。
        System.out.println("\nSpring 上下文已启动...\n");

        // 依赖查找
        UserFactory userFactory = applicationContext.getBean(UserFactory.class);
        System.out.println(userFactory);

        System.out.println("Spring 上下文准备关闭...");
        // 关闭beanfactory容器
        applicationContext.close();
        System.out.println("Spring 上下文已经关闭...");
    }

    @Bean(initMethod = "initUserFactory", destroyMethod = "myDestroy")
    @Lazy(value = true)
    public UserFactory userFactory() {
        return new DefaultUserFactory();
    }
}