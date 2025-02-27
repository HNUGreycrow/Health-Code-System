package org.software.code.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * AddNucleicAcidTestRecordByIDDto 是一个数据传输对象（DTO），
 * 用于封装通过身份证号添加核酸检测记录时所需的数据。
 * 借助 Lombok 的 @Data 注解自动生成 getter、setter、toString、equals 和 hashCode 等方法，
 * 利用 @JsonProperty 注解指定 JSON 序列化和反序列化时的字段名称，
 * 并使用 @NotNull 和 @NotBlank 注解对关键属性进行非空校验。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class AddNucleicAcidTestRecordByIDDto {

    /**
     * 被检测人员的身份证号，在 JSON 序列化和反序列化时对应 "identity_card" 字段。
     * 该字段不能为空字符串，否则会触发校验错误。
     */
    @NotBlank(message = "identity_card不能为空")
    @JsonProperty("identity_card")
    private String identity_card;

    /**
     * 核酸检测的类型，在 JSON 序列化和反序列化时对应 "kind" 字段。
     * 该字段不能为空，否则会触发校验错误。
     */
    @NotNull(message = "kind不能为空")
    @JsonProperty("kind")
    private Integer kind;

    /**
     * 核酸检测的管编号，在 JSON 序列化和反序列化时对应 "tubeid" 字段。
     * 该字段不能为空，否则会触发校验错误。
     */
    @NotNull(message = "tubeid不能为空")
    @JsonProperty("tubeid")
    private Long tubeid;

    /**
     * 核酸检测的地址，在 JSON 序列化和反序列化时对应 "test_address" 字段。
     * 该字段不能为空字符串，否则会触发校验错误。
     */
    @NotBlank(message = "test_address不能为空")
    @JsonProperty("test_address")
    private String test_address;
}