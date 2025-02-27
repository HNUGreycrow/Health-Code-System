package org.software.code.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * KafkaProducer 是一个 Spring 组件，用于向 Kafka 消息队列发送消息。
 * 它借助 Spring Kafka 提供的 KafkaTemplate 来完成消息的生产操作，
 * 封装了消息发送的逻辑，使得在其他业务代码中可以方便地调用该组件来发送消息到指定的 Kafka 主题。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Component
public class KafkaProducer {

    /**
     * 注入 KafkaTemplate 实例，该实例是 Spring Kafka 提供的核心工具，
     * 用于与 Kafka 集群进行交互。它允许将消息发送到指定的 Kafka 主题，
     * 这里的键和值类型都指定为 String，意味着消息的键和消息体都是字符串类型。
     */
    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 向指定的 Kafka 主题发送消息的方法。
     * 调用该方法时，需要传入目标主题的名称和要发送的消息内容，
     * 方法内部会使用注入的 KafkaTemplate 将消息发送到指定主题。
     *
     * @param topic  要发送消息的 Kafka 主题名称。Kafka 主题是消息的逻辑分类，
     *               不同的业务可以使用不同的主题来区分消息。
     * @param message 要发送的消息内容，这里要求消息是字符串类型。
     */
    public void sendMessage(String topic, String message) {
        // 调用 KafkaTemplate 的 send 方法将消息发送到指定主题
        kafkaTemplate.send(topic, message);
    }
}