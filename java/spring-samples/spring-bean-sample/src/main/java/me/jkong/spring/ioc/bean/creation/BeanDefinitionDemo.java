package me.jkong.spring.ioc.bean.creation;

import me.jkong.spring.ioc.overview.domain.User;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.GenericBeanDefinition;

/**
 * @author JKong
 * @version v1.0
 * @description {@link BeanDefinition} 实现方式
 * 1. 通过 BeanDefinitionBuilder
 * 2. 通过 AbstractBeanDefinition 以及派生类
 * @date 2020-03-15 18:32.
 */
public class BeanDefinitionDemo {
    public static void main(String[] args) {
        // 1. 通过 BeanDefinitionBuilder 创建 BeanDefinition
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        // 设置Bean属性
        builder.addPropertyValue("name", "JKong365")
                .addPropertyValue("age", 1);
        // 构建Bean
        AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();

        System.out.println("===============");

        // 2. 通过 AbstractBeanDefinition 创建 BeanDefinition
        beanDefinition = new GenericBeanDefinition();
        // 设置Bean类型
        beanDefinition.setBeanClass(User.class);
        // 设置Bean 属性
        MutablePropertyValues propertyValues = new MutablePropertyValues();
//        propertyValues.addPropertyValue("name", "JKong365");
//        propertyValues.addPropertyValue("age", 1);
        propertyValues.add("name", "JKong365")
                .add("age", 1);
        beanDefinition.setPropertyValues(propertyValues);


    }
}