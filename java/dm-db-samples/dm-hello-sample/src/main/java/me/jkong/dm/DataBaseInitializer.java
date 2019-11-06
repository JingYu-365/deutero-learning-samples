package me.jkong.dm;

import java.math.BigDecimal;
import java.sql.*;

/**
 * @author JKong
 * @version v1.0
 * @description DM 数据库测试
 * @date 2019/11/6 10:53.
 */
public class DataBaseInitializer {
    /**
     * 定义 DM JDBC 驱动串
     */
    private static final String JDBC_STRING = "dm.jdbc.driver.DmDriver";
    /**
     * 定义 DM URL 连接串
     */
    private static final String URL_STRING = "jdbc:dm://10.10.32.11:5236";
    /**
     * 定义连接用户名
     */
    private static final String USER_NAME = "SYSDBA";
    /**
     * 定义连接用户口令
     */
    private static final String PASSWORD = "wingtestdm8";
    /**
     * 定义连接对象
     */
    private static Connection conn = null;

    static {
        try {
            loadJdbcDriver();
            connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载 JDBC 驱动程序
     *
     * @throws SQLException 异常
     */
    private static void loadJdbcDriver() throws SQLException {
        try {
            System.out.println("Loading JDBC Driver...");
            // 加载 JDBC 驱动程序
            Class.forName(JDBC_STRING);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Load JDBC Driver Error : " + e.getMessage());
        } catch (Exception ex) {
            throw new SQLException("Load JDBC Driver Error : " + ex.getMessage());
        }
    }


    /**
     * 连接 DM 数据库
     *
     * @throws SQLException 异常
     */
    private static void connect() throws SQLException {
        try {
            System.out.println("Connecting to DM Server...");
            // 连接 DM 数据库
            conn = DriverManager.getConnection(URL_STRING, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            throw new SQLException("Connect to DM Server Error : "
                    + e.getMessage());
        }
    }

    public static Connection getConn() {
        return conn;
    }


    /**
     * 关闭连接
     *
     * @throws SQLException 异常
     */
    public static void disConnect() throws SQLException {
        try {
            // 关闭连接
            conn.close();
        } catch (SQLException e) {
            throw new SQLException("close connection error : " + e.getMessage());
        }
    }



    /**
     * 往产品信息表插入数据
     *
     * @throws SQLException 异常
     */
    public void insertTable() throws SQLException {
        // 插入数据语句
        String sql = "INSERT INTO jkong_test.product(" +
                "pro_name," +
                "author," +
                "publisher," +
                "publishtime," +
                "product_subcategoryid," +
                "productno," +
                "satetystocklevel," +
                "originalprice," +
                "nowprice," +
                "discount," +
                "description," +
                "photo," +
                "pro_type," +
                "papertotal," +
                "wordtotal," +
                "sellstarttime," +
                "sellendtime) " +
                "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        // 创建语句对象
        PreparedStatement pstmt = conn.prepareStatement(sql);
        // 为参数赋值
        pstmt.setString(1, "三国演义");
        pstmt.setString(2, "罗贯中");
        pstmt.setString(3, "中华书局");
        pstmt.setDate(4, Date.valueOf("2005-04-01"));
        pstmt.setInt(5, 4);
        pstmt.setString(6, "9787101046121");
        pstmt.setInt(7, 10);
        pstmt.setBigDecimal(8, new BigDecimal(19.0000));
        pstmt.setBigDecimal(9, new BigDecimal(15.2000));
        pstmt.setBigDecimal(10, new BigDecimal(8.0));
        pstmt.setString(11, "《三国演义》是中国第一部长篇章回体小说，中国小说由短篇发展至长篇的原因与说书有关。 ");
        // 设置大字段参数
        pstmt.setNull(12, java.sql.Types.BINARY);
        pstmt.setString(13, "25");
        pstmt.setInt(14, 943);
        pstmt.setInt(15, 93000);
        pstmt.setDate(16, Date.valueOf("2006-03-20"));
        pstmt.setDate(17, Date.valueOf("1900-01-01"));
        // 执行语句
        pstmt.executeUpdate();
        // 关闭语句
        pstmt.close();
    }

    /**
     * 查询产品信息表
     *
     * @throws SQLException 异常
     */
    public void queryProduct() throws SQLException {
        // 查询语句
        String sql = "SELECT productid,name,author,description,photo FROM production.product WHERE productid=11";
        // 创建语句对象
        Statement stmt = conn.createStatement();
        // 执行查询
        ResultSet rs = stmt.executeQuery(sql);
        // 显示结果集
        displayResultSet(rs);
        // 关闭结果集
        rs.close();
        // 关闭语句
        stmt.close();
    }

    /**
     * 修改产品信息表数据
     *
     * @throws SQLException 异常
     */
    public void updateTable() throws SQLException {
        // 更新数据语句
        String sql = "UPDATE production.product SET name = ?"
                + "WHERE productid = 11;";
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
     * 删除产品信息表数据
     *
     * @throws SQLException 异常
     */
    public void deleteTable() throws SQLException {
        // 删除数据语句
        String sql = "DELETE FROM production.product WHERE productid = 11;";
        // 创建语句对象
        Statement stmt = conn.createStatement();
        // 执行语句
        stmt.executeUpdate(sql);
        // 关闭语句
        stmt.close();
    }

    /**
     * 查询产品信息表
     *
     * @throws SQLException 异常
     */
    public void queryTable() throws SQLException {
        // 查询语句
        String sql = "SELECT productid,name,author,publisher FROM production.product";
        // 创建语句对象
        Statement stmt = conn.createStatement();
        // 执行查询
        ResultSet rs = stmt.executeQuery(sql);
        // 显示结果集
        displayResultSet(rs);
        // 关闭结果集
        rs.close();
        // 关闭语句
        stmt.close();
    }


    /**
     * 显示结果集
     *
     * @param rs 结果集对象
     * @throws SQLException 异常
     */
    private void displayResultSet(ResultSet rs) throws SQLException {
        // 取得结果集元数据
        ResultSetMetaData rsmd = rs.getMetaData();
        // 取得结果集所包含的列数
        int numCols = rsmd.getColumnCount();
        // 显示列标头
        for (int i = 1; i <= numCols; i++) {
            if (i > 1) {
                System.out.print(",");
            }
            System.out.print(rsmd.getColumnLabel(i));
        }
        System.out.println("");
        // 显示结果集中所有数据
        while (rs.next()) {
            for (int i = 1; i <= numCols; i++) {
                if (i > 1) {
                    System.out.print(",");
                }
                // 普通字段
                System.out.print(rs.getString(i));
            }
            System.out.println("");
        }
    }

    /**
     * 类主方法 @param args 参数
     */
    public static void main(String args[]) {
        try {
            // 定义类对象
            DataBaseInitializer hell = new DataBaseInitializer();
            // 加载驱动程序
            hell.loadJdbcDriver();
            // 连接 DM 数据库
            hell.connect();
            // 插入数据
            System.out.println("--- 插入产品信息 ---");
            hell.insertTable();
            // 查询含有大字段的产品信息
            System.out.println("--- 显示插入结果 ---");
            hell.queryProduct();
            // 在修改前查询产品信息表
            System.out.println("--- 在修改前查询产品信息 ---");
            hell.queryTable();
            // 修改产品信息表
            System.out.println("--- 修改产品信息 ---");
            hell.updateTable();
            // 在修改后查询产品信息表
            System.out.println("--- 在修改后查询产品信息 ---");
            hell.queryTable();
            // 删除产品信息表
            System.out.println("--- 删除产品信息 ---");
            hell.deleteTable();
            // 在删除后查询产品信息表
            System.out.println("--- 在删除后查询产品信息 ---");
            hell.queryTable();
            // 关闭连接
            hell.disConnect();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}