package com.xinfago.rabbitmq;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xinfago
 */
@EnableRabbit
@SpringBootApplication
public class RabbitSample {

    public static void main(String[] args) {
        SpringApplication.run(RabbitSample.class, args);
    }

}
