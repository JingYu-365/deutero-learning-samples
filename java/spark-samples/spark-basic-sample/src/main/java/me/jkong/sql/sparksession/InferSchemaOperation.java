package me.jkong.sql.sparksession;

import me.jkong.common.Person;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.*;

/**
 * Spark SQL支持自动将JavaBeans的RDD转换为DataFrame。
 * BeanInfo使用反射得到，定义了表的模式。
 * <p>
 * 目前，Spark SQL不支持包含Map字段的JavaBean。
 * 但是支持嵌套的JavaBeans和List或Array字段。
 * <p>
 * 我们可以通过创建实现Serializable的类来创建JavaBean，并为其所有字段设置getter和setter。
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/4/13 15:21.
 */
public class InferSchemaOperation {
    public static void main(String[] args) {
        SparkSession spark = SparkSession.builder().master("local").appName("InferSchemaOperation").getOrCreate();
        // 1. 基于 txt 文件创建 Person 类的RDD对象
        JavaRDD<Person> peopleRDD = spark.read()
                .textFile("src/main/resources/people.txt")
                .javaRDD()
                .map(line -> {
                    String[] parts = line.split(",");
                    Person person = new Person();
                    person.setName(parts[0]);
                    person.setAge(Integer.parseInt(parts[1].trim()));
                    return person;
                });

        // 2. 将模式应用于JavaBean的RDD以获取DataFrame
        Dataset<Row> peopleDF = spark.createDataFrame(peopleRDD, Person.class);
        // 3. 将DataFrame注册为临时视图
        peopleDF.createOrReplaceTempView("people");

        // 4. 使用spark提供的sql方法运行SQL语句
        Dataset<Row> teenagersDF = spark.sql("SELECT name FROM people WHERE age BETWEEN 13 AND 27");
        teenagersDF.show();

        // 5. 可以通过 字段索引 访问结果中一行的列
        Encoder<String> stringEncoder = Encoders.STRING();
        Dataset<String> teenagerNamesByIndexDF = teenagersDF.map(
                (MapFunction<Row, String>) row -> "Name: " + row.getString(0),
                stringEncoder);
        teenagerNamesByIndexDF.show();

        // 5. 可以通过 字段名称 访问结果中一行的列
        Dataset<String> teenagerNamesByFieldDF = teenagersDF.map(
                (MapFunction<Row, String>) row -> "Name: " + row.<String>getAs("name"),
                stringEncoder);
        teenagerNamesByFieldDF.show();

        spark.stop();
    }
}