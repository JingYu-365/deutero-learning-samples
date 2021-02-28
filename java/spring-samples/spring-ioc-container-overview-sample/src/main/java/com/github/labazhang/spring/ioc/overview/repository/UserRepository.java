package com.github.labazhang.spring.ioc.overview.repository;

import com.github.labazhang.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * @author JKong
 * @version v1.0
 * @description 依赖注入用户仓库
 * @date 2020-03-15 10:32.
 */
public class UserRepository {
    private List<User> users;

    private BeanFactory beanFactory;

    private ObjectFactory<ApplicationContext> objectFactory;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public ObjectFactory<ApplicationContext> getObjectFactory() {
        return objectFactory;
    }

    public void setObjectFactory(ObjectFactory<ApplicationContext> objectFactory) {
        this.objectFactory = objectFactory;
    }
}