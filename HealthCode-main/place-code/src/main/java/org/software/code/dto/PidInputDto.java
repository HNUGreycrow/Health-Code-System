package org.software.code.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 场所 ID 输入的数据传输对象（DTO），用于封装请求中的场所 ID 信息，
 * 方便在不同层之间传递该信息，确保数据的完整性和准确性。
 * 使用 Lombok 的 @Data 注解自动生成 getter、setter 等方法，
 * 通过 @NotNull 注解确保场所 ID 不为空，@JsonProperty 指定 JSON 序列化和反序列化时的字段名。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class PidInputDto {

    /**
     * 场所的唯一标识 ID，该字段不能为 null。
     * 在 JSON 序列化和反序列化时，对应的字段名为 "pid"。
     */
    @NotNull(message = "pid不能为空")
    @JsonProperty("pid")
    private Long pid;
}