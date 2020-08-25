package me.jkong.flink.datasink.custom;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 自定义 MySQL sink
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/8/25 07:14.
 */
public class CustomSinkToMysql extends BaseSinkToMysql {

    private final String insertSql;
    private final String url;
    private final String username;
    private final String password;

    private CustomSinkToMysql(String url, String username, String password, String insertSql) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.insertSql = insertSql;
    }

    @Override
    public String initInsertSql() {
        return this.insertSql;
    }

    @Override
    public Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            System.out.println("mysql get connection has exception , msg = " + e.getMessage());
        }
        return con;
    }

    public static class CustomSinkToMysqlBuilder {
        private String insertSql;
        private String url;
        private String username;
        private String password;
        private static final CustomSinkToMysqlBuilder BUILDER = new CustomSinkToMysqlBuilder();

        private CustomSinkToMysqlBuilder() {
        }

        public static CustomSinkToMysqlBuilder getInstance() {
            return BUILDER;
        }

        public CustomSinkToMysqlBuilder insertSql(String insertSql) {
            this.insertSql = insertSql;
            return this;
        }

        public CustomSinkToMysqlBuilder url(String url) {
            this.url = url;
            return this;
        }

        public CustomSinkToMysqlBuilder username(String username) {
            this.username = username;
            return this;
        }

        public CustomSinkToMysqlBuilder password(String password) {
            this.password = password;
            return this;
        }

        public CustomSinkToMysql build() {

            // todo 验证 url, username, password, insertSql 是否为空

            return new CustomSinkToMysql(url, username, password, insertSql);
        }
    }
}
