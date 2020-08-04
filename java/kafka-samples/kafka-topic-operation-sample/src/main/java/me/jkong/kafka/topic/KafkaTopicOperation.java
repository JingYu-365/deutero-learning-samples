package me.jkong.kafka.topic;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.KafkaFuture;
import org.junit.jupiter.api.*;

import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * Kafka Topic 相关操作
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/8/4 13:39.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class KafkaTopicOperation {

    private static AdminClient adminClient = null;
    private static final String TOPIC = "test-topic";

    @BeforeAll
    public static void init() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "10.10.32.17:9092");
        adminClient = AdminClient.create(props);
    }

    @AfterAll
    public static void cleanup() {
        adminClient.close();
    }


    @BeforeEach
    public void listTopicTest() throws ExecutionException, InterruptedException {
        ListTopicsResult topics = adminClient.listTopics();
        KafkaFuture<Set<String>> nameFuture = topics.names();
        Set<String> names = nameFuture.get();
        for (String name : names) {
            System.out.println(name);
        }
    }

    @Test
    @Order(1)
    public void deleteTopicTest() {
        adminClient.deleteTopics(Collections.singletonList("test_topic"));
    }

    @Test
    @Order(2)
    public void createTopicTest() {
        // partition 数量不可以超出 broker 数量
        NewTopic newTopic = new NewTopic(TOPIC, 1, (short) 1);
        adminClient.createTopics(Collections.singletonList(newTopic));
    }

    @Test
    @Order(3)
    public void descTopicTest() throws ExecutionException, InterruptedException {
        DescribeTopicsResult describeTopics = adminClient.describeTopics(Collections.singletonList(TOPIC));
        Map<String, KafkaFuture<TopicDescription>> topicMap = describeTopics.values();
        for (String topicName : topicMap.keySet()) {
            KafkaFuture<TopicDescription> descFuture = topicMap.get(topicName);
            TopicDescription topicDescription = descFuture.get();
            System.out.println(topicDescription.toString());
        }
    }
}