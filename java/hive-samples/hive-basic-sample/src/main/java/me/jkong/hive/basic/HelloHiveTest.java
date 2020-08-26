package me.jkong.hive.basic;


import java.sql.*;

/**
 * 测试Hive连接
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/7/6 14:54.
 */
public class HelloHiveTest {

    private static String driverName = "org.apache.hive.jdbc.HiveDriver";
    private static String url = "jdbc:hive2://10.10.32.17:10001/default";
    private static String user = "hdfs";
    private static String password = "";

    private static Connection conn = null;
    private static Statement stmt = null;
    private static PreparedStatement ps = null;
    private static ResultSet rs = null;


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

    public static void createTableTest() throws SQLException {
        ps = conn.prepareStatement("create table if not exists db_hive.users2(id int,name string,age int)");
        ps.execute();
    }

    public static void main(String[] args) {
        try {
            init();
            createTableTest();
            close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}