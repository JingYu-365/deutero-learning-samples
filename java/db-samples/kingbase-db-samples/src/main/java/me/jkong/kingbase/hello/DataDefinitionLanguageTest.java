package me.jkong.kingbase.hello;

import com.alibaba.druid.pool.DruidPooledConnection;
import me.jkong.kingbase.pool.DataBaseInitializer;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author JKong
 * @version v1.0
 * @description 数据定义语言测试
 * @date 2019/12/3 11:52.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DataDefinitionLanguageTest {

    private static Statement statement = null;
    private static DruidPooledConnection connection = null;

    @BeforeAll
    public static void init() throws SQLException {
        System.out.println("init");
        connection = DataBaseInitializer.getDataBase().getConnection();
        statement = connection.createStatement();
    }

    @AfterAll
    public static void close() throws SQLException {
        System.out.println("close");
        if (statement != null) {
            statement.close();
        }

        if (connection != null) {
            connection.close();
        }
    }

    /**
     * 创建模式
     *
     * @throws SQLException ex
     */
    @Order(1)
    @Test
    public void createSchemaTest() throws SQLException {
        String sql = "CREATE SCHEMA JKong_test AUTHORIZATION SYSTEM";
        statement.execute(sql);
    }

    /**
     * 删除模式
     * <p>
     * 注意点：
     * - 模式对大小写不敏感
     * - 删除不存在的模式会抛异常
     *
     * @throws SQLException ex
     */
    @Test
    @Disabled
    public void dropSchemaTest() throws SQLException {
        String sql = "DROP SCHEMA JKong_Test CASCADE;";
        statement.execute(sql);
    }


    /*******************************************************************************************************************
     * 创建数据表
     *
     * @throws SQLException sql ex
     */
    @Order(2)
    @Test
    public void createTableTest() throws SQLException {
        String sql = "CREATE TABLE JKONG_TEST.products" +
                "(product_id integer CONSTRAINT field_primary_key_name PRIMARY KEY," +
                "pro_name VARCHAR(50)," +
                "author VARCHAR(50)," +
                "publisher VARCHAR(50)," +
                "publishtime timestamp(6)," +
                "default_time timestamp default current_timestamp," +
                "product_subcategoryid integer ," +
                "productno VARCHAR(50)," +
                "satetystocklevel integer ," +
                "originalprice DECIMAL(22,6)) ";
        statement.execute(sql);
    }

    /**
     * 表重命名
     *
     * @throws SQLException
     */
    @Test
    @Disabled
    public void updateTableTest() throws SQLException {
        String sql = "ALTER table JKong_test.products_tmp RENAME TO products";
        statement.execute(sql);
    }


    /**
     * 删除表
     * <p>
     * 注意：
     * - 删除不存在的表会抛出异常：KSQLException: 错误: 表 "PRODUCT" 不存在
     *
     * @throws SQLException
     */
    @Test
    @Disabled
    public void dropTableTest() throws SQLException {
        String sql = "DROP TABLE IF EXISTS JKong_test.products CASCADE";
        statement.execute(sql);
    }


    /**
     * 表添加字段
     * <p>
     * 注意：
     * - 表不存在将抛出异常
     * - 字段已存在将抛出异常
     *
     * @throws SQLException
     */
    @Order(3)
    @Test
    public void addFieldToTableTest() throws SQLException {
        String sql = "ALTER TABLE JKong_test.products ADD COLUMN description text;";
        statement.execute(sql);
    }


    /**
     * 删除字段
     * <p>
     * 注意：
     * - 字段不存在将抛出异常
     *
     * @throws SQLException
     */
    @Order(4)
    @Test
    public void dropFieldFromTableTest() throws SQLException {
        String sql = "ALTER table JKong_test.products drop column description CASCADE";
        statement.execute(sql);
    }


    /**
     * 更新字段名称
     * <p>
     * - 修改字段名称
     * - ALTER TABLE products ALTER COLUMN price TYPE numeric(10,2);
     * - ALTER TABLE products RENAME COLUMN product_no TO product_number;
     *
     * @throws SQLException
     */
    @Test
    @Order(5)
    public void updateFieldOfTableTest() throws SQLException {
        String sql = "ALTER TABLE JKong_test.products RENAME COLUMN pro_name TO product_name";
        statement.execute(sql);
    }


    /**
     * 修改字段类型
     * todo 不支持modify
     * @throws SQLException
     */
    @Test
    @Order(6)
    public void updateFieldTypeTest() throws SQLException {
        String sql = "ALTER TABLE JKong_test.products ALTER COLUMN product_name TYPE VARCHAR(255)";
        statement.execute(sql);
    }


    /**
     * 为属性添加非空约束
     * 注意：
     * - 多次添加不报错
     *
     * @throws SQLException
     */
    @Test
    @Order(7)
    public void setFieldNotNullTest() throws SQLException {
        String sql = "ALTER TABLE JKong_test.products ALTER COLUMN PRODUCT_NAME SET NOT NULL ";
        statement.execute(sql);
    }

    /**
     * 移除属性非空约束
     * 注意：
     * - 多次添加不报错
     * - 字段对大小写也不敏感
     *
     * @throws SQLException
     */
    @Test
    @Order(8)
    public void removeFieldNotNullTest() throws SQLException {
        String sql = "ALTER TABLE JKong_test.products ALTER COLUMN PRODUCT_NAME DROP NOT NULL";
        statement.execute(sql);
    }

    /**
     * 为字段添加默认值
     *
     * @throws SQLException
     */
    @Test
    @Order(9)
    public void setDefaultForField() throws SQLException {
        String sql = "ALTER TABLE JKong_test.products ALTER COLUMN product_name SET DEFAULT 'product_dd'";
        statement.execute(sql);
    }

    /**
     * 删除字段默认值
     *
     * @throws SQLException
     */
    @Test
    @Order(10)
    public void dropDefaultForField() throws SQLException {
        String sql = "ALTER TABLE JKong_test.products ALTER COLUMN product_name DROP DEFAULT";
        statement.execute(sql);
    }


    /**
     * 添加唯一约束
     *
     * @throws SQLException
     */
    @Test
    @Order(11)
    public void addConstraintForField() throws SQLException {
        String sql = "ALTER TABLE JKong_test.products ADD CONSTRAINT field_constraint_name UNIQUE (product_name,author)";
        statement.execute(sql);
    }


    /**
     * 移除约束
     *
     * @throws SQLException
     */
    @Test
    @Order(12)
    public void dropConstraintFromField() throws SQLException {
        String sql = "ALTER TABLE JKong_test.products DROP CONSTRAINT field_constraint_name";
        statement.execute(sql);
    }


    /**
     * 删除主键约束
     *
     * @throws SQLException
     */
    @Test
    @Order(13)
    public void dropPrimaryKeyFromField() throws SQLException {
        String sql = "ALTER TABLE JKong_test.products DROP CONSTRAINT field_primary_key_name";
        statement.execute(sql);
    }


    /**
     * 添加主键约束
     *
     * @throws SQLException
     */
    @Test
    @Order(14)
    public void addPrimaryKeyForField() throws SQLException {
        String sql = "ALTER TABLE JKong_test.products ADD CONSTRAINT field_primary_key_name PRIMARY KEY (product_id)";
        statement.execute(sql);
    }


    @Test
    @Order(15)
    public void updatePrimaryKeyField() throws SQLException {
        String sql1 = "ALTER TABLE JKong_test.products DROP CONSTRAINT field_primary_key_name";
        statement.addBatch(sql1);
        String sql2 = "ALTER TABLE JKong_test.products ADD CONSTRAINT field_primary_key_name PRIMARY KEY (product_id,product_name)";
        statement.addBatch(sql2);
        int[] ints = statement.executeBatch();
        for (int i = 0; i < ints.length; i++) {
            int anInt = ints[i];
            System.out.println(anInt);
        }
    }

    @Test
    @Order(16)
    public void addIndexForTable() throws SQLException {
        String sql = "CREATE INDEX field_index_name ON JKong_test.products (product_name)";
//        String sql = "CREATE UNIQUE INDEX field_index_name ON JKong_test.products (product_name)";
        statement.execute(sql);
    }

    @Test
    @Order(17)
    public void updateIndex() throws SQLException {
        String sql = "ALTER INDEX JKong_test.field_index_name RENAME TO field_index_name_tmp";
        statement.execute(sql);
    }

    @Test
    @Order(18)
    @Disabled
    public void dropIndexFromTable() throws SQLException {
        String sql = "DROP INDEX JKong_test.field_index_name_tmp";
        statement.execute(sql);
    }




    /*
    - 添加约束
        - ALTER TABLE products ALTER COLUMN product_no SET NOT NULL;
        - ALTER TABLE products ALTER COLUMN product_no DROP NOT NULL;
        - ALTER TABLE products ALTER COLUMN price SET DEFAULT 7.77;
        - ALTER TABLE products ALTER COLUMN price DROP DEFAULT;
        - ALTER TABLE products ADD CHECK (name <> '');
        - ALTER TABLE products ADD CONSTRAINT some_name UNIQUE (product_no);
        - ALTER TABLE products ADD FOREIGN KEY (product_group_id) REFERENCES product_g
    - 删除约束
        - ALTER TABLE products DROP CONSTRAINT some_name;
     */



    /*
    - 默认值
        - price numeric DEFAULT 9.99
        - insert_time timestamp DEFAULT CURRENT_TIMESTAMP

    - CHECK 约束
        - price numeric CHECK (price > 0)
        - price numeric CONSTRAINT positive_price CHECK (price > 0)

    - NOT NULL 约束
        - product_no integer NOT NULL

    - NULL 约束 (NULL约束。这并不意味着该列必须为空，进而肯定是无用的。相反，它仅仅选择了列可能为空的默认行为。)
        - name text NULL

    - 唯一约束保
        - product_no integer UNIQUE
        - product_no integer, UNIQUE (product_no)
        - product_no integer CONSTRAINT must_be_different UNIQUE
        - 一组列定义一个唯一约束
        CREATE TABLE example (
            a integer,
            b integer,
            c integer,
            UNIQUE (a, c)
        );

    - 主键约束（一个表最多只能有一个主键）（PRIMARY KEY == UNIQUE NOT NULL）
        - product_no integer PRIMARY KEY
        - 主键也可以包含多于一个列
        CREATE TABLE example (
            a integer,
            b integer,
            c integer,
            PRIMARY KEY (a, c)
        );

    - 外键约束（一个外键约束指定一列（或一组列）中的值必须匹配出现在另一个表中某些行的值。）
        - product_no integer REFERENCES products (product_no)

    - 约束组合（一个列可以有多于一个的约束，只需要将这些约束一个接一个写出，约束的顺序没有关系。）
        - price numeric NOT NULL CHECK (price > 0)
     */


}