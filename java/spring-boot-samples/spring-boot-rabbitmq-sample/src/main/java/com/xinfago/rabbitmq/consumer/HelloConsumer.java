package com.xinfago.rabbitmq.consumer;

import com.xinfago.rabbitmq.config.Constant;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author xinfago
 */
@Component
public class HelloConsumer {

    @RabbitListener(queues = Constant.QUEUE)
    public void service(String message) {
        System.out.println("消息队列推送来的消息：" + message);
    }
}