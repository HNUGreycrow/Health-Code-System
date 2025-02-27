package org.software.code.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * TranscodingEventsDto 是一个数据传输对象（DTO），用于封装健康码转码事件相关的数据。
 * 借助 Lombok 的 @Data 注解自动生成 getter、setter、toString、equals 和 hashCode 等方法，
 * 并通过 @JsonProperty 注解指定 JSON 序列化和反序列化时的字段名称，
 * 使用 @NotNull 注解对关键属性进行非空校验，确保数据完整性。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class TranscodingEventsDto {

    /**
     * 用户的唯一标识，用于在系统中唯一确定要进行健康码转码操作的用户。
     * 在 JSON 序列化和反序列化时对应 "uid" 字段，该字段不能为空，否则会触发校验错误。
     */
    @NotNull
    @JsonProperty("uid")
    private Long uid;

    /**
     * 健康码转码事件的类型，用整数表示不同的转码事件，例如可能是接触风险人员、完成核酸检测等事件。
     * 在 JSON 序列化和反序列化时对应 "event" 字段，该字段不能为空，否则会触发校验错误。
     */
    @NotNull
    @JsonProperty("event")
    private Integer event;

    /**
     * 构造函数，用于创建 TranscodingEventsDto 对象。
     *
     * @param uid 用户的唯一标识
     * @param event 健康码转码事件的类型
     */
    public TranscodingEventsDto(Long uid, Integer event) {
        this.uid = uid;
        this.event = event;
    }
}