package me.jkong.flink.stream.basic.datasource.collection;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.LocalStreamEnvironment;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.ArrayList;
import java.util.List;

/**
 * 基于集合的数据源
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/8/25 22:03.
 */
public class CollectionBasedDataSource {
    public static void main(String[] args) throws Exception {
        LocalStreamEnvironment env = StreamExecutionEnvironment.createLocalEnvironment();
        DataStreamSource<Tuple2<String, Integer>> source = env.fromCollection(getData());
        source.map(new MapFunction<Tuple2<String, Integer>, Tuple2<String, Integer>>() {
            @Override
            public Tuple2<String, Integer> map(Tuple2<String, Integer> value) throws Exception {
                return new Tuple2<>(value.f0, value.f1 * value.f1);
            }
        }).print();
        env.execute("CollectionBasedDataSource");
    }


    public static List<Tuple2<String, Integer>> getData() {
        List<Tuple2<String, Integer>> data = new ArrayList<>();
        Tuple2<String, Integer> tuple1 = new Tuple2<>("zhangsan", 13);
        Tuple2<String, Integer> tuple2 = new Tuple2<>("lisi", 14);
        Tuple2<String, Integer> tuple3 = new Tuple2<>("wangwu", 15);
        Tuple2<String, Integer> tuple4 = new Tuple2<>("zhaoliu", 16);
        data.add(tuple1);
        data.add(tuple2);
        data.add(tuple3);
        data.add(tuple4);
        return data;
    }
}
