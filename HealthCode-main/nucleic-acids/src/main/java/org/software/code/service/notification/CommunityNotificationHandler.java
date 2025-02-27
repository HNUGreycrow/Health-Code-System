package org.software.code.service.notification;

import org.software.code.dto.NotificationMessageDto;
import org.software.code.kafaka.NotificationProducer;
import org.springframework.stereotype.Component;

/**
 * CommunityNotificationHandler 是一个用于处理向社区上报通知消息的处理器类。
 * 它实现了 NotificationHandler 接口，专门负责将特定类型的通知消息发送到 Kafka 主题。
 * 该类被 Spring 框架识别为一个组件，会被自动扫描并注册到应用的上下文当中。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Component
public class CommunityNotificationHandler implements NotificationHandler {

    /**
     * 注入 NotificationProducer，用于将通知消息发送到 Kafka 主题。
     */
    private final NotificationProducer producer;

    /**
     * 构造函数，通过依赖注入的方式接收 NotificationProducer 实例。
     *
     * @param producer 用于发送通知消息的 Kafka 生产者实例
     */
    public CommunityNotificationHandler(NotificationProducer producer) {
        this.producer = producer;
    }

    /**
     * 处理通知消息的方法，将消息类型设置为 "COMMUNITY"，并使用 NotificationProducer
     * 将消息发送到名为 "notification-topic" 的 Kafka 主题。
     *
     * @param message 包含通知信息的消息对象
     */
    @Override
    public void handle(NotificationMessageDto message) {
        // 将消息类型设置为 "COMMUNITY"，表示这是一个向社区上报的通知消息
        message.setType("COMMUNITY");
        // 调用 NotificationProducer 的 sendNotification 方法，将消息发送到指定的 Kafka 主题
        producer.sendNotification("notification-topic", message);
    }
}