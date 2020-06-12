package me.jkong.starter.service;

import me.jkong.starter.api.IConnectionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 获取MySQL的数据库连接
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/6/12 16:10.
 */
@Component
public class MysqlConnectionServiceImpl implements IConnectionService {

    private static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";

    @Value("me.jkong.url")
    private String url;

    @Value("me.jkong.user")
    private String user;

    @Value("me.jkong.password")
    private String password;

    @Override
    public Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName(DRIVER_CLASS);
        return DriverManager.getConnection(url, user, password);
    }
}