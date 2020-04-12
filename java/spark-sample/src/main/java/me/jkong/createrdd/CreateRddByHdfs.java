package me.jkong.createrdd;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;

/**
 * @author JKong
 * @version v1.0
 * @description 通过HDFS文件创建RDD
 * @date 2020-04-12 10:12.
 */
public class CreateRddByHdfs {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("CreateRddByLocalFile").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> dataRdd = sc.textFile("hdfs://localhost:9000/pride-and-prejudice.txt");

        JavaRDD<Integer> lineLength = dataRdd.map(new Function<String, Integer>() {
            @Override
            public Integer call(String s) throws Exception {
                return s.length();
            }
        });

        Integer count = lineLength.reduce(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer integer, Integer integer2) throws Exception {
                return integer + integer2;
            }
        });

        System.out.println("count: " + count);

        sc.close();
    }
}