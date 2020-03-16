package me.jkong.spring.ioc.bean.creation;

import com.sun.org.apache.xml.internal.security.Init;
import me.jkong.spring.ioc.bean.factory.DefaultUserFactory;
import me.jkong.spring.ioc.bean.factory.UserFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

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

        // 依赖查找
        UserFactory userFactory = applicationContext.getBean(UserFactory.class);

        // 关闭beanfactory容器
        applicationContext.close();
    }

    @Bean(initMethod = "initUserFactory")
    public UserFactory userFactory() {
        return new DefaultUserFactory();
    }
}