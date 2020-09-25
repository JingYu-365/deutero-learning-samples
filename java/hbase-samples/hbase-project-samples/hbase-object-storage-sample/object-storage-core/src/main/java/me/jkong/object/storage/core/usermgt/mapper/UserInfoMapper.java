package me.jkong.object.storage.core.usermgt.mapper;

import me.jkong.object.storage.core.usermgt.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;

/**
 * 用户持久层
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/8/22 15:25.
 */
@Mapper
public interface UserInfoMapper {
    void addUser(@Param("userInfo") UserInfo userInfo);

    int updateUserInfo(@Param("userId") String userId, @Param("password") String password,
                       @Param("detail") String detail);

    int deleteUser(@Param("userId") String userId);

    @ResultMap("UserInfoResultMap")
    UserInfo getUserInfo(@Param("userId") String userId);

    UserInfo checkPassword(@Param("userName") String userName,
                           @Param("password") String password);

    @ResultMap("UserInfoResultMap")
    UserInfo getUserInfoByName(@Param("userName") String userName);
}
