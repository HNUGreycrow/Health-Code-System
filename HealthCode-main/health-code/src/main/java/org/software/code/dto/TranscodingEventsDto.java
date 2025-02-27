package org.software.code.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 转码事件的数据传输对象（DTO）
 * 用于封装与转码事件相关的信息
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class TranscodingEventsDto {

    /**
     * 用户唯一标识，不能为空
     * 该字段在JSON数据中对应的属性名为 "uid"
     */
    @NotNull
    @JsonProperty("uid")
    private Long uid;

    /**
     * 转码事件的类型标识，不能为空
     * 该字段在JSON数据中对应的属性名为 "event"
     */
    @NotNull
    @JsonProperty("event")
    private Integer event;
}