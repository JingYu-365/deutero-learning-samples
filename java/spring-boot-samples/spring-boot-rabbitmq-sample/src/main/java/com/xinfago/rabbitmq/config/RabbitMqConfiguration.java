package com.xinfago.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbitmq 配置项
 *
 * @author xinfago
 */
@Configuration
public class RabbitMqConfiguration {

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

//    @Bean
//    public RabbitTemplate rabbitTemplate() {
//        return new RabbitTemplate();
//    }

}
