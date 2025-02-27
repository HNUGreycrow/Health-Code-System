package org.software.code.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * ScanPlaceCodeRequestDto 是一个数据传输对象（DTO），用于封装扫描场所码请求所需的信息。
 * 该类使用 Lombok 的 @Data 注解自动生成 getter、setter、toString、equals 和 hashCode 等方法，
 * 并通过 @JsonProperty 注解指定 JSON 序列化和反序列化时的字段名称，同时使用验证注解确保数据的有效性。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class ScanPlaceCodeRequestDto {

    /**
     * 用户的唯一标识，在扫描场所码操作中用于识别扫描场所码的用户。
     * 该字段不能为 null，在 JSON 序列化和反序列化时对应的字段名为 "uid"。
     */
    @NotNull(message = "uid不能为空")
    @JsonProperty("uid")
    private Long uid;

    /**
     * 包含场所信息的令牌，通常用于验证场所的合法性和获取场所相关信息。
     * 该字段不能为空白字符串，在 JSON 序列化和反序列化时对应的字段名为 "token"。
     */
    @NotBlank(message = "token不能为空")
    @JsonProperty("token")
    private String token;
}