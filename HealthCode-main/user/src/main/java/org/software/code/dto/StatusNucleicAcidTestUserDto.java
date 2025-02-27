package org.software.code.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * StatusNucleicAcidTestUserDto 是一个数据传输对象（DTO），用于封装修改核酸检测用户状态所需的信息。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class StatusNucleicAcidTestUserDto {

    /**
     * 核酸检测用户的唯一标识。
     * 该字段用于精确识别要修改状态的具体核酸检测用户，在 JSON 序列化和反序列化过程中，
     * 对应的 JSON 字段名为 "tid"。
     * 此属性为必填项，不允许为 null，若传入 null 值，会触发验证错误，
     * 错误提示信息为 "tid 不能为空"。
     */
    @NotNull(message = "tid不能为空")
    @JsonProperty("tid")
    private Long tid;

    /**
     * 核酸检测用户的状态。
     * 该布尔类型字段表示用户的状态，其含义可根据具体业务场景定义，例如 true 可表示启用状态，
     * false 可表示禁用状态等。在 JSON 序列化和反序列化时，对应的 JSON 字段名为 "status"。
     * 此属性是必需的，不允许为 null，若传入 null 值，会触发验证错误，
     * 错误提示信息为 "status 不能为空"。
     */
    @NotNull(message = "status不能为空")
    @JsonProperty("status")
    private Boolean status;
}