package me.jkong.flink.stream.basic.window;

import me.jkong.flink.stream.basic.datasource.socket.SocketBasedDataSource;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;

/**
 * TODO
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/8/26 06:54.
 */
public class TimeWindowOperation {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime);
        // alternatively:
        // env.setStreamTimeCharacteristic(TimeCharacteristic.IngestionTime);
        // env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        DataStream<Tuple2<String, Integer>> dataStream = env
                .socketTextStream("127.0.0.1", 9999)
                .flatMap(new SocketBasedDataSource.Splitter())
                .keyBy((KeySelector<Tuple2<String, Integer>, String>) value -> value.f0)
                // 设置时间窗口
                .timeWindow(Time.seconds(5))
                .sum(1);
        dataStream.print();
        env.execute("SocketBasedDataSource");
    }
}
