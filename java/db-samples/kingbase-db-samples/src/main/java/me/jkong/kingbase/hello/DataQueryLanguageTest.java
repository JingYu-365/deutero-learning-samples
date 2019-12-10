package me.jkong.kingbase.hello;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.google.gson.GsonBuilder;
import com.kingbase8.jdbc.KbResultSetMetaData;
import me.jkong.kingbase.pool.DataBaseInitializer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author JKong
 * @version v1.0
 * @description DQL
 * @date 2019/12/3 11:52.
 */
public class DataQueryLanguageTest {

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
    public void queryTableMetadataTest() throws SQLException {

        Map<String, String> fieldsMap = initFieldsMap();

        String sql = "SELECT * FROM JKONG_TEST.products WHERE PRODUCT_ID = 2";
        ResultSet resultSet = statement.executeQuery(sql);

        KbResultSetMetaData metaData = (KbResultSetMetaData) resultSet.getMetaData();
        List<String> nameList = new ArrayList<>();

        for (int i = 0; i < metaData.getColumnCount(); i++) {
            nameList.add(metaData.getBaseColumnName(i + 1));
        }
        System.out.println(nameList);

        List<Map<String, Object>> resultList = new ArrayList<>();
        while (resultSet.next()) {
            Map<String, Object> resultMap = new HashMap<>(nameList.size());
            for (String name : nameList) {
                resultMap.put(fieldsMap.get(name), resultSet.getObject(name));
            }
            resultList.add(resultMap);
        }
        System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(resultList));
        assert resultList.size() == 1;
    }

    private Map<String,String> initFieldsMap() {
        String[] split = "product_id,product_name,author,publisher,publishtime,product_subcategoryid,productno,satetystocklevel,originalprice".split(",");

        Map<String,String> fields = new HashMap<>(16);
        for (String filed : split) {
            fields.put(filed.toUpperCase(),filed);
        }
        return fields;
    }
}