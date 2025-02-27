package org.software.code.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 该类是用于反转场所码状态请求的数据传输对象（DTO），
 * 主要用于封装反转场所码状态所需的场所 ID 和目标状态信息，
 * 并在不同层之间传递这些信息，比如从控制器层传递到服务层。
 * 借助 Lombok 的 @Data 注解，自动生成了 getter、setter、toString、equals 和 hashCode 等方法。
 * 同时使用 @NotNull 注解对各字段进行非空校验，确保请求数据的完整性。
 * 通过 @JsonProperty 注解指定 JSON 序列化和反序列化时的字段名称。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class OppositePlaceCodeRequestDto {

    /**
     * 场所的唯一标识 ID，用于指定要反转状态的具体场所。
     * 该字段不能为 null，在 JSON 序列化和反序列化时对应的名称为 "pid"。
     */
    @NotNull(message = "pid不能为空")
    @JsonProperty("pid")
    private Long pid;

    /**
     * 场所码要反转到的目标状态，使用布尔值表示。
     * 该字段不能为 null，在 JSON 序列化和反序列化时对应的名称为 "status"。
     */
    @NotNull(message = "status不能为空")
    @JsonProperty("status")
    private Boolean status;
}