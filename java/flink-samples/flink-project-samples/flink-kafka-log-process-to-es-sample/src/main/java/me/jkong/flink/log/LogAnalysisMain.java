package me.jkong.flink.log;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Tuple3;
import scala.Tuple4;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Properties;

/**
 * 从 Kafka 中获取日志数据，处理后将数据装载到ES中
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/8/20 21:19.
 */
public class LogAnalysisMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogAnalysisMain.class);

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");
        properties.setProperty("group.id", "test-group");

        String topic = "test-topic";

        FlinkKafkaConsumer<String> kafkaConsumer = new FlinkKafkaConsumer<>(topic, new SimpleStringSchema(), properties);
        kafkaConsumer.assignTimestampsAndWatermarks(
                WatermarkStrategy.forBoundedOutOfOrderness(Duration.ofSeconds(10)));
        DataStreamSource<String> consumerData = env.addSource(kafkaConsumer);

        // 清洗数据
        SingleOutputStreamOperator<Tuple3<Long, String, Long>> processedData = consumerData.map(new MapFunction<String, Tuple4<String, Long, String, Long>>() {
            @Override
            public Tuple4<String, Long, String, Long> map(String value) throws Exception {
                String[] splits = value.split("\t");
                String level = splits[2];

                String timeStr = splits[3];
                long time = 0L;
                try {
                    SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    time = sourceFormat.parse(timeStr).getTime();
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                }

                String domain = splits[5];
                long traffic = Long.parseLong(splits[6]);
                return new Tuple4<>(level, time, domain, traffic);
            }
        }).filter(item -> item._2() > 0).filter(item -> !item._1().equals("E"))
                .map(new MapFunction<Tuple4<String, Long, String, Long>, Tuple3<Long, String, Long>>() {
                    @Override
                    public Tuple3<Long, String, Long> map(Tuple4<String, Long, String, Long> value) throws Exception {
                        return new Tuple3<>(value._2(), value._3(), value._4());
                    }
                });

        if (LOGGER.isDebugEnabled()) {
            processedData.print().setParallelism(1);
        }

        // 数据统计
        SingleOutputStreamOperator<Tuple3<String, String, Long>> resultData = processedData.keyBy(1)
                .window(TumblingEventTimeWindows.of(Time.seconds(10)))
                .apply(new WindowFunction<Tuple3<Long, String, Long>, Tuple3<String, String, Long>, Tuple, TimeWindow>() {
                    @Override
                    public void apply(Tuple tuple, TimeWindow window, Iterable<Tuple3<Long, String, Long>> input, Collector<Tuple3<String, String, Long>> out) throws Exception {

                    }
                });


        // 输出数据


        env.execute("LogAnalysisMain");

    }
}
