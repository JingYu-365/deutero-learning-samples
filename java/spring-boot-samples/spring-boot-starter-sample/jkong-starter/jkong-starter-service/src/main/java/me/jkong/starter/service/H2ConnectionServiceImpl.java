package me.jkong.starter.service;

import me.jkong.starter.api.IConnectionService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 获取H2数据库的连接（默认）
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/6/12 16:14.
 */
public class H2ConnectionServiceImpl implements IConnectionService {

    /**
     * 数据库连接URL
     */
    private static final String JDBC_URL = "jdbc:h2:./h2/data";
    /**
     * 连接数据库时使用的用户名
     */
    private static final String USER = "jkong";
    /**
     * 连接数据库时使用的密码
     */
    private static final String PASSWORD = "";
    /**
     * 连接H2数据库时使用的驱动类，org.h2.Driver这个类是由H2数据库自己提供的，在H2数据库的jar包中可以找到
     */
    private static final String DRIVER_CLASS = "org.h2.Driver";

    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        // 加载驱动
        Class.forName(DRIVER_CLASS);
        // 根据连接URL，用户名，密码，获取数据库连接
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }

}