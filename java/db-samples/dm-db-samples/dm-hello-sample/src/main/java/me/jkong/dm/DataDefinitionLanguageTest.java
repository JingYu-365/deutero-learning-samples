package me.jkong.dm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
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

    @After
    public void close() throws SQLException {
        DataBaseInitializer.disConnect();
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
     * 1. 自增列功能定义
     *  IDENTITY [ (种子, 增量) ]
     *  IDENTITY 适用于 INT(-2147483648 ～ +2147483647)、 BIGINT(-263～+263-2)类型的列。 每个表只能创建一个自增列；
     * @throws SQLException sql ex
     */
    @Test
    public void createTable() throws SQLException {
        Statement statement = conn.createStatement();
        // 注意：
        // 1. 模式名一定全部转为大写
        // 2. 字段及表名需要使用'\"'括起来
        String sql = "CREATE TABLE \"JKONG_TEST\".\"product2\"\n" +
                // 给主键设置约束名，方便主键删除。
                "(\"product_id\" INT IDENTITY(1,1) CONSTRAINT \"product2_pri\" NOT CLUSTER PRIMARY KEY," +
                "\"pro_name\" VARCHAR(50),\n" +
                "\"author\" VARCHAR(50),\n" +
                "\"publisher\" VARCHAR(50),\n" +
                "\"publishtime\" DATETIME(6),\n" +
                "\"product_subcategoryid\" INT,\n" +
                "\"productno\" VARCHAR(50),\n" +
                "\"satetystocklevel\" INT,\n" +
                "\"originalprice\" DECIMAL(22,6),\n" +
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
     * <p>
     * ALTER TABLE [<模式名>.]<表名> <修改表定义子句>
     * <修改表定义子句> ::=
     * ADD [COLUMN] <列定义>|
     * ADD [COLUMN] (<列定义> {,<列定义>})|
     * <p>
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
     * <p>
     * ALTER TABLE [<模式名>.]<表名> <修改表定义子句>
     * <修改表定义子句> ::=
     * ADD [CONSTRAINT [<约束名>] ] <表级约束子句> [<CHECK 选项>] [<失效生效选项>]|
     * DROP CONSTRAINT <约束名> [RESTRICT | CASCADE] |
     * <p>
     * <CHECK 选项>
     * 1. PRIMARY KEY
     * 2. UNIQUE
     * 3. REFERENCES
     * 4. CHECK
     *
     * @throws SQLException
     */
    @Test
    public void alterUniqueField() throws SQLException {
        Statement statement = conn.createStatement();
        // 被修改的字段不存在会抛错：列[product_TMP]不存在

        // 添加唯一约束
        // String sql = "ALTER TABLE \"JKONG_TEST\".\"product\" ADD CONSTRAINT \"TEST_CONSTRAINT\" UNIQUE (\"rpoduct_TMP\")";

        // 删除唯一约束
        // String sql = "ALTER TABLE \"JKONG_TEST\".\"product\" DROP CONSTRAINT \"TEST_CONSTRAINT\ CASCADE" ;

        // 添加主键
        String sql = "ALTER TABLE \"JKONG_TEST\".\"product\" ADD CONSTRAINT \"product_id_pri\" NOT CLUSTER  PRIMARY KEY(\"product_id\")";

        // 删除主键
        // String sql = "alter table \"JKONG_TEST\".\"product\" DROP constraint \"product_id_pri\"" ;

        // 修改主键
        // 注意：
        // 1. 此方法只修改主键字段，但是不修改主键名称
        // 2. 约束名不存在时：报错无效的约束名[product_id_pri_1]
        // 建议操作：
        // 1. 主键名采用：{tableName}_pKey 的格式
        // 2. 目前支持每张表一个主键，
        // 2. 首先使用 modify 去修改主键，如果抛错，则说明主键本不存在，然后再调用 ADD 添加主键。
        // String sql = "alter table \"JKONG_TEST\".\"product\" modify constraint \"product_id_pri\" to NOT CLUSTER primary key (\"product_id\",\"pro_name\")" ;
        statement.execute(sql);
        System.out.println(OVER);
        statement.close();
    }

    /**
     * 查询指定表主键
     *
     * @throws SQLException
     */
    @Test
    public void selectTablePrimaryKey() throws SQLException {
        // 查询语句
        String sql = "SELECT CONSTRAINT_NAME FROM user_constraints WHERE OWNER='JKONG_TEST' AND TABLE_NAME='product' and CONSTRAINT_TYPE='P'";
        // 创建语句对象
        Statement stmt = conn.createStatement();
        // 执行查询
        ResultSet rs = stmt.executeQuery(sql);
        // 显示结果集
        System.out.println(DataQueryLanguageTest.displayResultSet(rs));
        // 关闭结果集
        rs.close();
        // 关闭语句
        stmt.close();
    }


    /**
     * 修改字段名称
     * <p>
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
     * <p>
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
        // String sql = "ALTER TABLE \"JKONG_TEST\".\"product\" ALTER COLUMN \"TEST_FIELD\" DROP DEFAULT";
        statement.execute(sql);
        System.out.println(OVER);
        statement.close();
    }

    /**
     * 修改字段 非空属性
     * <p>
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
     * <p>
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
        // String sql = "ALTER TABLE \"JKONG_TEST\".\"product\" MODIFY \"product_TMP\" INTEGER";
        statement.execute(sql);
        System.out.println(OVER);
        statement.close();
    }


    /**
     * 添加索引 | 替还索引
     * <p>
     * CREATE [OR REPLACE] [CLUSTER|NOT PARTIAL][UNIQUE | BITMAP| SPATIAL] INDEX <索引名>
     * ON [<模式名>.]<表名>(<索引列定义>{,<索引列定义>}) [GLOBAL] [<STORAGE 子句>] [NOSORT] [ONLINE];
     *
     * 1. UNIQUE    指明该索引为唯一索引；         todo 支持
     * 2. BITMAP    指明该索引为位图索引；
     * 3. SPATIAL   指明该索引为空间索引；
     * 4. CLUSTER   指明该索引为聚簇索引（也叫聚集索引），不能应用到函数索引中；
     * 5. NOT PARTIAL 指明该索引为非聚簇索引，缺省即为非聚簇索引； todo 默认
     * 6. <索引名> 指明被创建索引的名称，索引名称最大长度128字节；
     */
    @Test
    public void createIndex() throws SQLException {
        Statement statement = conn.createStatement();
        // 创建索引
        // String sql = "CREATE INDEX \"pro_name_index\" ON \"JKONG_TEST\".\"product\"(\"pro_name\")";

        // 如果索引不存在则创建，如果存在则替还索引
        String sql = "CREATE OR REPLACE INDEX \"pro_name_index\" ON \"JKONG_TEST\".\"product\"(\"author\")";
        statement.execute(sql);
        System.out.println(OVER);
        statement.close();
    }


    /**
     * 更新索引名称
     * <p>
     * ALTER INDEX [<模式名>.]<索引名> <修改索引定义子句>
     * <修改索引定义子句> ::=
     * —— RENAME TO [<模式名>.]<索引名>|
     * —— <INVISIBLE | VISIBLE>
     * —— <UNUSABLE>|
     * —— <REBUILD>[NOSORT][ONLINE]|
     * —— <MONITORING | NOMONITORING> USAGE
     */
    @Test
    public void updateIndex() throws SQLException {
        Statement statement = conn.createStatement();
        // 如果索引不存在则创建，如果存在则替还索引
        String sql = "ALTER INDEX \"JKONG_TEST\".\"pro_name_index\" RENAME TO \"author_index\"";
        statement.execute(sql);
        System.out.println(OVER);
        statement.close();
    }

    /**
     * 删除索引
     * <p>
     * DROP INDEX [<模式名>.]<索引名>;
     */
    @Test
    public void deleteIndex() throws SQLException {
        Statement statement = conn.createStatement();
        // 如果索引不存在则创建，如果存在则替还索引
        String sql = "DROP INDEX \"JKONG_TEST\".\"author_index\"";
        statement.execute(sql);
        System.out.println(OVER);
        statement.close();
    }
}