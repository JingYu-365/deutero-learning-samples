package com.github.laba.mockito.common;

/**
 * TODO
 *
 * @author laba zhang
 */
public class UserDao {

    public UserInfo queryUserInfoById(String id) {
        throw new IllegalArgumentException("id is illegal.");
    }
}
