package org.software.code.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * MidInputDto 是一个数据传输对象（DTO），主要用于在不同层之间传递健康码管理人员的唯一标识（mid）。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class MidInputDto {

    /**
     * 健康码管理人员的唯一标识。
     * 该字段用于准确标识一个健康码管理人员，在进行 JSON 序列化和反序列化时，
     * 对应的 JSON 字段名为 "mid"。
     * 此属性是必需的，不允许为 null，若传入 null 值，会触发验证错误，
     * 错误提示信息为 "mid 不能为空"。
     */
    @NotNull(message = "mid 不能为空")
    @JsonProperty("mid")
    private Long mid;

}