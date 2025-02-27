package org.software.code.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * AddNucleicAcidTestRecordDto 是一个数据传输对象（DTO），用于封装添加核酸检测记录所需的数据。
 * 借助 Lombok 的 @Data 注解，自动生成 getter、setter、toString、equals 和 hashCode 等方法。
 * 使用 @JsonProperty 注解指定 JSON 序列化和反序列化时的字段名称。
 * 通过 @NotNull 注解对关键属性进行非空校验，确保数据的完整性。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class AddNucleicAcidTestRecordDto {

    /**
     * 用户的身份验证令牌，用于标识用户身份。
     * 在 JSON 序列化和反序列化时对应 "token" 字段。
     * 该字段不能为空，否则会触发校验错误。
     */
    @NotNull(message = "token不能为空")
    @JsonProperty("token")
    private String token;

    /**
     * 核酸检测的类型，例如单人单检、多人混检等。
     * 在 JSON 序列化和反序列化时对应 "kind" 字段。
     * 该字段不能为空，否则会触发校验错误。
     */
    @NotNull(message = "kind不能为空")
    @JsonProperty("kind")
    private Integer kind;

    /**
     * 核酸检测样本所在的管编号，用于追踪和管理样本。
     * 在 JSON 序列化和反序列化时对应 "tubeid" 字段。
     * 该字段不能为空，否则会触发校验错误。
     */
    @NotNull(message = "tubeid不能为空")
    @JsonProperty("tubeid")
    private Long tubeid;

    /**
     * 核酸检测的具体地址，记录检测的地点信息。
     * 在 JSON 序列化和反序列化时对应 "test_address" 字段。
     * 该字段不能为空，否则会触发校验错误。
     */
    @NotNull(message = "test_address不能为空")
    @JsonProperty("test_address")
    private String test_address;
}