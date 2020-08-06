package me.jkong.hbase.common;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.client.coprocessor.AggregationClient;
import org.apache.hadoop.hbase.client.coprocessor.LongColumnInterpreter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.*;
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

    private static Configuration configuration() {
        return HBaseConfig.getConfiguration();
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
                List<TableName> tableNames = listTableNamesByNamespace(namespace);
                if (tableNames != null && tableNames.size() > 0) {
                    return false;
                }
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
     * 创建表
     *
     * @param tableName    表名
     * @param columnFamily 例族
     */
    public static void createTable(String tableName, String... columnFamily) throws IOException {
        if (!isTableExist(TableName.valueOf(tableName))) {
            HTableDescriptor descriptor = new HTableDescriptor(TableName.valueOf(tableName));
            for (String cf : columnFamily) {
                descriptor.addFamily(new HColumnDescriptor(cf));
            }
            admin().createTable(descriptor);
        }
    }

    /**
     * 删除表
     *
     * @param tableName 表名
     * @return true：删除成功
     */
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
     * 更新表名
     *
     * @param oldTableName 需要修改的表的表名
     * @param newTableName 新表名
     * @return true 修改成功
     * @throws IOException e
     */
    public static boolean updateTableName(String oldTableName, String newTableName) throws IOException {
        return updateTableName(TableName.valueOf(oldTableName), TableName.valueOf(newTableName));
    }

    public static boolean updateTableName(TableName oldTableName, TableName newTableName) throws IOException {

        if (oldTableName == null || newTableName == null) {
            throw new IllegalArgumentException("table name is null.");
        }

        if (!isTableExist(oldTableName)) {
            throw new TableNotFoundException(String.format("table named %s not found.", oldTableName.getNameAsString()));
        } else if (isTableExist(newTableName)) {
            throw new TableExistsException(String.format("table named %s exist.", newTableName.getNameAsString()));
        }

        String snapshotName = UUID.randomUUID().toString();
        Admin admin = admin();
        if (disableTable(oldTableName)) {
            admin.snapshot(snapshotName, oldTableName);
            admin.cloneSnapshot(snapshotName, newTableName);
            admin.deleteSnapshot(snapshotName);
            admin.deleteTable(oldTableName);
        }
        return true;
    }

    /**
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

    /**
     * 清空数据表
     *
     * @param tableName      表名
     * @param preserveSplits <code>true</code> 是否应保留拆分
     * @throws IOException e
     */
    public static void truncateTable(TableName tableName, boolean preserveSplits) throws IOException {
        Admin admin = admin();
        if (disableTable(tableName)) {
            admin.truncateTable(tableName, preserveSplits);
        }
    }


    /**
     * 启用table
     *
     * @param tableName 表名
     * @return true：启动成功
     */
    public static boolean enableTable(TableName tableName) {
        Admin admin = admin();
        try {
            if (admin.isTableAvailable(tableName)) {
                if (admin.isTableDisabled(tableName)) {
                    admin.enableTable(tableName);
                }
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 禁用table
     *
     * @param tableName 表名
     * @return true：禁用成功
     */
    public static boolean disableTable(TableName tableName) {
        Admin admin = admin();
        try {
            if (admin.isTableAvailable(tableName)) {
                if (admin.isTableEnabled(tableName)) {
                    admin.disableTable(tableName);
                }
            }
            return true;
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
    public static ColumnFamilyDescriptor[] listFamiliesByTableName(String namespace, String tableName) throws IOException {
        return listFamiliesByTableName(TableName.valueOf(namespace, tableName));
    }

    public static ColumnFamilyDescriptor[] listFamiliesByTableName(String tableName) throws IOException {
        return listFamiliesByTableName(TableName.valueOf(tableName));
    }

    public static List<String> listFamilyNameByTableName(String namespace, String tableName) throws IOException {
        return listFamilyNamesByTableName(TableName.valueOf(namespace, tableName));
    }

    public static List<String> listFamilyNameByTableName(String tableName) throws IOException {
        return listFamilyNamesByTableName(TableName.valueOf(tableName));
    }

    /**
     * 根据表名获取表的列簇信息
     *
     * @param tableName 表名称
     * @return family descriptors
     */
    public static ColumnFamilyDescriptor[] listFamiliesByTableName(TableName tableName) throws IOException {
        if (tableName == null) {
            return new ColumnFamilyDescriptor[0];
        }

        try (Table table = connection().getTable(tableName)) {
            return table.getDescriptor().getColumnFamilies();
        }
        // ignore
    }

    public static List<String> listFamilyNamesByTableName(TableName tableName) throws IOException {
        ColumnFamilyDescriptor[] familyDescriptors = listFamiliesByTableName(tableName);
        if (familyDescriptors == null || familyDescriptors.length == 0) {
            return Collections.emptyList();
        }
        return Stream.of(familyDescriptors).map(ColumnFamilyDescriptor::getNameAsString).collect(Collectors.toList());
    }


    /**
     * 为指定表添加例族
     *
     * @param tableName  表名
     * @param familyName 例族名
     * @return true：添加成功
     */
    public static boolean addFamilyName(String tableName, String familyName) throws IOException {
        if (tableName == null || tableName.trim().length() == 0 ||
                familyName == null || familyName.trim().length() == 0) {
            throw new IllegalArgumentException("table name or family name is null.");
        }
        return addFamilyName(TableName.valueOf(tableName), familyName);
    }

    public static boolean addFamilyName(TableName tableName, String familyName) throws IOException {
        if (tableName == null || familyName == null || familyName.trim().length() == 0) {
            throw new IllegalArgumentException("family name or table name is null.");
        }

        if (!isTableExist(tableName)) {
            throw new TableNotFoundException(String.format("table named %s not found.", tableName.getNameAsString()));
        }

        if (disableTable(tableName)) {
            // adding new ColumnFamily
            ColumnFamilyDescriptor cf = ColumnFamilyDescriptorBuilder.of(familyName);
            admin().addColumnFamily(tableName, cf);
        }
        return true;
    }

    /**
     * 更新指定表的例族名称
     *
     * @param tableName     表名
     * @param oldFamilyName 需要修改的例族名称
     * @param newFamilyName 新的例族名称
     * @return true：修改成功
     * @throws IOException e
     */
    public static boolean updateFamilyName(String tableName, String oldFamilyName, String newFamilyName) throws IOException {
        if (tableName == null || tableName.trim().length() == 0 ||
                oldFamilyName == null || oldFamilyName.trim().length() == 0 ||
                newFamilyName == null || newFamilyName.trim().length() == 0) {
            throw new IllegalArgumentException("family name or table name is null.");
        }
        return updateFamilyName(TableName.valueOf(tableName), oldFamilyName, newFamilyName);
    }

    public static boolean updateFamilyName(TableName tableName, String oldFamilyName, String newFamilyName)
            throws IOException {
        if (tableName == null || oldFamilyName == null || oldFamilyName.trim().length() == 0
                || newFamilyName == null || newFamilyName.trim().length() == 0) {
            throw new IllegalArgumentException("family name or table name is null.");
        }

        if (!isTableExist(tableName)) {
            throw new TableNotFoundException(String.format("table named %s not found.", tableName.getNameAsString()));
        }

        if (!isExistNamespace(oldFamilyName)) {
            throw new NamespaceNotFoundException(oldFamilyName + " not exists. ");
        } else if (isExistNamespace(newFamilyName)) {
            throw new NamespaceExistException(newFamilyName + " exists.");
        }

        Admin admin = admin();
        if (disableTable(tableName)) {
            TableDescriptor tableDesc = admin.getDescriptor(tableName);
            ColumnFamilyDescriptor tempColumnDesc = tableDesc.getColumnFamily(Bytes.toBytes(oldFamilyName));
            if (tempColumnDesc == null) {
                throw new NamespaceNotFoundException(oldFamilyName + " is null column ");
            } else {
                // modifying existing ColumnFamily
                ColumnFamilyDescriptor cf2 = ColumnFamilyDescriptorBuilder.of(newFamilyName);
                admin.modifyColumnFamily(tableName, cf2);
            }
        }
        return true;
    }

    /**
     * 获取表中指定family的字段信息
     */
    public static void listTableColumnNames(TableName tableName, String familyName, String rowKey) throws IOException {
        ResultScanner scanner = null;
        try {
            final Scan scan = new Scan();
            scanner = connection().getTable(tableName).getScanner(scan);
            if (scanner == null) {
                return;
            }
            if (rowKey == null) {
                rowKey = "";
            }
            for (Result res : scanner) {
                List<Cell> cells = res.listCells();
                if (cells != null && cells.size() > 0) {
                    rowKey = CellUtil.toString(cells.get(0), false).split("/")[0];
                }
            }
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        // TODO: 2020/5/19  

        final Get get = new Get(Bytes.toBytes(rowKey));
        Result result = connection().getTable(tableName).get(get);
        Map<byte[], byte[]> familyMap = result.getFamilyMap(Bytes.toBytes(familyName));
        System.out.println(rowKey);
        for (Map.Entry<byte[], byte[]> entry : familyMap.entrySet()) {
            System.out.println(Bytes.toString(entry.getKey()));
        }
    }

    /**
     * 统计表中数据条数  todo: 待测试
     *
     * @param tableName 表名
     * @return 行数
     * @throws Throwable e
     */
    public static long countTableRow(TableName tableName) throws Throwable {
        Scan scan = new Scan();
        AggregationClient aggregationClient = new AggregationClient(configuration());
        return aggregationClient.rowCount(tableName, new LongColumnInterpreter(), scan);

    }

    public static void scanTableShow(TableName tableName) throws IOException {
        Scan scan = new Scan();
        ResultScanner scanner = connection().getTable(tableName).getScanner(scan);
        for (Result result : scanner) {
            System.out.println(result);
        }
    }


    /************************************ grant operation for hbase *****************************************
     * 对用户授权
     */
    public static void userPermission() {
    }


    private static class HBaseConfig {

        private static final String HBASE_ROOT_DIR = "hdfs://10.10.27.47:9000/hbase";

        private static final String HBASE_ZOOKEEPER_QUORUM = "10.10.27.47:2181,10.10.27.48:2181,10.10.27.49:2181";
//        private static final String HBASE_ZOOKEEPER_QUORUM = "10.10.27.47,10.10.27.48,10.10.27.49";

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
//                configuration.set("hbase.zookeeper.property.clientPort", HBASE_ZOOKEEPER_PROPERTY_CLIENT_PORT);
                configuration.set("hbase.zookeeper.quorum", HBASE_ZOOKEEPER_QUORUM);
//                configuration.set("hbase.rootdir", HBASE_ROOT_DIR);
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

    public static void main(String[] args) throws IOException {
        Scan scan = new Scan();
        scan.withStartRow(Bytes.toBytes("user-012"),false);
        Filter filter = new PageFilter(1);
        scan.setFilter(filter);
        ResultScanner scanner = connection().getTable(TableName.valueOf("user_table")).getScanner(scan);
        for (Result result : scanner) {
            System.out.println(result);
        }
    }

}