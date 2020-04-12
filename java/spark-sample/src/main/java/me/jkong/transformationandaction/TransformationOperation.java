package me.jkong.transformationandaction;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.VoidFunction;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author JKong
 * @version v1.0
 * @description transformation 操作实战
 * @date 2020-04-12 11:13.
 */
public class TransformationOperation {


    public static void main(String[] args) {
//        map();
//        filter();
//        flatMap();
//        groupByKey();
//        reduceByKey();
//        sortByKey();
        join();
    }


    /**
     * 排序
     */
    private static void join() {
        SparkConf conf = new SparkConf().setMaster("local").setAppName("sortByKey operation");
        JavaSparkContext sc = new JavaSparkContext(conf);
        List<Tuple2<Integer, String>> user = Arrays.asList(
                new Tuple2<>(1, "leo"),
                new Tuple2<>(2, "lili"),
                new Tuple2<>(3, "tom")
        );

        List<Tuple2<Integer, Integer>> score = Arrays.asList(
                new Tuple2<>(1, 89),
                new Tuple2<>(2, 99),
                new Tuple2<>(1, 99),
                new Tuple2<>(3, 98),
                new Tuple2<>(3, 78),
                new Tuple2<>(3, 79)
        );

        JavaPairRDD<Integer, String> userData = sc.parallelizePairs(user);
        JavaPairRDD<Integer, Integer> scoreData = sc.parallelizePairs(score);

        // 排序，添加参数 false，则按照降序排序，否则为升序
        JavaPairRDD<Integer, Tuple2<String, Integer>> joinPair = userData.join(scoreData);


        // 将 sortedPair 数据打印
        joinPair.foreach(new VoidFunction<Tuple2<Integer, Tuple2<String, Integer>>>() {
            @Override
            public void call(Tuple2<Integer, Tuple2<String, Integer>> el) throws Exception {
                System.out.println("user id:" + el._1() + ", name: " + el._2()._1() + ", score: " + el._2()._2());
            }
        });
        sc.close();
    }

    /**
     * 排序
     */
    private static void sortByKey() {
        SparkConf conf = new SparkConf().setMaster("local").setAppName("sortByKey operation");
        JavaSparkContext sc = new JavaSparkContext(conf);
        List<Tuple2<Integer, String>> data = Arrays.asList(
                new Tuple2<Integer, String>(99, "leo"),
                new Tuple2<Integer, String>(67, "lili"),
                new Tuple2<Integer, String>(56, "tom"),
                new Tuple2<Integer, String>(45, "tommy"),
                new Tuple2<Integer, String>(34, "shaddy"),
                new Tuple2<Integer, String>(12, "homi"),
                new Tuple2<Integer, String>(99, "haddy")
        );
        JavaPairRDD<Integer, String> parallelizeData = sc.parallelizePairs(data);

        // 排序，添加参数 false，则按照降序排序，否则为升序
        JavaPairRDD<Integer, String> sortedPair = parallelizeData.sortByKey(false);


        // 将 sortedPair 数据打印
        sortedPair.foreach(new VoidFunction<Tuple2<Integer, String>>() {
            @Override
            public void call(Tuple2<Integer, String> data) throws Exception {
                System.out.println("class: " + data._1() + ", score count: " + data._2());
            }
        });

        sc.close();
    }

