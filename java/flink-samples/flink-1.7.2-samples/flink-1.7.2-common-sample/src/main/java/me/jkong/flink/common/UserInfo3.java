package me.jkong.flink.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户实体
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/8/25 11:56.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo3 {

    private Double id;
    private String name;
    private Integer age;
    private Boolean gender;
    private String birth;
    private String desc;

}