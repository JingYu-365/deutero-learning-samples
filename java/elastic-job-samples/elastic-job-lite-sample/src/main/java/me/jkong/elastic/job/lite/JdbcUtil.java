package me.jkong.elastic.job.lite;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcUtil {
    /**
     * url
     */
    private static String URL = "jdbc:mysql://localhost:3306/job?characterEncoding=utf8&useSSL=false";
    /**
     * user
     */
    private static String USER = "root";
    /**
     * password
     */
    private static String PASSWORD = "123456";
    /**
     * 驱动程序类
     */
    private static String DRIVER = "com.mysql.jdbc.Driver";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void close(ResultSet rs, PreparedStatement ps, Connection con) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        if (con != null) {
                            try {
                                con.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    public static void executeUpdate(String sql, Object... obj) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            for (int i = 0; i < obj.length; i++) {
                ps.setObject(i + 1, obj[i]);
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(null, ps, con);
        }
    }

    public static List<Map<String, Object>> executeQuery(String sql, Object... obj) {
        Connection con = getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            for (int i = 0; i < obj.length; i++) {
                ps.setObject(i + 1, obj[i]);
            }
            rs = ps.executeQuery();
            List<Map<String, Object>> list = new ArrayList<>();
            int count = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                Map<String, Object> map = new HashMap<String,
                        Object>();
                for (int i = 0; i < count; i++) {
                    Object ob = rs.getObject(i + 1);
                    String key = rs.getMetaData().getColumnName(i
                            + 1);
                    map.put(key, ob);
                }
                list.add(map);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs, ps, con);
        }
        return null;
    }
}