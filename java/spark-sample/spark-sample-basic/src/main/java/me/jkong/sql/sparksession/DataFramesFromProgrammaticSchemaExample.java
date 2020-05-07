package me.jkong.sql.sparksession;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import java.util.ArrayList;
import java.util.List;

/**
 * 如果无法提前定义JavaBean类，则可以以编程方式动态获取 Dataset<Row>
 * - 从原始RDD创建一个Rows的RDD；
 * - 创建由StructType匹配Row步骤1中创建的RDD中的结构表示的模式。
 * - Row通过createDataFrame提供的方法将模式应用于RDD的SparkSession。
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/4/13 15:42.
 */
public class DataFramesFromProgrammaticSchemaExample {

    public static void main(String[] args) {

        SparkSession spark = SparkSession.builder().appName("Data Frames From Programmatic Schema").master("local").getOrCreate();

        // 1. 创建 RDD
        JavaRDD<String> peopleRDD = spark.sparkContext()
                .textFile("src/main/resources/people.txt", 1)
                .toJavaRDD();

        // 以字符串形式定义 schema
        String schemaString = "name,age";

        // 根据字符串形式 schema 生成 schema 对象
        List<StructField> fields = new ArrayList<>();
        for (String fieldName : schemaString.split(",")) {
            StructField field = DataTypes.createStructField(fieldName, DataTypes.StringType, true);
            fields.add(field);
        }
        StructType schema = DataTypes.createStructType(fields);

        // 将RDD（People）的记录转换为 row RDD
        JavaRDD<Row> rowRDD = peopleRDD.map((Function<String, Row>) record -> {
            String[] attributes = record.split(",");
            return RowFactory.create(attributes[0], attributes[1].trim());
        });

        // 将 schema 应用于 RDD，生成 DataFrame 对象
        Dataset<Row> peopleDataFrame = spark.createDataFrame(rowRDD, schema);

        // 使用DataFrame创建一个临时视图
        peopleDataFrame.createOrReplaceTempView("people");

        // 在使用DataFrames创建的临时视图上运行SQL
        Dataset<Row> results = spark.sql("SELECT name FROM people");
        results.show();

        // SQL查询的结果是DataFrames，并支持所有正常的RDD操作
        // 可以通过字段索引或字段名称访问结果中一行的列
        Dataset<String> namesDS = results.map(
                (MapFunction<Row, String>) row -> "Name: " + row.getString(0),
                Encoders.STRING());
        namesDS.show();
        spark.stop();
    }
}