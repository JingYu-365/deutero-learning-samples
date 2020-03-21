package me.jkong.spring.dependency.lookup;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * @author JKong
 * @version v1.0
 * @description Object provider 查找Bean
 * @date 2020-03-17 21:19.
 */
public class ObjectProviderDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ObjectProviderDemo.class);
        applicationContext.refresh();

        lookupByObjectProvider(applicationContext);

        // 关闭 context
        applicationContext.close();
    }

    private static void lookupByObjectProvider(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<String> provider = applicationContext.getBeanProvider(String.class);
        System.out.println(provider.getObject());
    }

    @Bean
    public String helloWorld() {
        return "Hello, World.";
    }

}