    /**
     * 统计
     */
    private static void reduceByKey() {
        SparkConf conf = new SparkConf().setMaster("local").setAppName("reduceByKey operation");
        JavaSparkContext sc = new JavaSparkContext(conf);
        List<Tuple2<String, Integer>> data = Arrays.asList(
                new Tuple2<String, Integer>("class1", 99),
                new Tuple2<String, Integer>("class3", 67),
                new Tuple2<String, Integer>("class2", 56),
                new Tuple2<String, Integer>("class3", 45),
                new Tuple2<String, Integer>("class3", 34),
                new Tuple2<String, Integer>("class2", 12),
                new Tuple2<String, Integer>("class1", 99)
        );
        JavaPairRDD<String, Integer> parallelizeData = sc.parallelizePairs(data);

        // 将文本拆分成单词使用flatMap算子
        JavaPairRDD<String, Integer> pairRDD = parallelizeData.reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        });

        // 将 pairRDD 数据打印
        pairRDD.foreach(new VoidFunction<Tuple2<String, Integer>>() {
            @Override
            public void call(Tuple2<String, Integer> data) throws Exception {
                System.out.println("class: " + data._1() + ", score count: " + data._2());
            }
        });

        sc.close();
    }

    /**
     * 分类
     */
    private static void groupByKey() {
        SparkConf conf = new SparkConf().setMaster("local").setAppName("groupByKey operation");
        JavaSparkContext sc = new JavaSparkContext(conf);
        List<Tuple2<String, Integer>> data = Arrays.asList(
                new Tuple2<String, Integer>("class1", 99),
                new Tuple2<String, Integer>("class3", 67),
                new Tuple2<String, Integer>("class2", 56),
                new Tuple2<String, Integer>("class3", 45),
                new Tuple2<String, Integer>("class3", 34),
                new Tuple2<String, Integer>("class2", 12),
                new Tuple2<String, Integer>("class1", 99)
        );
        JavaPairRDD<String, Integer> parallelizeData = sc.parallelizePairs(data);

        // 将文本拆分成单词使用flatMap算子
        JavaPairRDD<String, Iterable<Integer>> pairRDD = parallelizeData.groupByKey();

        // 将 pairRDD 数据打印
        pairRDD.foreach(new VoidFunction<Tuple2<String, Iterable<Integer>>>() {
            @Override
            public void call(Tuple2<String, Iterable<Integer>> el) throws Exception {
                System.out.println("class name: " + el._1);
                Iterable<Integer> scores = el._2();
                scores.forEach(System.out::println);
            }
        });

        sc.close();
    }

    /**
     * 将文本拆分成单词
     */
    private static void flatMap() {
        SparkConf conf = new SparkConf().setMaster("local").setAppName("flatMap operation");
        JavaSparkContext sc = new JavaSparkContext(conf);
        List<String> data = Arrays.asList("hello you", "hello me", "hello king", "hello kong");
        JavaRDD<String> parallelizeData = sc.parallelize(data);

        // 将文本拆分成单词使用flatMap算子
        JavaRDD<String> flatMapRdd = parallelizeData.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String s) throws Exception {
                return Arrays.asList(s.split(" ")).iterator();
            }
        });

        flatMapRdd.foreach(new VoidFunction<String>() {
            @Override
            public void call(String str) throws Exception {
                System.out.println(str);
            }
        });

        sc.close();
    }

    /**
     * 将偶数过滤掉
     */
    private static void filter() {
        SparkConf conf = new SparkConf().setMaster("local").setAppName("filter operation");
        JavaSparkContext sc = new JavaSparkContext(conf);
        List<Integer> data = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        JavaRDD<Integer> parallelizeData = sc.parallelize(data);

        // 使用filter算子过滤掉偶数
        JavaRDD<Integer> mapRdd = parallelizeData.filter(new Function<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) throws Exception {
                return integer % 2 != 0;
            }
        });
        mapRdd.foreach(new VoidFunction<Integer>() {
            @Override
            public void call(Integer num) throws Exception {
                System.out.println(num);
            }
        });

        sc.close();
    }

    /**
     * 将m每个元素都乘以2
     */
    private static void map() {
        SparkConf conf = new SparkConf().setMaster("local").setAppName("map operation");
        JavaSparkContext sc = new JavaSparkContext(conf);
        List<Integer> data = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        JavaRDD<Integer> parallelizeData = sc.parallelize(data);

        JavaRDD<Integer> mapRdd = parallelizeData.map((Function<Integer, Integer>) integer -> integer * 2);
        mapRdd.foreach(new VoidFunction<Integer>() {
            @Override
            public void call(Integer num) throws Exception {
                System.out.println(num);
            }
        });

        sc.close();
    }
}