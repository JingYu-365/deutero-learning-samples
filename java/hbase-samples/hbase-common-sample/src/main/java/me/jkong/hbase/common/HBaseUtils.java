package me.jkong.hbase.common;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * HBase 工具类
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/5/8 19:40.
 */
public class HBaseUtils {

    static {
        HBaseConfig.init();
    }

    public static void close() {
        HBaseConfig.close();
    }

    private static Admin admin() {
        return HBaseConfig.getAdmin();
    }

    private static Connection connection() {
        return HBaseConfig.getConnection();
    }

    /*********************************** namespace operation for hbase ************************************
     * 创建命名空间
     * @param namespace 命名空间名称
     * @return true：创建成功
     */
    public static boolean createNamespace(String namespace) {
        NamespaceDescriptor descriptor = NamespaceDescriptor.create(namespace).build();
        try {
            if (!isExistNamespace(namespace)) {
                admin().createNamespace(descriptor);
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 调用接口 {@link Admin#getNamespaceDescriptor(String)}时，
     * 若发生特定的异常，即找不到命名空间，则创建命名空间
     *
     * @param namespace 命名空间名称
     * @return true：命名空间存在
     */
    public static boolean isExistNamespace(String namespace) {
        return getNamespaceDetail(namespace) != null;
    }

    /**
     * 获取命名空间详细信息
     *
     * @param namespace 命名空间名称
     * @return 命名空间详细信息
     */
    public static Map<String, String> getNamespaceDetail(String namespace) {
        try {
            NamespaceDescriptor descriptor = admin().getNamespaceDescriptor(namespace);
            return descriptor.getConfiguration();
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 删除命名空间
     *
     * @param namespace 命名空间名称
     * @return true：删除成功
     */
    public static boolean deleteNamespace(String namespace) {
        try {
            if (isExistNamespace(namespace)) {
                // TODO: 2020/5/9 需要验证此命名空间下是否存在表，如果存在表是不允许删除的
                admin().deleteNamespace(namespace);
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 获取当前HBase中所有的命名空间
     *
     * @return 命名空间名列表
     * @throws IOException e
     */
    public static List<String> listAllNamespaceNames() throws IOException {
        return Arrays.stream(listAllNamespace())
                .map(NamespaceDescriptor::getName).collect(Collectors.toList());
    }

    /**
     * 获取所有 命名空间信息
     *
     * @return 命名空间信息
     * @throws IOException e
     */
    public static NamespaceDescriptor[] listAllNamespace() throws IOException {
        return admin().listNamespaceDescriptors();
    }

    /************************************ table operation for hbase *****************************************
     * 获取指定命名空间下的所有表名
     *
     * @param namespace 命名空间
     * @return 表名
     * @throws IOException e
     */
    public static List<TableName> listTableNamesByNamespace(String namespace) throws IOException {
        List<TableDescriptor> tableDescriptors = null;
        if (isExistNamespace(namespace)) {
            tableDescriptors = admin().listTableDescriptorsByNamespace(Bytes.toBytes(namespace));
        }

        if (tableDescriptors != null) {
            return tableDescriptors.stream().map(TableDescriptor::getTableName).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    /**
     * 创建表
     */
    public static void createTable(String tableName, String... columnFamily) throws IOException {
        if (!isTableExist(TableName.valueOf(tableName))) {
            // 创建表属性对象,表名需要转字节
            HTableDescriptor descriptor = new HTableDescriptor(TableName.valueOf(tableName));
            // 创建多个列族
            for (String cf : columnFamily) {
                descriptor.addFamily(new HColumnDescriptor(cf));
            }
            // 根据对表的配置，创建表
            admin().createTable(descriptor);
        }
    }


    public static boolean dropTable(TableName tableName) {
        try {
            if (isTableExist(tableName)) {
                admin().disableTable(tableName);
                admin().deleteTable(tableName);
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 验证表是否存在
     *
     * @param tableName 表名
     * @return true：表存在
     */
    public static boolean isTableExist(TableName tableName) {
        try {
            return admin().tableExists(tableName);
        } catch (IOException e) {
            return false;
        }
    }


    /************************************ family operation for hbase *****************************************
     * 根据表名获取表的列簇信息
     *
     * @param namespace 命名空间名称
     * @param tableName 表名称
     */
    public static ColumnFamilyDescriptor[] listFamiliesByTableName(String namespace, String tableName) {
        return listFamiliesByTableName(TableName.valueOf(namespace, tableName));
    }

    public static ColumnFamilyDescriptor[] listFamiliesByTableName(String tableName) {
        return listFamiliesByTableName(TableName.valueOf(tableName));
    }

    public static List<String> listFamilyNameByTableName(String namespace, String tableName) {
        return listFamilyNamesByTableName(TableName.valueOf(namespace, tableName));
    }

    public static List<String> listFamilyNameByTableName(String tableName) {
        return listFamilyNamesByTableName(TableName.valueOf(tableName));
    }

    /**
     * 根据表名获取表的列簇信息
     *
     * @param tableName 表名称
     * @return family descriptors
     */
    public static ColumnFamilyDescriptor[] listFamiliesByTableName(TableName tableName) {
        if (tableName == null) {
            return new ColumnFamilyDescriptor[0];
        }

        Table table = null;
        try {
            table = connection().getTable(tableName);
            return table.getDescriptor().getColumnFamilies();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (table != null) {
                    table.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ColumnFamilyDescriptor[0];
    }

    public static List<String> listFamilyNamesByTableName(TableName tableName) {
        ColumnFamilyDescriptor[] familyDescriptors = listFamiliesByTableName(tableName);
        if (familyDescriptors == null || familyDescriptors.length == 0) {
            return Collections.emptyList();
        }
        return Stream.of(familyDescriptors).map(ColumnFamilyDescriptor::getNameAsString).collect(Collectors.toList());
    }


    /**
     * 虎丘表中指定family的字段信息
     */
    public static void listTableColumnNames(TableName tableName, String familyName, String rowKey) {
        try {
            Get get = new Get(Bytes.toBytes(rowKey));
            Result result = connection().getTable(tableName).get(get);
            Map<byte[], byte[]> familyMap = result.getFamilyMap(Bytes.toBytes(familyName));
            for(Map.Entry<byte[], byte[]> entry:familyMap.entrySet()){
                System.out.println(Bytes.toString(entry.getKey()));
            }
        } catch (IOException e) {

        }
    }

    private static class HBaseConfig {

        private static final String HBASE_ROOT_DIR = "hdfs://10.10.27.47:9000/hbase";

        private static final String HBASE_ZOOKEEPER_QUORUM = "10.10.27.47,10.10.27.48,10.10.27.49";

        private static final String HBASE_ZOOKEEPER_PROPERTY_CLIENT_PORT = "2181";

        private static Configuration configuration;

        private static Connection connection;

        private static Admin admin;

        public static Configuration getConfiguration() {
            return configuration;
        }

        public static Connection getConnection() {
            return connection;
        }

        public static Admin getAdmin() {
            return admin;
        }

        /**
         * 初始化配置信息
         */
        public static void init() {
            try {
                configuration = HBaseConfiguration.create();
                configuration.set("hbase.zookeeper.property.clientPort", HBASE_ZOOKEEPER_PROPERTY_CLIENT_PORT);
                configuration.set("hbase.zookeeper.quorum", HBASE_ZOOKEEPER_QUORUM);
                configuration.set("hbase.rootdir", HBASE_ROOT_DIR);
                connection = ConnectionFactory.createConnection(configuration);
                admin = connection.getAdmin();
            } catch (IOException e) {
                e.printStackTrace();
                // todo logging
            }
        }

        /**
         * 释放资源
         */
        public static void close() {
            try {
                if (null != admin) {
                    admin.close();
                }
                if (null != connection) {
                    connection.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                // todo logging
            }
        }

    }

}