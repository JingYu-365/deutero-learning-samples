package com.xinfago.myabtis.plus.basic.entity;

import lombok.Data;

/**
 * 用户信息
 *
 * @author xinfago
 */
@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
