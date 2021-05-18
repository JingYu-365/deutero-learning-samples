package com.xinfago.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import com.xinfago.common.entity.UserEntity;
import com.xinfago.rabbitmq.config.Constant;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author xinfago
 */
@Component
@RabbitListener(queues = Constant.QUEUE)
public class HelloConsumer {

    /**
     * 标注了 @RabbitHandler 注解的方法，在接收到消息时，会选择与消息体类型一致的方法进行解析
     * <p>
     * 比如：向同一个queue中发送了 String 类型和 UserEntity 类型的消息，那么在消费者接收到消息时，则选择合适的方法进行处理。
     * <p>
     * 并使用 isDefault 标识是否为默认处理方法（当找不到合适的处理方法时，则选择此方法）
     */
    @RabbitHandler(isDefault = true)
    public void service(Object message) {
        System.out.println("消息队列推送来的 default 消息：" + message);
    }

    @RabbitHandler
    public void service(String message) {
        System.out.println("消息队列推送来的 text 消息：" + message);
    }

//    @RabbitHandler
//    public void service(UserEntity entity) {
//        System.out.println("消息队列推送来的 object 消息：" + entity);
//    }

    @RabbitHandler
    public void service(Message message, UserEntity entity, Channel channel) {
        System.out.println("Message: " + message.toString());
        System.out.println("消息队列推送来的 object 消息：" + entity);
    }
}