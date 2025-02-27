package org.software.code.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * StatusManagerDto 是一个数据传输对象（DTO），用于在不同层之间传递修改健康码管理人员状态所需的信息。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class StatusManagerDto {

    /**
     * 健康码管理人员的唯一标识。
     * 该字段用于准确标识要修改状态的健康码管理人员，在进行 JSON 序列化和反序列化时，
     * 对应的 JSON 字段名为 "mid"。
     * 此属性是必需的，不允许为 null，若传入 null 值，会触发验证错误，
     * 错误提示信息为 "mid 不能为空"。
     */
    @NotNull(message = "mid不能为空")
    @JsonProperty("mid")
    private Long mid;

    /**
     * 健康码管理人员的登录状态。
     * false 表示不可登录，true 表示可以登录。该字段用于指定要将管理人员的状态修改为何种状态，
     * 在进行 JSON 序列化和反序列化时，对应的 JSON 字段名为 "status"。
     * 此属性是必需的，不允许为 null，若传入 null 值，会触发验证错误，
     * 错误提示信息为 "status 不能为空"。
     */
    @NotNull(message = "status不能为空")
    @JsonProperty("status")
    private Boolean status;
}