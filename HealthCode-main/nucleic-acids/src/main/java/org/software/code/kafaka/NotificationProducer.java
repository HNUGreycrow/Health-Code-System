package org.software.code.kafaka; // 这里包名可能拼写有误，应该是 "kafka"

import com.fasterxml.jackson.databind.ObjectMapper;
import org.software.code.dto.NotificationMessageDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * NotificationProducer 是一个 Kafka 生产者服务类，
 * 主要用于将通知消息发送到指定的 Kafka 主题。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Service
public class NotificationProducer {

    /**
     * 注入 KafkaTemplate，用于与 Kafka 进行交互，发送消息到指定主题。
     * 这里使用的 KafkaTemplate 泛型为 <String, String>，表示消息的键和值都为字符串类型。
     */
    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * ObjectMapper 用于将 Java 对象序列化为 JSON 字符串，
     * 以便将 NotificationMessageDto 对象转换为适合在 Kafka 中传输的格式。
     */
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 向指定的 Kafka 主题发送通知消息。
     *
     * @param topic 要发送消息的 Kafka 主题名称。
     * @param message 包含通知信息的 NotificationMessageDto 对象。
     */
    public void sendNotification(String topic, NotificationMessageDto message) {
        try {
            // 将 NotificationMessageDto 对象序列化为 JSON 字符串
            String messageStr = objectMapper.writeValueAsString(message);
            // 使用 KafkaTemplate 将消息字符串发送到指定的 Kafka 主题
            kafkaTemplate.send(topic, messageStr);
        } catch (Exception e) {
            // 若在序列化或发送消息过程中出现异常，打印异常堆栈信息
            e.printStackTrace();
        }
    }
}