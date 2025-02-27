package org.software.code.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Uid输入的数据传输对象（DTO）
 * 用于封装包含用户唯一标识（uid）的输入信息
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class UidInputDto {

    /**
     * 用户唯一标识，不能为空。
     * 当该字段为空时，会抛出验证错误，错误信息为 "uid不能为空"。
     * 该字段在JSON数据中对应的属性名为 "uid"。
     */
    @NotNull(message = "uid不能为空")
    @JsonProperty("uid")
    private Long uid;

}