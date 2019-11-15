package me.jkong.dm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author JKong
 * @version v1.0
 * @description 数据库查询语言
 * @date 2019/11/6 14:34.
 */
public class DataQueryLanguageTest {

    private Connection conn = null;
    private static final String OVER = "over...";

    @Before
    public void init() {
        conn = DataBaseInitializer.getConn();
    }


    @After
    public void close() throws SQLException {
        DataBaseInitializer.disConnect();
    }

    /**
     * 单表查询
     *
     * @throws SQLException 异常
     */
    @Test
    public void queryProduct() throws SQLException {
        // 查询语句
        String sql = "SELECT * FROM \"JKONG_TEST\".\"product23\" WHERE \"system_time\" >= to_date('2019-11-14 17:41:35','YYYY-MM-DD HH24:MI:SS')";
        executeSql(sql);
    }

    /**
     * 多表查询
     *
     * @throws SQLException 异常
     */
    @Test
    public void joinQueryProduct() throws SQLException {
        // 查询语句
        String sql = "SELECT P1.\"pro_name\" AS PNAME,P2.\"author\" AS PAUT " +
                "FROM \"JKONG_TEST\".\"product2\" P2 INNER JOIN \"JKONG_TEST\".\"product\" P1 " +
                "ON P1.\"productno\" = P2.\"productno\"";
        executeSql(sql);
    }


    public void executeSql(String sql) throws SQLException {
        // 创建语句对象
        Statement stmt = conn.createStatement();
        // 执行查询
        ResultSet rs = stmt.executeQuery(sql);
        // 显示结果集
        System.out.println(displayResultSet(rs));
        // 关闭结果集
        rs.close();
        // 关闭语句
        stmt.close();
    }


    public static List<Map<String, Object>> displayResultSet(ResultSet rs) throws SQLException {
        // 取得结果集元数据
        ResultSetMetaData rsmd = rs.getMetaData();
        // 取得结果集所包含的列数
        int numCols = rsmd.getColumnCount();
        List<Map<String, Object>> data = new ArrayList<>();
        // 显示结果集中所有数据
        while (rs.next()) {
            Map<String, Object> resultMap = new HashMap<>(16);
            for (int i = 1; i <= numCols; i++) {
                resultMap.put(rsmd.getColumnLabel(i), rs.getObject(i));
            }
            data.add(resultMap);
        }
        return data;
    }
}