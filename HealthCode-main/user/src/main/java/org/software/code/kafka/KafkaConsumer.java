package org.software.code.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * KafkaConsumer 是一个 Spring 组件，用于消费 Kafka 消息。
 * 该类使用 Spring Kafka 提供的功能，监听指定 Kafka 主题的消息，并对其进行相应处理。
 * 同时，该类还注入了 KafkaTemplate，可用于后续可能的消息生产操作。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Component
public class KafkaConsumer {

    /**
     * 注入 KafkaTemplate 实例，用于与 Kafka 集群进行交互，
     * 可以使用该实例向 Kafka 主题发送消息，但在当前类中未使用该功能，
     * 可能在后续的扩展中会使用它来实现消息的生产。
     */
    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Kafka 消息监听器方法，用于监听名为 "test" 的 Kafka 主题，
     * 并使用 "my-group" 作为消费者组 ID。
     * 当监听到该主题有新消息时，Spring Kafka 会自动调用此方法进行处理。
     *
     * @param record 从 Kafka 主题接收到的消息记录，包含消息的键、值等信息。
     *               消息的键和值类型均为 String。
     */
    @KafkaListener(topics = "test", groupId = "my-group")
    public void listen(ConsumerRecord<String, String> record) {
        // 打印接收到的消息的值
        System.out.println("Received message: " + record.value());
    }
}