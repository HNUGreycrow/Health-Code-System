package org.software.code.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 该类是一个数据传输对象（DTO），用于封装包含场所 ID 信息的令牌。
 * 它主要用于在不同层之间传递令牌数据，比如从控制器层传递到服务层。
 * 借助 Lombok 的 @Data 注解，自动生成了 getter、setter、toString、equals 和 hashCode 等方法。
 * 使用 @NotBlank 注解确保令牌不为空或仅包含空白字符，@JsonProperty 注解指定了 JSON 序列化和反序列化时的字段名称。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class PidTokenInputDto {

    /**
     * 包含场所 ID 信息的令牌，该字段不能为空白字符串。
     * 在 JSON 序列化和反序列化时，对应的字段名为 "token"。
     */
    @NotBlank(message = "token不能为空")
    @JsonProperty("token")
    private String token;
}