package org.software.code.kafaka; // 注意：这里包名可能拼写错误，应该是 "kafka"

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.software.code.common.except.BusinessException;
import org.software.code.common.except.ExceptionEnum;
import org.software.code.dto.NotificationMessageDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * NotificationConsumer 是一个 Kafka 消费者服务类，用于从 Kafka 主题中消费通知消息，
 * 并根据消息类型执行相应的通知操作。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Service
public class NotificationConsumer {
    // 用于 JSON 数据的序列化和反序列化
    private final ObjectMapper objectMapper = new ObjectMapper();
    // 日志记录器，用于记录关键信息和错误信息
    private static final Logger logger = LogManager.getLogger(NotificationConsumer.class);

    /**
     * 监听 Kafka 主题 "notification-topic" 中的消息，处理接收到的通知消息。
     *
     * @param messageStr 从 Kafka 主题接收到的消息字符串
     */
    @KafkaListener(topics = "notification-topic", groupId = "notification-group")
    public void consumeNotification(String messageStr) {
        try {
            // 将接收到的 JSON 字符串消息反序列化为 NotificationMessageDto 对象
            NotificationMessageDto message = objectMapper.readValue(messageStr, NotificationMessageDto.class);

            // 根据消息类型执行相应的通知操作
            switch (message.getType()) {
                case "SMS":
                    // 若消息类型为 "SMS"，则调用发送短信通知的方法
                    sendSmsNotification(message);
                    break;
                case "COMMUNITY":
                    // 若消息类型为 "COMMUNITY"，则调用上报社区的方法
                    reportToCommunity(message);
                    break;
                case "EPIDEMIC":
                    // 若消息类型为 "EPIDEMIC"，则调用上报疫情防控办的方法
                    reportToEpidemicPrevention(message);
                    break;
                case "POSITIVE":
                    // 若消息类型为 "POSITIVE"，则调用处理单管阳性情况的方法
                    reportPositive(message);
                    break;
                default:
                    // 若消息类型不匹配任何已知类型，则不做处理
                    break;
            }
        } catch (Exception e) {
            // 打印异常堆栈信息
            e.printStackTrace();
            // 抛出业务异常，异常信息由 ExceptionEnum.RETEST_NOTIFICATION_EXCEPTION 定义
            throw new BusinessException(ExceptionEnum.RETEST_NOTIFICATION_EXCEPTION);
        }
    }

    /**
     * 发送短信通知的方法，目前仅记录日志，需要实现具体的短信发送逻辑。
     *
     * @param message 包含通知信息的消息对象
     */
    private void sendSmsNotification(NotificationMessageDto message) {
        // 记录发送短信通知的日志信息
        logger.info("Sending SMS notification to user: Name={}, Identity Card={}, Phone={}", message.getName(), message.getIdentity_card(), message.getPhone());
        // TODO 发送短信通知的逻辑
    }

    /**
     * 上报社区的方法，目前仅记录日志，需要实现具体的上报逻辑。
     *
     * @param message 包含通知信息的消息对象
     */
    private void reportToCommunity(NotificationMessageDto message) {
        // 记录上报社区的日志信息
        logger.info("Reporting to community for user: Name={}, Identity Card={}, Phone={}", message.getName(), message.getIdentity_card(), message.getPhone());
        // TODO 上报社区的逻辑
    }

    /**
     * 上报疫情防控办的方法，目前仅记录日志，需要实现具体的上报逻辑。
     *
     * @param message 包含通知信息的消息对象
     */
    private void reportToEpidemicPrevention(NotificationMessageDto message) {
        // 记录上报疫情防控办的日志信息
        logger.info("Reporting to epidemic prevention office for user: Name={}, Identity Card={}, Phone={}", message.getName(), message.getIdentity_card(), message.getPhone());
        // TODO 上报疫情防控办的逻辑
    }

    /**
     * 处理单管阳性情况的方法，目前仅记录日志，需要实现具体的处理逻辑。
     *
     * @param message 包含通知信息的消息对象
     */
    private void reportPositive(NotificationMessageDto message) {
        // 记录单管阳性情况的日志信息
        logger.info("single tubei positive: Name={}, Identity Card={}, Phone={}", message.getName(), message.getIdentity_card(), message.getPhone());
        // TODO 单管阳性
    }
}