package org.software.code.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * TidInputDto 是一个数据传输对象（DTO），用于在不同层之间传递核酸检测用户的唯一标识（tid）。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class TidInputDto {

    /**
     * 核酸检测用户的唯一标识。
     * 该字段用于唯一确定一个核酸检测用户，在进行 JSON 序列化和反序列化时，
     * 对应的 JSON 字段名为 "tid"。
     * 此属性是必需的，不允许为 null，若传入 null 值，会触发验证错误，
     * 错误提示信息为 "tid 不能为空"。
     */
    @NotNull(message = "tid不能为空")
    @JsonProperty("tid")
    private Long tid;
}