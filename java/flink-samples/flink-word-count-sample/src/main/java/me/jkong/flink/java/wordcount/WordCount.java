package me.jkong.flink.java.wordcount;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

/**
 * flink 入门 词频统计
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/8/5 14:55.
 */
public class WordCount {
    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        DataSource<String> source = env.readTextFile("H:\\JKong\\github\\personal-samples\\java\\flink-samples\\flink-word-count-sample\\src\\main\\resources\\data\\wordcount.txt");

        source.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public void flatMap(String value, Collector<Tuple2<String, Integer>> collector) throws Exception {
                String[] tokens = value.split(" ");
                for (String token : tokens) {
                    collector.collect(new Tuple2<>(token, 1));
                }
            }
        }).groupBy(0).sum(1).print();

    }
}