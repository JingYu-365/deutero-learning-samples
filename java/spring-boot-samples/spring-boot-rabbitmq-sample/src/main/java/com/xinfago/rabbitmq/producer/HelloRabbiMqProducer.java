package com.xinfago.rabbitmq.producer;

import com.xinfago.rabbitmq.config.Constant;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * @author xinfago
 */
@RestController
public class HelloRabbiMqProducer {

    @Resource
    private AmqpTemplate rabbitTemplate;

    @RequestMapping("/send/{message}")
    public String sendMessage(@PathVariable String message) {
        // param1: exchange  param2:routingKey
        rabbitTemplate.convertAndSend(Constant.EXCHANGE, Constant.ROUTING_KEY, message);
        return "ok";
    }
}