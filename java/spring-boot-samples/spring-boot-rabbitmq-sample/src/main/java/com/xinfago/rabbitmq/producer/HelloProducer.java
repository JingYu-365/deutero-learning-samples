package com.xinfago.rabbitmq.producer;

import com.xinfago.common.entity.UserEntity;
import com.xinfago.rabbitmq.config.Constant;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Random;


/**
 * @author xinfago
 */
@RestController
public class HelloProducer {

    @Resource
    private AmqpTemplate rabbitTemplate;

    @GetMapping("/send/text")
    public String sendMessage(@RequestParam("message") String message) {
        // param1: exchange  param2:routingKey
        rabbitTemplate.convertAndSend(Constant.EXCHANGE, Constant.ROUTING_KEY, message);
        return "ok";
    }

    @GetMapping("/send/object")
    public String sendMessage() {
        UserEntity entity = new UserEntity().setId(new Random().nextLong())
                .setNickName("xinfago")
                .setUserName("xinfa")
                .setPassword("123456")
                .setCreateTime(System.currentTimeMillis());
        rabbitTemplate.convertAndSend(Constant.EXCHANGE, Constant.ROUTING_KEY, entity);
        return "ok";
    }
}