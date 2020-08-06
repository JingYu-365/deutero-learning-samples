package me.jkong.flink.datatype.java;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

/**
 * flink 支持数据类型之 keySelector
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/8/6 22:21.
 */
public class WordCountKeyTypeOfKeySelector {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> stream = env.socketTextStream("localhost", 9999, "\n");
        stream.flatMap(new FlatMapFunction<String, WordCount>() {
            @Override
            public void flatMap(String value, Collector<WordCount> collector) throws Exception {
                String[] tokens = value.toLowerCase().split(",");
                for (String token : tokens) {
                    collector.collect(new WordCount(token.trim(), 1));
                }
            }
        }).keyBy(new KeySelector<WordCount, String>() {
            @Override
            public String getKey(WordCount value) throws Exception {
                return value.word;
            }
        }).timeWindow(Time.seconds(5))
                .sum("count")
                .print()
                .setParallelism(1);

        env.execute("WordCountDataTypeOfPojo");
    }


    public static class WordCount {
        private String word;
        private long count;

        public WordCount(String word, long count) {
            this.word = word;
            this.count = count;
        }

        public WordCount() {
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public long getCount() {
            return count;
        }

        public void setCount(long count) {
            this.count = count;
        }

        @Override
        public String toString() {
            return "WordCount{" +
                    "word='" + word + '\'' +
                    ", count=" + count +
                    '}';
        }
    }
}
