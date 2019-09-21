package com.jkong.dubbo.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author JKong
 * @version v1.0
 * @description Person entity
 * @date 2019/8/1 11:33.
 */
@Data
@Accessors(chain = true)
public class PersonEntity implements Serializable {
    private static final long serialVersionUID = -4350425709412972900L;
    private String id;
    private String name;
    private String age;
    private String address;
}