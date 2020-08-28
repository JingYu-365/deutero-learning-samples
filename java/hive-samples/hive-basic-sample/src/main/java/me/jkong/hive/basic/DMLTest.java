package me.jkong.hive.basic;

import java.sql.*;

/**
 * 使用 JDBC 对数据进行 MDL 操作
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/7/6 17:39.
 */
public class DMLTest {
    private static String driverName = "org.apache.hive.jdbc.HiveDriver";
    private static String url = "jdbc:hive2://10.10.32.17:10001,10.10.32.18:10001,10.10.32.19:10001";
    private static String user = "hdfs";
    private static String password = "";

    private static Connection conn = null;
    private static Statement stmt = null;
    private static PreparedStatement ps = null;
    private static ResultSet rs = null;

    public static void main(String[] args) {
        try {
            init();
            // 1. 清空数据表
//            truncateTable();
            // 2. 新增数据（非常耗时，一条数据将近半分钟）
//            insertDataToTable();
            // 3. 装载本地数据到Hive中
//            loadDataFromLocalToTable();
            // 4. 装载HDFS数据到Hive中
//            loadDataFromHdfsToTable();
            // Hive 不支持 delete 操作
            // Hive 不支持 update 操作

            // 5. 查询数据
            queryDataFromTable();
            // 6. 统计总数（非常耗时，少量数据会达到一分钟以上）
//            countDataFromTable();

            close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    private static void deleteDataFromTable() throws SQLException {
        stmt = conn.createStatement();
        stmt.execute("delete * from table db_test.users where id = 1");
    }

    private static void countDataFromTable() throws SQLException {
        System.out.println("== count ==");
        String sql = "select count(1) from db_test.users";
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            System.out.println(rs.getInt(1));
        }
    }

    private static void queryDataFromTable() throws SQLException {
        System.out.println("== query ==");
        String sql = "SELECT  *  FROM jkong_big_data.user_info LIMIT 10 OFFSET 0";
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            System.out.println(rs.getString(1) + "\t" +
                    rs.getString(2) + "\t" +
                    rs.getInt(3)
            );
        }
    }

    private static void loadDataFromHdfsToTable() throws SQLException {
        System.out.println("== load data from local to hive ==");
        stmt = conn.createStatement();
        stmt.execute("load data inpath '/users.txt' into table db_test.users");
    }

    private static void loadDataFromLocalToTable() throws SQLException {
        System.out.println("== load data from local to hive ==");
        stmt = conn.createStatement();
        stmt.execute("load data local inpath '/tmp/users.txt' into table db_test.users");
    }

    private static void insertDataToTable() throws SQLException {
        System.out.println("== insert data ==");
        stmt = conn.createStatement();
        stmt.execute("insert into table db_test.users values('id1','user1',12)");
    }

    private static void truncateTable() throws SQLException {
        System.out.println("== truncate table ==");
        stmt = conn.createStatement();
        stmt.execute("truncate table db_test.users");
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