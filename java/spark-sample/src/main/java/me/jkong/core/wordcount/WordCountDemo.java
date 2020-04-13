package me.jkong.core.wordcount;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;

/**
 * @author JKong
 * @version v1.0
 * @description 第一个spark项目，统计单词出现次数
 * @date 2020-04-11 15:07.
 */
public class WordCountDemo {

    public static void main(String[] args) {
        // 1. 创建SparkConf对象，设置Spark应用的配置信息
        SparkConf conf = new SparkConf()
                .setAppName("word count local")
                // 设置spark应用程序要连接的spark集群的master节点的URL。如果设置为local，则在本地运行。
                .setMaster("local");

        // 2. 创建JavaSparkContext对象
        // 在 Spark 中，SparkContext 是 Spark 所有功能的一个入口，无论是使用 java | scala，甚至是使用Python编程，
        // 都需要有一个 SparkContext，主要的作用是初始化 Spark 应用程序所需要的一些核心组件，包括：
        // 调度器（DAGSchedule | TaskScheduler），还会到SparkMaster节点上进行注册，等等。
        JavaSparkContext sc = new JavaSparkContext(conf);

        // 3. 针对输入源（HDFS| 本地文件），创建一个初始的RDD。
        // 输入源中的数据会被打散，分配到RDD的每个partition中，从而形成一个初始的分布式的数据集。
        // SparkContext中，根据输入源的文件类型来创建RDD的方法叫做：textFile()
        // 在 Java中，创建的普通的RDD，都叫做JavaRDD。
        // 在 RDD 中有元素的概念，如果是HDFS或者本地文件，创建的RDD对象的每个元素相当于文件中的一行。
        JavaRDD<String> lines = sc.textFile("/Users/zhdh/Desktop/pride-and-prejudice.txt");

        // 4. 对初始的RDD进行transformation操作。先将每一行拆分成单个的单词。
        // 通常，操作会通过创建function，并配合RDD的map、flatmap等算子来执行function。
        // FlatMapFunction 中的两个参数，分别代表着输入和输出类型。
        final JavaRDD<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String line) throws Exception {
                return Arrays.asList(line.split(" ")).iterator();
            }
        });

        // 5. 需要将单词统计为：key：word & value：count 的格式
        // mapToPair 算子，要求是与 PairFunction配合使用，第一个参数是输入类型，第二个参数和第三个参数代表输出类型。
        JavaPairRDD<String, Integer> wordPairs = words.mapToPair(new PairFunction<String, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String word) throws Exception {
                return new Tuple2<>(word, 1);
            }
        });

        // 6. 需要以单词作为key，统计出每个单词出现的次数
        // reduceByKey 算子，需要将相同key的数据进行一个操作，此处是将同一个单词的统计结果进行相加。
        //
        JavaPairRDD<String, Integer> wordCountResult = wordPairs.reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        });

        // 到这里为止，就得到了每个单词出现的次数
        // 我们的新需求，是要按照每个单词出现次数的顺序，降序排序
        // wordCounts RDD内的元素是这种格式：(spark, 3) (hadoop, 2)
        // 因此我们需要将RDD转换成(3, spark) (2, hadoop)的这种格式，才能根据单词出现次数进行排序

        // 7. 进行key-value的反转映射
        JavaPairRDD<Integer, String> countWord = wordCountResult.mapToPair(new PairFunction<Tuple2<String, Integer>, Integer, String>() {
            @Override
            public Tuple2<Integer, String> call(Tuple2<String, Integer> s) throws Exception {
                return new Tuple2<Integer, String>(s._2, s._1);
            }
        });

        // 8. 按照key进行排序
        JavaPairRDD<Integer, String> sortedCountWords = countWord.sortByKey(false);
        // 再次将value-key进行反转映射
        JavaPairRDD<String, Integer> sortedWordCount = sortedCountWords.mapToPair(new PairFunction<Tuple2<Integer, String>, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(Tuple2<Integer, String> s) throws Exception {
                return new Tuple2<String, Integer>(s._2, s._1);
            }
        });

        // 9. 到此为止，我们获得了按照单词出现次数排序后的单词计数
        // 打印出来
        sortedWordCount.foreach(new VoidFunction<Tuple2<String, Integer>>() {
            @Override
            public void call(Tuple2<String, Integer> s) throws Exception {
                System.out.println("word \"" + s._1 + "\" appears " + s._2 + " times.");
            }
        });

        // 关闭上下文
        sc.close();
    }

}