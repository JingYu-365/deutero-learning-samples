package me.jkong.dm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.*;

/**
 * @author JKong
 * @version v1.0
 * @description 数据操作语言
 * @date 2019/11/6 14:34.
 */
public class DataManipulationLanguageTest {

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
     * 添加数据
     *
     * @throws SQLException
     */
    @Test
    public void insertTable() throws SQLException {
        // 插入数据语句
        // 注意点：
        // 1. 列名使用双引号括起来

        // 违反表[product]唯一性约束
        String sql = "INSERT INTO \"JKONG_TEST\".\"product\"(" +
                "\"pro_name\"," +
                "\"author\"," +
                "\"publisher\"," +
                "\"publishtime\"," +
                "\"product_subcategoryid\"," +
                "\"productno\"," +
                "\"satetystocklevel\"," +
                "\"originalprice\") " +
                "VALUES(?,?,?,?,?,?,?,?);";
        // 创建语句对象
        PreparedStatement pstmt = conn.prepareStatement(sql);
        // 为参数赋值
        pstmt.setString(1, "三国演义");
        pstmt.setString(2, "罗贯中");
        pstmt.setString(3, "中华书局");
        pstmt.setDate(4, Date.valueOf("2005-04-01"));
        pstmt.setInt(5, 4);
        pstmt.setString(6, "9787101046123");
        pstmt.setInt(7, 10);
        pstmt.setBigDecimal(8, new BigDecimal(19.0000));
        // 执行语句
        pstmt.executeUpdate();
        // 关闭语句
        pstmt.close();
    }

    @Test
    public void insertTableDefaultField() throws SQLException {
        // 插入数据语句
        // 注意点：
        // 1. 列名使用双引号括起来

        // 违反表[product]唯一性约束
        String sql = "INSERT INTO \"JKONG_TEST\".\"product23\"(" +
                "\"pro_name\") " +
                "VALUES(?);";
        // 创建语句对象
        PreparedStatement pstmt = conn.prepareStatement(sql);
        // 为参数赋值
        pstmt.setString(1, "三国演义");
        // 执行语句
        pstmt.executeUpdate();
        // 关闭语句
        pstmt.close();
    }

    /**
     * 更新数据
     *
     * @throws SQLException 异常
     */
    @Test
    public void updateTable() throws SQLException {
        // 更新数据语句
        String sql = "UPDATE \"JKONG_TEST\".\"product\" SET \"pro_name\" = ? WHERE \"product_id\" = 1;";
        // 创建语句对象
        PreparedStatement pstmt = conn.prepareStatement(sql);
        // 为参数赋值
        pstmt.setString(1, "三国演义（上） ");
        // 执行语句
        pstmt.executeUpdate();
        // 关闭语句
        pstmt.close();
    }

    /**
     * 删除数据
     *
     * @throws SQLException 异常
     */
    @Test
    public void deleteTable() throws SQLException {
        // 删除数据语句
        String sql = "DELETE FROM \"JKONG_TEST\".\"product\" WHERE \"product_id\" = 1;";
        // 创建语句对象
        Statement stmt = conn.createStatement();
        // 执行语句
        stmt.executeUpdate(sql);
        // 关闭语句
        stmt.close();
    }

    /**
     * 基表数据删除语句
     * <p>
     * TRUNCATE TABLE [<模式名>.]<表名>[PARTITION [(]<分区名>[)]];
     *
     * @throws SQLException sql ex
     */
    @Test
    public void dropField() throws SQLException {
        Statement statement = conn.createStatement();
        String sql = "TRUNCATE TABLE \"JKONG_TEST\".\"product\"";
        statement.execute(sql);
        System.out.println(OVER);
        statement.close();
    }
}