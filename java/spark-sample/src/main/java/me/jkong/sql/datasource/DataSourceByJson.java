package me.jkong.sql.datasource;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.Arrays;
import java.util.List;

/**
 * Spark SQL可以自动推断JSON数据集的模式并将其加载为Dataset<Row>。
 * 可以在Dataset<String>或JSON文件上使用SparkSession.read().json()完成此转换。
 * <p>
 * 对于常规多行JSON文件，请将multiLine选项设置为true。
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/4/13 17:11.
 */
public class DataSourceByJson {

    public static void main(String[] args) {
        // 1. 创建 SparkSession 对象
        SparkSession spark = SparkSession.builder().master("local").appName("DataSourceByJson").getOrCreate();

        // 2. 指定JSON数据集路径。路径可以是单个文本文件，也可以是存储文本文件的目录
        Dataset<Row> people = spark.read().json("src/main/resources/people.json");

        // 3. 可以使用printSchema（）方法可视化推断的架构
        // root
        //  |-- age: long (nullable = true)
        //  |-- name: string (nullable = true)
        people.printSchema();

        // 4. 使用DataFrame创建一个临时视图
        people.createOrReplaceTempView("people");

        // 5. 使用spark提供的sql方法运行SQL语句
        Dataset<Row> namesDF = spark.sql("SELECT name FROM people WHERE age BETWEEN 13 AND 27");
        // +------+
        // |  name|
        // +------+
        // |Justin|
        // +------+
        namesDF.show();

        // 6. 为由Dataset<String>表示的JSON数据集创建DataFrame，每个数据集存储一个JSON对象。
        List<String> jsonData = Arrays.asList(
                "{\"name\":\"Yin\",\"address\":{\"city\":\"Columbus\",\"state\":\"Ohio\"}}");
        Dataset<String> anotherPeopleDataset = spark.createDataset(jsonData, Encoders.STRING());
        Dataset<Row> anotherPeopleDF = spark.read().json(anotherPeopleDataset);
        // +---------------+----+
        // |        address|name|
        // +---------------+----+
        // |[Columbus,Ohio]| Yin|
        // +---------------+----+
        anotherPeopleDF.show();
    }
}