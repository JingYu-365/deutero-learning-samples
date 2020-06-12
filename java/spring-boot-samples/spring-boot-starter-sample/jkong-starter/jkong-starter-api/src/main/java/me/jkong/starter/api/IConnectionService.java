package me.jkong.starter.api;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 获取数据连接抽象接口
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/6/12 16:01.
 */
public interface IConnectionService {

    /**
     * 获取数据库连接
     *
     * @return 数据库连接
     * @throws ClassNotFoundException 驱动类异常
     * @throws SQLException           数据库连接异常
     */
    Connection getConnection() throws ClassNotFoundException, SQLException;

}