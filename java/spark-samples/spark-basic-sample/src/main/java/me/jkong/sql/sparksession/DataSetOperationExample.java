package me.jkong.sql.sparksession;

import me.jkong.common.Person;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;

import java.util.Arrays;
import java.util.Collections;

/**
 * 使用 DataSet 操作数据
 * <p>
 * 数据集与RDD类似，但是，他们不使用Java序列化或Kryo，
 * 而是使用专用的编码器来序列化对象以便通过网络进行处理或传输。
 * <p>
 * 虽然编码器和标准序列化都负责将对象转换为字节，但编码器是动态生成的代码，
 * 并使用一种格式，允许Spark执行许多操作，如过滤、排序和散列，而无需将字节反序列化为对象。
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/4/13 15:04.
 */
public class DataSetOperationExample {
    public static void main(String[] args) {
        SparkSession spark = SparkSession.builder()
                .appName("Java Spark SQL Dataset example")
                .master("local")
                .getOrCreate();

        // 实例化类对象
        Person person = new Person();
        person.setName("JKong");
        person.setAge(27);

        // 创建 People 类的编码器，并创建 People 的数据集
        Encoder<Person> personEncoder = Encoders.bean(Person.class);
        Dataset<Person> personDataset = spark.createDataset(
                Collections.singletonList(person),
                personEncoder
        );
        //+---+-----+
        //|age| name|
        //+---+-----+
        //| 27|JKong|
        //+---+-----+
        personDataset.show();

        // 使用 Encoders 提供的编码器进行数据编码
        Encoder<Integer> integerEncoder = Encoders.INT();
        Dataset<Integer> primitiveDS = spark.createDataset(Arrays.asList(1, 2, 3), integerEncoder);
        Dataset<Integer> transformedDS = primitiveDS.map(
                (MapFunction<Integer, Integer>) value -> value + 1,
                integerEncoder);
        // Returns [2, 3, 4]
        transformedDS.collect();
        //+-----+
        //|value|
        //+-----+
        //|    2|
        //|    3|
        //|    4|
        //+-----+
        transformedDS.show();

        // 指定 类编码器 基于属性的名称，将 DataFrames 数据转为 Dataset 数据
        String path = "src/main/resources/people.json";
        personDataset = spark.read().json(path).as(personEncoder);
        //+----+-------+
        //| age|   name|
        //+----+-------+
        //|null|Michael|
        //|  27|  JKong|
        //|  29| Justin|
        //|  27|   Nick|
        //+----+-------+
        personDataset.show();

        spark.stop();
    }
}