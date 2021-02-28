package com.github.labazhang.spring.ioc.bean.factory;

import com.github.labazhang.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author JKong
 * @version v1.0
 * @description 通过 FactoryBean 实例化 Bean
 * @date 2020-03-16 21:14.
 */
public class UserFactoryBean implements FactoryBean<User> {

    @Override
    public User getObject() throws Exception {
        return User.createUser();
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }
}