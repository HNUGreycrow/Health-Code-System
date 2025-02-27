package org.software.code.dto;

import lombok.Data;

/**
 * NotificationMessageDto 是一个数据传输对象（DTO），用于封装通知消息相关的数据。
 * 该类借助 Lombok 的 @Data 注解，自动生成了 getter、setter、toString、equals 和 hashCode 等方法，
 * 方便在不同层之间传输和操作通知消息信息。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class NotificationMessageDto {
    /**
     * 通知消息所关联的人员姓名，可用于明确通知的对象。
     */
    private String name;

    /**
     * 通知消息所关联的人员电话号码，便于通过短信或电话等方式发送通知。
     */
    private String phone;

    /**
     * 通知消息所关联的人员身份证号码，作为人员身份的重要标识。
     */
    private String identity_card;

    /**
     * 通知消息的类型，例如可能是健康码异常通知、核酸检测结果通知等，用于区分不同类型的通知。
     */
    private String type;
}