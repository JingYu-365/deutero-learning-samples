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

/**
 * @author JKong
 * @version v1.0
 * @description 第一个spark项目，统计单词出现次数
 * @date 2020-04-11 15:07.
 */
public class WordCountCluster {

    public static void main(String[] args) {
        // 1. 如果要在集群上部署，需要修改的只有两个地方：
        // - 将 SparkConf 的 setMaster() 方法给删掉，默认会自己回去连接；
        // - 针对的不是本地文件，而是存放在hadoop hdfs 中的文件。
        SparkConf conf = new SparkConf()
                .setAppName("word count cluster");

        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> lines = sc.textFile("hdfs://localhost:9000/pride-and-prejudice.txt");

        // 4. 对初始的RDD进行transformation操作。先将每一行拆分成单个的单词。
        // 通常，操作会通过创建function，并配合RDD的map、flatmap等算子来执行function。
        // FlatMapFunction 中的两个参数，分别代表着输入和输出类型。
        final JavaRDD<String> words =
                lines.flatMap((FlatMapFunction<String, String>) line -> Arrays.asList(line.split(" ")).iterator());

        // 5. 需要将单词统计为：key：word & value：count 的格式
        // mapToPair 算子，要求是与 PairFunction配合使用，第一个参数是输入类型，第二个参数和第三个参数代表输出类型。
        JavaPairRDD<String, Integer> wordPairs =
                words.mapToPair((PairFunction<String, String, Integer>) word -> new Tuple2<>(word, 1));

        // 6. 需要以单词作为key，统计出每个单词出现的次数
        // reduceByKey 算子，需要将相同key的数据进行一个操作，此处是将同一个单词的统计结果进行相加。
        //
        JavaPairRDD<String, Integer> wordCountResult =
                wordPairs.reduceByKey((Function2<Integer, Integer, Integer>) (v1, v2) -> v1 + v2);

        // 到这里为止，就得到了每个单词出现的次数
        // 我们的新需求，是要按照每个单词出现次数的顺序，降序排序
        // wordCounts RDD内的元素是这种格式：(spark, 3) (hadoop, 2)
        // 因此我们需要将RDD转换成(3, spark) (2, hadoop)的这种格式，才能根据单词出现次数进行排序

        // 7. 进行key-value的反转映射
        JavaPairRDD<Integer, String> countWord =
                wordCountResult.mapToPair((PairFunction<Tuple2<String, Integer>, Integer, String>) s -> new Tuple2<>(s._2, s._1));

        // 8. 按照key进行排序
        JavaPairRDD<Integer, String> sortedCountWords = countWord.sortByKey(false);
        // 再次将value-key进行反转映射
        JavaPairRDD<String, Integer> sortedWordCount =
                sortedCountWords.mapToPair((PairFunction<Tuple2<Integer, String>, String, Integer>) s -> new Tuple2<String, Integer>(s._2, s._1));

        // 9. 到此为止，我们获得了按照单词出现次数排序后的单词计数
        // 打印出来
        sortedWordCount.foreach((VoidFunction<Tuple2<String, Integer>>) s -> System.out.println("word \"" + s._1 + "\" appears " + s._2 + " times."));

        // 关闭上下文
        sc.close();
    }

}