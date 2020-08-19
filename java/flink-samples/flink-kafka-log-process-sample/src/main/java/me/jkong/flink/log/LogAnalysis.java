package me.jkong.flink.log;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Tuple3;
import scala.Tuple4;

import java.text.SimpleDateFormat;
import java.util.Properties;

/**
 * 消费Kafka中的日志数据进行数据统计
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/8/19 21:20.
 */
public class LogAnalysis {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogAnalysis.class);

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");
        properties.setProperty("group.id", "test-log-group");
        String topic = "log-topic";

        DataStream<String> stream = env.addSource(new FlinkKafkaConsumer<>(topic, new SimpleStringSchema(), properties));

        // 清洗数据，将数据转换为 time，domain，traffic 格式
        SingleOutputStreamOperator<Tuple3<Long, String, Long>> logDataStream = stream.map(new MapFunction<String, Tuple4<String, Long, String, Long>>() {
            @Override
            public Tuple4<String, Long, String, Long> map(String data) throws Exception {
                String[] splits = data.split("\t");
                String level = splits[2];
                String timeStr = splits[3];
                Long time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timeStr).getTime();
                String domain = splits[5];
                Long traffic = Long.parseLong(splits[6]);
                return new Tuple4<>(level, time, domain, traffic);
            }
        }).filter(item -> item._2() != 0)
                .filter(item -> item._1().equals("E"))
                .map(new MapFunction<Tuple4<String, Long, String, Long>, Tuple3<Long, String, Long>>() {
                    @Override
                    public Tuple3<Long, String, Long> map(Tuple4<String, Long, String, Long> value) throws Exception {
                        return new Tuple3<>(value._2(), value._3(), value._4());
                    }
                });

        if (LOGGER.isDebugEnabled()) {
            logDataStream.print().setParallelism(1);
        }








        env.execute("LogAnalysis");
    }
}
