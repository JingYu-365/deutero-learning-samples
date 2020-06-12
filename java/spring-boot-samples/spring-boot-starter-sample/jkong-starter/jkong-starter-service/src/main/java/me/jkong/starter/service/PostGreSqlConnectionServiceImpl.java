package me.jkong.starter.service;

import me.jkong.starter.api.IConnectionService;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 获取PostGreSql数据库连接
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/6/12 16:45.
 */
public class PostGreSqlConnectionServiceImpl implements IConnectionService {

    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        return null;
    }
}