package me.jkong.hbase.basic;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;

import java.io.IOException;

/**
 * 使用API操作HBase
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/5/8 17:48.
 */
public class HBaseApiExample {

    private static final String HBASE_ROOT_DIR = "hdfs://10.10.27.47:9000/hbase";

    private static final String HBASE_ZOOKEEPER_QUORUM = "10.10.27.47,10.10.27.48,10.10.27.49";

    private static final String HBASE_ZOOKEEPER_PROPERTY_CLIENT_PORT = "2181";

    /**
     * 管理HBase的配置信息
     */
    private static Configuration configuration;

    /**
     * 管理HBase连接
     */
    private static Connection connection;

    /**
     * 管理HBase数据库的信息
     */
    private static Admin admin;


    public static void main(String[] args) throws IOException {
        //连接
        init();
        String colF[] = {"score"};
        // 建表
        createTable("student", colF);
        insertData("student", "zhangsan", "score", "English", "69");
        insertData("student", "zhangsan", "score", "Math", "86");
        insertData("student", "zhangsan", "score", "Computer", "77");
        getData("student", "zhangsan", "score", "Computer");
    }


    /**
     * 创建表
     *
     * @param myTableName 表名
     * @param colFamily   列族数组
     * @throws Exception
     */
    public static void createTable(String myTableName, String[] colFamily) throws IOException {
        TableName tableName = TableName.valueOf(myTableName);
        if (admin.tableExists(tableName)) {
            System.out.println("Table exists");
        } else {
            HTableDescriptor hTableDescriptor = new HTableDescriptor(tableName);
            for (String str : colFamily) {
                HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(str);
                hTableDescriptor.addFamily(hColumnDescriptor);
            }
            admin.createTable(hTableDescriptor);
        }
    }

    /**
     * 添加单元格数据
     *
     * @param tableName 表名
     * @param rowKey    行键
     * @param colFamily 列族
     * @param col       列限定符
     * @param val       数据
     * @thorws Exception
     */
    public static void insertData(String tableName, String rowKey, String colFamily, String col, String val) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Put put = new Put(rowKey.getBytes());
        put.addColumn(colFamily.getBytes(), col.getBytes(), val.getBytes());
        table.put(put);
        table.close();
    }


    /**
     * 浏览数据
     *
     * @param tableName 表名
     * @param rowKey    行
     * @param colFamily 列族
     * @param col       列限定符
     * @throw IOException
     */
    public static void getData(String tableName, String rowKey, String colFamily, String col) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Get get = new Get(rowKey.getBytes());
        get.addColumn(colFamily.getBytes(), col.getBytes());
        Result result = table.get(get);
        System.out.println(new String(result.getValue(colFamily.getBytes(), col == null ? null : col.getBytes())));
        table.close();
    }

    /**
     * 操作数据库之前,建立连接
     */
    public static void init() throws IOException {
        configuration = HBaseConfiguration.create();
        //
        configuration.set("hbase.rootdir", HBASE_ROOT_DIR);
        // 设置zookeeper节点
        configuration.set("hbase.zookeeper.quorum", HBASE_ZOOKEEPER_QUORUM);
        // 设置zookeeper节点端口
        configuration.set("hbase.zookeeper.property.clientPort", HBASE_ZOOKEEPER_PROPERTY_CLIENT_PORT);
        connection = ConnectionFactory.createConnection(configuration);
        admin = connection.getAdmin();

    }

    /**
     * 操作数据库之后，关闭连接
     */
    public static void close() throws IOException {
        if (admin != null) {
            // 退出用户
            admin.close();
        }
        if (null != connection) {
            // 关闭连接
            connection.close();
        }

    }


}