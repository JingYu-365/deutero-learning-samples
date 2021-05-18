package com.xinfago.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * rabbitmq 配置项
 *
 * @author xinfago
 */
@Configuration
public class RabbitMqConfiguration {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Bean
    public Queue myQueue() {
        return new Queue(Constant.QUEUE);
    }

    @Bean
    public Exchange myExchange() {
        // new Exchange()
        // return new TopicExchange("topic.biz.ex", false, false, null);
        // return new DirectExchange("direct.biz.ex", false, false, null);
        // return new FanoutExchange("fanout.biz.ex", false, false, null);
        // return new HeadersExchange("header.biz.ex", false, false, null); // 交换器名称，交换器类型（），是否是持久化的，是否自动删除，交换器属性 Map集合
        // return new CustomExchange("custom.biz.ex", ExchangeTypes.DIRECT, false, false, null);
        return new DirectExchange(Constant.EXCHANGE, false, false, null);
    }

    @Bean
    public Binding myBinding() {
        // 绑定的目的地，绑定的类型：到交换器还是到队列，交换器名称，路由key， 绑定的属性
        // new Binding("", Binding.DestinationType.EXCHANGE, "", "", null);
        // 绑定的目的地，绑定的类型：到交换器还是到队列，交换器名称，路由key， 绑定的属性
        // new Binding("", Binding.DestinationType.QUEUE, "", "", null);
        // 绑定了交换器direct.biz.ex到队列myqueue，路由key是 direct.biz.ex
        return new Binding(Constant.QUEUE, Binding.DestinationType.QUEUE, Constant.EXCHANGE, Constant.ROUTING_KEY, null);
    }

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    @PostConstruct
    public void initRabbitTemplate() {
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {

            /**
             * 设置 消息发送到 Broker 时，调用的回调函数
             * @param correlationData 当前消息的全局唯一标识
             * @param ack true：消息成功收到，false：消息投送失败
             * @param cause 失败的原因
             */
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println(correlationData);
                System.out.println(ack);
                System.out.println(cause);
            }
        });

        // 设置 消息被Exchange发送到 Queue 后，调用的回调函数
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            public void returnedMessage(ReturnedMessage returned) {
                System.out.println("========= returnedMessage-1 ========");
                System.out.println(returned);
            }

            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println("========= returnedMessage-2 ========");
                System.out.println(message);
                System.out.println(replyCode);
                System.out.println(replyText);
                System.out.println(exchange);
                System.out.println(routingKey);
            }
        });
    }

}
