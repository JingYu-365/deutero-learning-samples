package me.jkong.dm;

import org.junit.Before;
import org.junit.Test;

import java.sql.*;

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
                "(\"product_id\" INT IDENTITY(1,1) CONSTRAINT \"product_id_pri\" NOT CLUSTER PRIMARY KEY," +        // 给主键设置约束名，方便主键删除
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
                "UNIQUE(\"productno\")) ";
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
     * ALTER TABLE [<模式名>.]<表名> <修改表定义子句>
     * <修改表定义子句> ::=
     * ADD [COLUMN] <列定义>|
     * ADD [COLUMN] (<列定义> {,<列定义>})|
     *
     * DROP [COLUMN] <列名> [RESTRICT | CASCADE] |
     *
     * @throws SQLException sql ex
     */
    @Test
    public void addField() throws SQLException {
        Statement statement = conn.createStatement();
        String sql = "ALTER TABLE \"JKONG_TEST\".\"product\" ADD COLUMN \"product_TMP\" VARCHAR(255)";
//        String sql = "ALTER TABLE \"JKONG_TEST\".\"product\" DROP COLUMN \"product_TMP\" CASCADE";
        statement.execute(sql);
        System.out.println(OVER);
        statement.close();
    }

    /**
     * 修改字段唯一性
     *
     * ALTER TABLE [<模式名>.]<表名> <修改表定义子句>
     * <修改表定义子句> ::=
     * ADD [CONSTRAINT [<约束名>] ] <表级约束子句> [<CHECK 选项>] [<失效生效选项>]|
     * DROP CONSTRAINT <约束名> [RESTRICT | CASCADE] |
     *
     * @throws SQLException
     */
    @Test
    public void alterUniqueField() throws SQLException {
        Statement statement = conn.createStatement();
        // 被修改的字段不存在会抛错：列[product_TMP]不存在
//        String sql = "ALTER TABLE \"JKONG_TEST\".\"product\" ADD CONSTRAINT \"TEST_CONSTRAINT\" UNIQUE (\"rpoduct_TMP\")";
//        String sql = "ALTER TABLE \"JKONG_TEST\".\"product\" DROP CONSTRAINT \"TEST_CONSTRAINT\ CASCADE" ;

//        String sql = "ALTER TABLE \"JKONG_TEST\".\"product\" ADD CONSTRAINT \"product_id_pri\" NOT CLUSTER  PRIMARY KEY(\"product_id\")";
        // alter table "JKONG_TEST"."product" modify constraint "CONS134218766" to primary key ("pro_name")
        String sql = "alter table \"JKONG_TEST\".\"product\" DROP constraint \"product_id_pri\"" ;
        statement.execute(sql);
        System.out.println(OVER);
        statement.close();
    }

    @Test
    public void testGetPK() throws SQLException{
        DatabaseMetaData dbmd= conn.getMetaData();
        String[] types = {"TABLE"};
        ResultSet rs = dbmd.getTables(null, null, "%", types);
        ResultSetMetaData rmd = rs.getMetaData();
        while(rs.next()){
            //3对应的位置就是表名
            String tableName = rs.getString(3);
            System.out.println(tableName);
            //根据表名获得主键结果集
            ResultSet pks = dbmd.getPrimaryKeys(null, null, tableName);
            //根据结果集元数据打印内容
            ResultSetMetaData pkmd = pks.getMetaData();
            while(pks.next()){
                for(int i = 1;i <= pkmd.getColumnCount();i ++){
                    System.out.println(pkmd.getColumnName(i)+"\t"+pks.getString(i));
                }
            }

        }
    }



    /**
     * 修改字段名称
     *
     * ALTER TABLE [<模式名>.]<表名> <修改表定义子句>
     * <修改表定义子句> ::=
     * ALTER [COLUMN] <列名> RENAME TO <列名> |
     *
     * @throws SQLException
     */
    @Test
    public void alterRenameField() throws SQLException {
        Statement statement = conn.createStatement();
        // 被修改的字段不存在会抛错：列[product_TMP]不存在
        String sql = "ALTER TABLE \"JKONG_TEST\".\"product\" ALTER COLUMN \"TEST_FIELD\" RENAME TO \"rpoduct_TMP\"";
        statement.execute(sql);
        System.out.println(OVER);
        statement.close();
    }

    /**
     * 修改字段默认值
     *
     * ALTER TABLE [<模式名>.]<表名> <修改表定义子句>
     * <修改表定义子句> ::=
     * ALTER [COLUMN] <列名> SET DEFAULT <列缺省值表达式>|
     * ALTER [COLUMN] <列名> DROP DEFAULT |
     *
     * @throws SQLException
     */
    @Test
    public void alterDefaultField() throws SQLException {
        Statement statement = conn.createStatement();
        // 被修改的字段不存在会抛错：列[product_TMP]不存在
        String sql = "ALTER TABLE \"JKONG_TEST\".\"product\" ALTER COLUMN \"TEST_FIELD\" SET DEFAULT \"1\"";
//        String sql = "ALTER TABLE \"JKONG_TEST\".\"product\" ALTER COLUMN \"TEST_FIELD\" DROP DEFAULT";
        statement.execute(sql);
        System.out.println(OVER);
        statement.close();
    }

    /**
     * 修改字段 非空属性
     *
     * ALTER TABLE [<模式名>.]<表名> <修改表定义子句>
     * <修改表定义子句> ::=
     * ALTER [COLUMN] <列名> SET <NULL | NOT NULL>|
     * ALTER [COLUMN] <列名> SET [NOT] VISIBLE|
     *
     * @throws SQLException
     */
    @Test
    public void alterNotNullField() throws SQLException {
        Statement statement = conn.createStatement();
        // 被修改的字段不存在会抛错：列[product_TMP]不存在
        String sql = "ALTER TABLE \"JKONG_TEST\".\"product\" ALTER COLUMN \"TEST_FIELD\" SET NOT NULL";
        statement.execute(sql);
        System.out.println(OVER);
        statement.close();
    }

    /**
     * 修改字段定义
     *
     * ALTER TABLE [<模式名>.]<表名> <修改表定义子句>
     * <修改表定义子句> ::=
     * MODIFY <列定义>|
     *
     * @throws SQLException
     */
    @Test
    public void modifyField() throws SQLException {
        Statement statement = conn.createStatement();
        // 被修改的字段不存在会抛错：列[product_TMP]不存在
        String sql = "ALTER TABLE \"JKONG_TEST\".\"product\" MODIFY \"product_TMP\" VARCHAR(200)";
//        String sql = "ALTER TABLE \"JKONG_TEST\".\"product\" MODIFY \"product_TMP\" INTEGER";
        statement.execute(sql);
        System.out.println(OVER);
        statement.close();
    }


    /**
     * 基表数据删除语句
     *
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

    // TODO: 2019/11/6 创建主键




    // TODO: 2019/11/6 修改主键

    // TODO: 2019/11/6 添加索引

    // TODO: 2019/11/6 删除索引 

}