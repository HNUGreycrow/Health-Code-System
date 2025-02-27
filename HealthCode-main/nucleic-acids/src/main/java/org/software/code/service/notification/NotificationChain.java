package org.software.code.service.notification;

import org.software.code.dto.NotificationMessageDto;

import java.util.ArrayList;
import java.util.List;

/**
 * NotificationChain 是一个用于管理和执行通知处理链的类，
 * 它基于责任链模式设计，允许将多个通知处理程序组合在一起，
 * 依次对通知消息进行处理，以实现不同类型的通知发送逻辑。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
public class NotificationChain {
    /**
     * 存储通知处理程序的列表，每个处理程序都实现了 NotificationHandler 接口。
     */
    private List<NotificationHandler> handlers = new ArrayList<>();

    /**
     * 向通知处理链中添加一个通知处理程序。
     * 该方法支持链式调用，添加处理程序后返回当前 NotificationChain 实例。
     *
     * @param handler 要添加的通知处理程序
     * @return 当前 NotificationChain 实例，方便进行链式操作
     */
    public NotificationChain addHandler(NotificationHandler handler) {
        handlers.add(handler);
        return this;
    }

    /**
     * 执行通知处理链，依次调用处理链中每个处理程序的 handle 方法对通知消息进行处理。
     * 每个处理程序会根据自身的逻辑对消息进行相应的处理，如设置消息类型并发送到 Kafka 主题等。
     *
     * @param message 要处理的通知消息对象
     */
    public void execute(NotificationMessageDto message) {
        for (NotificationHandler handler : handlers) {
            handler.handle(message);
        }
    }
}