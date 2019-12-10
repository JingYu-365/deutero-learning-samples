package me.jkong.kingbase.pool;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author JKong
 * @version v1.0
 * @description 数据库配置类
 * @date 2019/11/13 15:26.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class DefaultDataBaseConfig {

    /**
     * 驱动
     */
    private String driver;

    /**
     * 数据库连接
     */
    private String url;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 初始连接大小
     */
    private Integer initialSize;

    /**
     * 最大活跃数
     */
    private Integer maxActive;

    /**
     * 数据库类型
     */
    private String dataBaseType;
}