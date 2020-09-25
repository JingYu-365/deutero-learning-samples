package me.jkong.object.storage.core.usermgt.entity;

import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;
import me.jkong.object.storage.core.utils.MD5Util;
import me.jkong.object.storage.core.utils.RandomUtil;

/**
 * 用户类
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/8/22 14:35.
 */
@Data
@Accessors(chain = true)
public class UserInfo {

    private String userId;
    private String userName;
    private String password;
    private String detail;
    private RoleEnum roleType;
    private Date createTime;

    public UserInfo(String userName, String password, RoleEnum roleType, String detail) {
        this.userId = RandomUtil.getUUID();
        this.userName = userName;
        this.password = MD5Util.getMd5Password(password);
        this.roleType = roleType;
        this.detail = detail;
        this.createTime = new Date();
    }

    public UserInfo() {
    }

}
