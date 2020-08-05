package me.jkong.kafka.hello.cluster;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * 集群消息生产者
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/8/5 11:43.
 */
public class ClusterMsgProducer {

    private static Properties props;
    private static final String TOPIC = "test-topic";

    public static void main(String[] args) {
        // 初始化配置信息
        init();
        // 构建消息生产者
        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 36; i++) {
            // 发送消息
            producer.send(new ProducerRecord<>(TOPIC, Integer.toString(i), Integer.toString(i)));
        }
        producer.close();
    }

    private static void init() {
        props = new Properties();
        props.put("bootstrap.servers", "10.10.32.17:9092,10.10.32.18:9092,10.10.32.19:9092");
        props.put("retries", 0);
        props.put("acks", "all");
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    }
}