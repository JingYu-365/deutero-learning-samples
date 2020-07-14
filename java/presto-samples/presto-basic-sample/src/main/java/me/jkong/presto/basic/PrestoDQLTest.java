package me.jkong.presto.basic;

import java.sql.*;

/**
 * 简单测试 Presto 查询操作
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/7/12 11:07.
 */
public class PrestoDQLTest {


    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // 通过 Presto 查询 PostgreSQL
//        prestoOperatePostGreSql();

        // 通过 Presto 查询 MySQL
//        prestoOperateMySQL();

        // 通过 Presto 查询 Hive
//        prestoOperateHive();

        // 通过 Presto 实现 MySQL join PostgreSQL
        prestoOperateMySQLJoinPostGreSql();
    }

    private static void prestoOperateMySQLJoinPostGreSql() throws SQLException, ClassNotFoundException {
        // 1. 加载驱动
        Class.forName("io.prestosql.jdbc.PrestoDriver");
        // 2. 建立连接
        Connection connection = DriverManager.getConnection("jdbc:presto://10.10.32.17:8081", "root", null);
        // 3. 创建 Statement
        Statement statement = connection.createStatement();
        // 4. 定义执行SQL
        String sql = "select user.id, user.age, review.name  " +
                "from mysql.mysql_db.user_table user left join postgresql.test_hhh.review_db review " +
                "on user.id = review.id " +
                "where user.id = 1";
        // 5. 执行SQL
        ResultSet resultSet = statement.executeQuery(sql);
        // 6. 获取结果
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            int age = resultSet.getInt("age");
            String name = resultSet.getString("name");
            System.out.println("id: " + id + "\t" + "name: " + name + "\t" + "age: " + age);
        }

        // 7. 释放资源
        resultSet.close();
        statement.close();
        connection.close();
    }

    private static void prestoOperateMySQL() throws SQLException, ClassNotFoundException {
        // 1. 加载驱动
        Class.forName("io.prestosql.jdbc.PrestoDriver");
        // 2. 建立连接
        Connection connection = DriverManager.getConnection("jdbc:presto://10.10.32.17:8081", "root", null);
        // 3. 创建 Statement
        Statement statement = connection.createStatement();
        // 4. 定义执行SQL
        String sql = "select count(*) as count from mysql.mysql_db.user_table";
        // 5. 执行SQL
        ResultSet resultSet = statement.executeQuery(sql);
        // 6. 获取结果
        while (resultSet.next()) {
            int count = resultSet.getInt("count");
            System.out.println("num of row is: " + count);
        }

        // 7. 释放资源
        resultSet.close();
        statement.close();
        connection.close();
    }

    private static void prestoOperateHive() throws ClassNotFoundException, SQLException {
        // 1. 加载驱动
        Class.forName("io.prestosql.jdbc.PrestoDriver");
        // 2. 建立连接
        Connection connection = DriverManager.getConnection("jdbc:presto://10.10.32.17:8081", "root", null);
        // 3. 创建 Statement
        Statement statement = connection.createStatement();
        // 4. 定义执行SQL
        String sql = "select * from hive.db_test.users";
        // 5. 执行SQL
        ResultSet resultSet = statement.executeQuery(sql);
        // 6. 获取结果
        int id;
        String name;
        int age;
        while (resultSet.next()) {
            id = resultSet.getInt("id");
            name = resultSet.getString("name");
            age = resultSet.getInt("age");
            System.out.println("id: " + id + "\t" + "name: " + name + "\t" + "age: " + age);
        }

        // 7. 释放资源
        resultSet.close();
        statement.close();
        connection.close();
    }

    private static void prestoOperatePostGreSql() throws ClassNotFoundException, SQLException {
        // 1. 加载驱动
        Class.forName("io.prestosql.jdbc.PrestoDriver");
        // 2. 建立连接
        Connection connection = DriverManager.getConnection("jdbc:presto://10.10.32.17:8081/postgresql", "root", null);
        // 3. 创建 Statement
        Statement statement = connection.createStatement();
        // 4. 定义执行SQL
        String sql = "select count(*) as count from postgresql.test_hhh.review_db where age in (1)";
        // 5. 执行SQL
        ResultSet resultSet = statement.executeQuery(sql);
        // 6. 获取结果
        while (resultSet.next()) {
            int count = resultSet.getInt("count");
            System.out.println("num of row: " + count);
        }

        // 7. 释放资源
        resultSet.close();
        statement.close();
        connection.close();
    }
}