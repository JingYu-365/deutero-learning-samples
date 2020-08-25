package me.jkong.flink.stream.basic.datasource.socket;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

/**
 * 通过socket获取数据
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/8/25 21:44.
 */
public class SocketBasedDataSource {
    /**
     * 第一步： nc -lk 9999
     * 第二步： 启动服务
     * 第三步： 在控制台输入数据
     */
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<Tuple2<String, Integer>> dataStream = env
                .socketTextStream("127.0.0.1", 9999)
                .flatMap(new SocketBasedDataSource.Splitter())
                .keyBy((KeySelector<Tuple2<String, Integer>, String>) value -> value.f0)
                .timeWindow(Time.seconds(5))
                .sum(1);
        dataStream.print();
        env.execute("Window WordCount");
    }

    public static class Splitter implements FlatMapFunction<String, Tuple2<String, Integer>> {
        @Override
        public void flatMap(String sentence, Collector<Tuple2<String, Integer>> out) throws Exception {
            for (String word : sentence.split(" ")) {
                out.collect(new Tuple2<>(word, 1));
            }
        }
    }
}
