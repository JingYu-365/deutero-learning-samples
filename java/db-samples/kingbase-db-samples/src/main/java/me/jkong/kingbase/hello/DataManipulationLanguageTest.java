package me.jkong.kingbase.hello;

import com.alibaba.druid.pool.DruidPooledConnection;
import me.jkong.kingbase.pool.DataBaseInitializer;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author JKong
 * @version v1.0
 * @description 数据库 DML 操作
 * @date 2019/12/3 11:52.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DataManipulationLanguageTest {


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


    @Test
    @Order(1)
    public void insertData() throws SQLException {
        String sql = "INSERT INTO JKONG_TEST.products(product_id,product_name,author,publisher,publishtime,product_subcategoryid,productno,satetystocklevel,originalprice) " +
                "VALUES(1,'三国演义1','罗贯中','中华书局','2005-04-01',4,'9787101046123',10,19.0000),(2,'三国演义2','罗贯中','中华书局','2005-04-01',4,'9787101046123',10,19.0000);";
        statement.executeUpdate(sql);
    }


    @Test
    @Order(2)
    public void updateData() throws SQLException {
        String sql = "UPDATE JKONG_TEST.products SET product_name='娃哈哈' WHERE PRODUCT_ID=1;";
        statement.executeUpdate(sql);
    }


    @Test
    @Order(3)
    public void deleteData() throws SQLException {
        String sql = "DELETE FROM JKONG_TEST.products WHERE product_name='娃哈哈'";
        statement.executeUpdate(sql);
    }



    /*
    - 新增数据
        - INSERT INTO products VALUES (1, 'Cheese', 9.99);
        - INSERT INTO products (product_no, name, price) VALUES (1, 'Cheese', 9.99);
        - INSERT INTO products (product_no, name, price) VALUES
            (1, 'Cheese', 9.99),
            (2, 'Bread', 1.99),
            (3, 'Milk', 2.99);

    - 更新数据
        - UPDATE products SET price = 10 WHERE price = 5;
        - UPDATE mytable SET a = 5, b = 3, c = 1 WHERE a > 0;

    - 删除数据
        - DELETE FROM products WHERE price = 10;
        - DELETE FROM products;
     */
}