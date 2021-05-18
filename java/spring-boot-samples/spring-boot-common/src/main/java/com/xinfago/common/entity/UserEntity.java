package com.xinfago.common.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户信息
 *
 * @author xinfago
 */
@Data
@Accessors(chain = true)
public class UserEntity {
    private Long id;
    private String userName;
    private String password;
    private String nickName;
    private Long createTime;
}
