package org.software.code.service.notification;

import org.software.code.dto.NotificationMessageDto;

/**
 * NotificationHandler 是一个通知处理程序的接口，它定义了处理通知消息的统一行为。
 * 不同类型的通知处理逻辑可以通过实现该接口来完成，例如向社区上报通知、向疫情防控部门上报通知等。
 * 这样设计的好处是遵循了面向接口编程的原则，提高了代码的可扩展性和可维护性。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
public interface NotificationHandler {
    /**
     * 处理通知消息的方法，所有实现该接口的类都需要实现此方法，
     * 以定义具体的通知处理逻辑，比如设置消息类型、将消息发送到特定的 Kafka 主题等。
     *
     * @param message 包含通知信息的消息对象，其具体信息封装在 NotificationMessageDto 中
     */
    void handle(NotificationMessageDto message);
}