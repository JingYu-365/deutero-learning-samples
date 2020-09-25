package me.jkong.object.storage.core.usermgt.service;

import me.jkong.object.storage.core.usermgt.entity.UserInfo;

/**
 * 用户管理service
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/8/22 15:53.
 */
public interface IUserService {

    /**
     * 添加用户
     *
     * @param userInfo 用户信息
     * @return true：操作成功
     */
    public boolean addUser(UserInfo userInfo);

    /**
     * 更新用户信息
     *
     * @param userId   用户ID
     * @param password 密码
     * @param detail   描述信息
     * @return true：操作成功
     */
    public boolean updateUserInfo(String userId, String password, String detail);

    /**
     * 删除用户
     *
     * @param userId 用户ID
     * @return true: 操作成功
     */
    public boolean deleteUser(String userId);

    /**
     * 获取用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    public UserInfo getUserInfo(String userId);

    /**
     * 验证密码是否正确
     *
     * @param userName 用户名
     * @param password 密码
     * @return 用户信息
     */
    public UserInfo checkPassword(String userName, String password);

    /**
     * 根据用户名获取用户信息
     *
     * @param userName 用户名
     * @return 用户信息
     */
    public UserInfo getUserInfoByName(String userName);
}
