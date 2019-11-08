package me.jkong.druid.config.druid;

import com.alibaba.druid.pool.DruidDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author JKong
 * @version v1.0
 * @description Druid 数据库连接池配置类
 * @date 2019/11/8 11:47.
 * @see <url>https://blog.csdn.net/zhanghe__/article/details/79429114</url>
 * @see <url>https://blog.csdn.net/qq_37279783/article/details/82013702</url>
 * @see <url>https://blog.csdn.net/zl_momomo/article/details/82851134</url>
 * @see <url>https://blog.csdn.net/a18302465887/article/details/76215979</url>
 * @see <url>https://github.com/alibaba/druid/wiki/DruidDataSource%E9%85%8D%E7%BD%AE%E5%B1%9E%E6%80%A7%E5%88%97%E8%A1%A8</url>
 */
public class DruidConfig {

    public static void main(String[] args) {
        getDataSource();
    }

    public static void getDataSource() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("dm.jdbc.driver.DmDriver");
        dataSource.setUrl("jdbc:dm://10.10.32.11:5236");
        dataSource.setUsername("SYSDBA");
        dataSource.setPassword("wingtestdm8");
        dataSource.setInitialSize(Runtime.getRuntime().availableProcessors() / 2);
        dataSource.setMaxActive(Runtime.getRuntime().availableProcessors() * 2);
        try {
            // 获得连接:
            conn = dataSource.getConnection();
            // 编写SQL：
            String sql = "SELECT CONSTRAINT_NAME FROM user_constraints WHERE OWNER='JKONG_TEST' AND TABLE_NAME='product' and CONSTRAINT_TYPE='P'";
            pstmt = conn.prepareStatement(sql);
            // 执行sql:
            rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}