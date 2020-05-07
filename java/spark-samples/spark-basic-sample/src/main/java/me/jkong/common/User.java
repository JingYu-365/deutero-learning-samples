package me.jkong.common;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * datasource entity
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/4/14 10:49.
 */
@Data
@Accessors(chain = true)
public class User implements Serializable {
    private long id;
    private String name;
    private long age;
}