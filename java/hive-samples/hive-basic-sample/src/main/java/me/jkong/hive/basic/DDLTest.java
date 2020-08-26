package me.jkong.hive.basic;

import java.sql.*;


/**
 * 测试 数据库定义语言 对Hive操作
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/7/6 17:39.
 */
public class DDLTest {
    private static String driverName = "org.apache.hive.jdbc.HiveDriver";
    private static String url = "jdbc:hive2://10.10.32.17:10001,10.10.32.18:10001,10.10.32.19:10001";
//    private static String url = "jdbc:hive2://10.10.32.17:2181/;serviceDiscoveryMode=zooKeeper;zooKeeperNamespace=hiveserver2";
    private static String user = "hdfs";
    private static String password = "";

    private static Connection conn = null;
    private static Statement stmt = null;
    private static PreparedStatement ps = null;
    private static ResultSet rs = null;

    public static void main(String[] args) {
        try {
            init();

//            // 1. 删除数据库
//            dropDatabase();
//            // 2. 创建数据库
//            createDatabase();
//            // 3. 查询数据库列表
            listDatabases();
//            // 4. 查询数据库描述信息
//            descDatabase();

            // 1. 删除表
//            dropTable();
            // 2. 创建表
//            createTable();
            // 3. 修改表名
//            renameTable();
            // 4. 表添加字段
//            addFiled();
            // 5. 表更新字段
//            updateField();
            // 6. 表移除字段
//            removeField();
            // 7. 查询表结构
            descTable();

            close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static void descTable() throws SQLException {
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("desc db_test.users2");
        while (rs.next()) {
            System.out.println(rs.getString(1) + " : " + rs.getString(2));
            // userid : string
            // name : string
            // age : int
        }
    }

    private static void removeField() throws SQLException {
        stmt = conn.createStatement();
        stmt.execute("alter table db_test.users replace columns(userId string,name string,age int)");
    }

    private static void updateField() throws SQLException {
        stmt = conn.createStatement();
        stmt.execute("alter table db_test.users change column id userId string");
        // hive> desc users;
        // OK
        // id                  	int
        // name                	string
        // age                 	int
        // desc                	string
        // Time taken: 0.183 seconds, Fetched: 4 row(s)
        // hive> desc users;
        // OK
        // userid              	string          // Hive 不区分大小写，以小写存储
        // name                	string
        // age                 	int
        // desc                	string
    }

    private static void addFiled() throws SQLException {
        stmt = conn.createStatement();
        stmt.execute("alter table db_test.users add columns(desc string)");
        // hive> desc users;
        // OK
        // id                  	int
        // name                	string
        // age                 	int
        // Time taken: 0.167 seconds, Fetched: 3 row(s)
        // hive> desc users;
        // OK
        // id                  	int
        // name                	string
        // age                 	int
        // desc                	string
    }

    private static void renameTable() throws SQLException {
        stmt = conn.createStatement();
        stmt.execute("ALTER TABLE db_test.new_users RENAME TO db_test.users");
    }

    private static void dropTable() throws SQLException {
        stmt = conn.createStatement();
        stmt.execute("drop table if exists db_test.users2");
    }

    /**
     * 在 Hive 中存在管理表和分区表
     * 创建管理表：
     * <pre>
     *  create table dept_partition(
     *   deptno int, dname string, loc string
     *  )
     *  row format delimited fields terminated by '\t';
     * </pre>
     * <p>
     * 创建分区表：
     * <pre>
     *  create table dept_partition(
     *   deptno int, dname string, loc string
     *  )
     *  partitioned by (month string)
     *  row format delimited fields terminated by '\t';
     * </pre>
     *
     * @throws SQLException
     */
    public static void createTable() throws SQLException {
        ps = conn.prepareStatement("create table if not exists db_test.users2(" +
                "id int,name string,age int) " +
                "row format delimited fields terminated by ',' " +
                "stored as textfile");
        ps.execute();
    }

    private static void descDatabase() throws SQLException {
        System.out.println("=== 数据库描述 ===");
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("desc database db_test");
        while (rs.next()) {
            System.out.println(rs.getString(1));
            System.out.println(rs.getString(2));
        }
    }

    private static void listDatabases() throws SQLException {
        System.out.println("=== 数据库名称 ===");
        Statement statement = conn.createStatement();
        ResultSet catalogs = conn.getMetaData().getCatalogs();
        while (catalogs.next()) {
            System.out.println(catalogs.getString(1));
        }

        ResultSet rs = statement.executeQuery("show databases");
        ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();
        for (int i = 0; i < metaData.getColumnCount(); i++) {
            System.out.println(metaData.getColumnName(i + 1));
        }
        System.out.println();
        while (rs.next()) {
            System.out.println(rs.getString(1));
            // db_hive
            // db_test
            // default
        }
    }

    public static void dropDatabase() throws SQLException {
        stmt = conn.createStatement();
        stmt.execute("drop database if exists db_test");
    }

    public static void createDatabase() throws SQLException {
        stmt = conn.createStatement();
        stmt.execute("create database if not exists db_test");
    }

    public static void init() throws ClassNotFoundException, SQLException {
        Class.forName(driverName);
        conn = DriverManager.getConnection(url, user, password);
    }

    public static void close() throws SQLException {
        if (stmt != null) {
            stmt.close();
        }
        if (ps != null) {
            ps.close();
        }
        if (conn != null) {
            conn.close();
        }
    }
}