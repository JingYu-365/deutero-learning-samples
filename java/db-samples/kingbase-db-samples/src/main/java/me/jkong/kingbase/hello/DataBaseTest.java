package me.jkong.kingbase.hello;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import me.jkong.kingbase.pool.DataBaseInitializer;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

/**
 * @author JKong
 * @version v1.0
 * @description 数据库连接测试
 * @date 2019/12/3 15:10.
 */
public class DataBaseTest {

    private static DruidDataSource dataSource = DataBaseInitializer.getDataBase();

    @Test
    public void initDataBase() throws SQLException {
        System.out.println(dataSource);
        DruidPooledConnection connection = dataSource.getConnection();
        System.out.println(connection);
        connection.close();
    }
}