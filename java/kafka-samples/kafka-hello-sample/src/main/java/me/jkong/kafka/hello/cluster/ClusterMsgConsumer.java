package me.jkong.kafka.hello.cluster;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Properties;

/**
 * 集群消息消费者
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/8/5 11:44.
 */
public class ClusterMsgConsumer {


    private static Properties props;
    private static final String TOPIC = "test-topic";

    public static void main(String[] args) {
        init();
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(TOPIC));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
            }
        }
    }

    private static void init() {
        props = new Properties();
        props.put("bootstrap.servers", "10.10.32.17:9092,10.10.32.18:9092,10.10.32.19:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    }
}