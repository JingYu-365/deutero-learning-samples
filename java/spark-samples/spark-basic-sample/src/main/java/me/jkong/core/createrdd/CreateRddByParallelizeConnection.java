package me.jkong.core.createrdd;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;

import java.util.Arrays;
import java.util.List;

/**
 * @author JKong
 * @version v1.0
 * @description 通过集合创建RDD
 * @date 2020-04-12 09:19.
 */
public class CreateRddByParallelizeConnection {

    public static void main(String[] args) {
        SparkConf conf = new SparkConf()
                .setAppName("CreateRddByParallelizeConnection")
                .setMaster("local");

        JavaSparkContext sc = new JavaSparkContext(conf);

        // 通过并行化集合创建RDD
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        JavaRDD<Integer> parallelizeRdd = sc.parallelize(list);

        // 进行reduce算子操作计算累加和
        Integer result = parallelizeRdd.reduce(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer integer, Integer integer2) throws Exception {
                return integer + integer2;
            }
        });

        System.out.println(result);

        sc.close();
    }
}