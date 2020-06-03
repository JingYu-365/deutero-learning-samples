package me.jkong.mybatis.basic;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {
    private int id;
    private String username;
    private String sex;
    private Date birthday;
    private String address;
}