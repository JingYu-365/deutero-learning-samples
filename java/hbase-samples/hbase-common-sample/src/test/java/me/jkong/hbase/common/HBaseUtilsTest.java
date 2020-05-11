package me.jkong.hbase.common;

import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * HBase 工具类测试
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/5/9 11:00.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HBaseUtilsTest {

    @BeforeAll
    static void setUp() {
        System.out.println(" HBaseUtilsTest Begin ... ...");
    }

    @AfterAll
    static void tearDown() {
        HBaseUtils.close();
        System.out.println(" HBaseUtilsTest End ... ...");
    }

    private static String namespace = "jkong";

    @Test
    @Order(1)
    void isExistNamespace() {
        Assertions.assertFalse(HBaseUtils.isExistNamespace(namespace));
    }

    @Test
    @Order(2)
    void createNamespace() {
        Assertions.assertTrue(HBaseUtils.createNamespace(namespace));
        Assertions.assertTrue(HBaseUtils.isExistNamespace(namespace));
    }

    @Test
    @Order(3)
    @Disabled
    void getNamespaceDetail() {
        Map<String, String> namespaceDetail = HBaseUtils.getNamespaceDetail(namespace);
        for (String s : namespaceDetail.keySet()) {
            System.out.println(s + " : " + namespaceDetail.get(s));
        }
    }

    @Test
    @Order(4)
    void deleteNamespace() {
        Assertions.assertTrue(HBaseUtils.deleteNamespace(namespace));
        Assertions.assertFalse(HBaseUtils.isExistNamespace(namespace));
    }

    @Test
    @Order(5)
    @Disabled
    void listAllNamespaceNames() throws IOException {
        HBaseUtils.listAllNamespaceNames().forEach(System.out::println);
    }

    @Test
    @Order(6)
    @Disabled
    void listAllNamespaceInfo() throws IOException {
        NamespaceDescriptor[] descriptors = HBaseUtils.listAllNamespace();
        for (NamespaceDescriptor descriptor : descriptors) {
            Map<String, String> configuration = descriptor.getConfiguration();
            for (String key : configuration.keySet()) {
                System.out.println(key + " : " + configuration.get(key));
            }
        }
    }

    @Test
    void close() {
    }


    @Test
    void listAllNamespace() {
    }

    @Test
    void listTableNamesByNamespace() throws IOException {
        List<TableName> tableNames = HBaseUtils.listTableNamesByNamespace("default");
        for (TableName tableName : tableNames) {
            System.out.println(tableName.getNameAsString());
        }
    }

    @Test
    void createTable() {
    }

    @Test
    void dropTable() {
    }

    @Test
    void isTableExist() {
    }

    @Test
    void listFamiliesByTableName() throws IOException {
        HBaseUtils.listFamilyNameByTableName("default", "user_table").forEach(System.out::println);
        // 如果命名空间为"default"，那么可以省略
        HBaseUtils.listFamilyNameByTableName("user_table").forEach(System.out::println);
    }

    @Test
    void testListFamiliesByTableName() {
    }

    @Test
    void testListFamiliesByTableName1() {
    }

    @Test
    void listFamilyNames() {
    }

    @Test
    void listFamilyNameByTableName() {
    }

    @Test
    void testListFamilyNameByTableName() {
    }

    @Test
    void listFamilyNamesByTableName() {
    }

    @Test
    void listTableColumnNames() {
        try {
            HBaseUtils.listTableColumnNames(TableName.valueOf("user_table"),"information","user-001");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}