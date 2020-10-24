package me.jkong.mybatis.h2;

import org.apache.ibatis.annotations.Mapper;

/**
 * 用户操作Mapper
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/10/23 12:45.
 */
@Mapper
public interface UserMapper {

    /**
     * 插入用户信息
     *
     * @param user 用户信息
     * @return 执行条数
     */
    Integer insertUser(User user);

    /**
     * 根据ID获取用户信息
     *
     * @param id 用户Id
     * @return 用户信息
     */
    User findUserById(Long id);
}