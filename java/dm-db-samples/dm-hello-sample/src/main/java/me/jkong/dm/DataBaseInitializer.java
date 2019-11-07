package me.jkong.dm;

import java.sql.*;

/**
 * @author JKong
 * @version v1.0
 * @description DM 数据库测试
 * @date 2019/11/6 10:53.
 */
public class DataBaseInitializer {
    /**
     * 定义 DM JDBC 驱动串
     */
    private static final String JDBC_STRING = "dm.jdbc.driver.DmDriver";
    /**
     * 定义 DM URL 连接串
     */
    private static final String URL_STRING = "jdbc:dm://10.10.32.11:5236";
    /**
     * 定义连接用户名
     */
    private static final String USER_NAME = "SYSDBA";
    /**
     * 定义连接用户口令
     */
    private static final String PASSWORD = "wingtestdm8";
    /**
     * 定义连接对象
     */
    private static Connection conn = null;

    static {
        try {
            loadJdbcDriver();
            connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载 JDBC 驱动程序
     *
     * @throws SQLException 异常
     */
    private static void loadJdbcDriver() throws SQLException {
        try {
            System.out.println("Loading JDBC Driver...");
            // 加载 JDBC 驱动程序
            Class.forName(JDBC_STRING);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Load JDBC Driver Error : " + e.getMessage());
        } catch (Exception ex) {
            throw new SQLException("Load JDBC Driver Error : " + ex.getMessage());
        }
    }


    /**
     * 连接 DM 数据库
     *
     * @throws SQLException 异常
     */
    private static void connect() throws SQLException {
        try {
            System.out.println("Connecting to DM Server...");
            // 连接 DM 数据库
            conn = DriverManager.getConnection(URL_STRING, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            throw new SQLException("Connect to DM Server Error : "
                    + e.getMessage());
        }
    }

    public static Connection getConn() {
        return conn;
    }


    /**
     * 关闭连接
     *
     * @throws SQLException 异常
     */
    public static void disConnect() throws SQLException {
        try {
            // 关闭连接
            conn.close();
        } catch (SQLException e) {
            throw new SQLException("close connection error : " + e.getMessage());
        }
    }

}