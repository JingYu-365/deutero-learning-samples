package me.jkong.dm;

import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author JKong
 * @version v1.0
 * @description 数据定义语言
 * @date 2019/11/6 14:33.
 */
public class DataDefinitionLanguageTest {

    private Connection conn = null;
    private static final String OVER = "over...";

    @Before
    public void init() {
        conn = DataBaseInitializer.getConn();
    }

    /*******************************************************************************************************************
     * 创建数据库
     *
     * @throws SQLException sql ex
     */
    @Test
    public void createSchema() throws SQLException {
        Statement statement = conn.createStatement();
        String sql = "CREATE SCHEMA jkong_test AUTHORIZATION SYSDBA;";
        statement.execute(sql);

        System.out.println(OVER);
        statement.close();
    }

    /**
     * 删除数据库
     *
     * @throws SQLException sql ex
     */
    @Test
    public void dropSchema() throws SQLException {
        Statement statement = conn.createStatement();
        String sql = "DROP SCHEMA jkong_test CASCADE";
        statement.execute(sql);

        System.out.println(OVER);
        statement.close();
    }

    /*******************************************************************************************************************
     * 创建数据表
     *
     * @throws SQLException sql ex
     */
    @Test
    public void createTable() throws SQLException {
        Statement statement = conn.createStatement();
        // 注意：
        // 1. 模式名一定全部转为大写
        // 2. 字段及表名需要使用'\"'括起来
        String sql = "CREATE TABLE \"JKONG_TEST\".\"product\"\n" +
                "(\"product_id\" INT IDENTITY(1,1) PRIMARY KEY," +
                "\"pro_name\" VARCHAR(50),\n" +
                "\"author\" VARCHAR(50),\n" +
                "\"publisher\" VARCHAR(50),\n" +
                "\"publishtime\" DATETIME(6),\n" +
                "\"product_subcategoryid\" INT,\n" +
                "\"productno\" VARCHAR(50),\n" +
                "\"satetystocklevel\" INT,\n" +
                "\"originalprice\" DECIMAL(22,6),\n" +
                "\"newprice\" DECIMAL(22,6),\n" +
                "\"discount\" DECIMAL(22,6),\n" +
                "\"description\" VARCHAR2(50),\n" +
                "\"photo\" VARCHAR(50),\n" +
                "\"pro_type\" VARCHAR2(50),\n" +
                "\"papertotal\" INT,\n" +
                "\"wordtotal\" INT,\n" +
                "\"sellstarttime\" DATETIME(6),\n" +
                "\"sellendtime\" DATETIME(6),\n" +
                "\"productid\" INT,\n" +
                "UNIQUE(\"productid\")) ";
        statement.execute(sql);

        System.out.println(OVER);
        statement.close();
    }

    /**
     * 删除表
     *
     * @throws SQLException SQL ex
     */
    @Test
    public void dropTable() throws SQLException {
        Statement statement = conn.createStatement();
        // 注意表名需要使用'\"'括起来
        // CASCADE：
        String sql = "DROP TABLE IF EXISTS \"JKONG_TEST\".\"product\" CASCADE";
        statement.execute(sql);
        System.out.println(OVER);
        statement.close();
    }


    /**
     * 修改表名
     *
     * @throws SQLException sql ex
     */
    @Test
    public void updateTable() throws SQLException {
        Statement statement = conn.createStatement();
        String sql = "ALTER TABLE \"JKONG_TEST\".\"product\" RENAME TO \"product_TMP\"";
        statement.execute(sql);
        System.out.println(OVER);
        statement.close();
    }

    /**
     * 添加字段
     *
     * @throws SQLException sql ex
     */
    @Test
    public void addField() throws SQLException {
        Statement statement = conn.createStatement();
        String sql = "ALTER TABLE \"JKONG_TEST\".\"product\" RENAME TO \"product_TMP\"";
        statement.execute(sql);
        System.out.println(OVER);
        statement.close();
    }

    // TODO: 2019/11/6 修改字段类型 及 名称

    // TODO: 2019/11/6 删除字段

    // TODO: 2019/11/6 创建主键

    // TODO: 2019/11/6 修改主键

    // TODO: 2019/11/6 添加索引

    // TODO: 2019/11/6 删除索引 

}