package org.software.code.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * CreateNucleicAcidDto 是一个数据传输对象（DTO），用于封装创建核酸检测记录所需的基本信息。
 * 在进行核酸检测相关业务操作时，前端会将用户的必要信息封装成该 DTO 并发送到后端，后端使用该 DTO 接收和验证数据。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class CreateNucleicAcidDto {

    /**
     * 进行核酸检测的人员的身份证号码。
     * 该字段是创建核酸检测记录的必要信息，用于唯一标识检测人员。
     * 在进行 JSON 序列化和反序列化时，对应的 JSON 字段名为 "identity_card"。
     * 此属性不允许为空或仅包含空白字符，若违反该规则，会触发验证错误并提示 "identity_card不能为空"。
     */
    @NotBlank(message = "identity_card不能为空")
    @JsonProperty("identity_card")
    private String identityCard;

    /**
     * 进行核酸检测的人员的姓名。
     * 该字段用于明确核酸检测记录对应的人员身份，是创建核酸检测记录的必要信息。
     * 在进行 JSON 序列化和反序列化时，对应的 JSON 字段名为 "name"。
     * 此属性不允许为空或仅包含空白字符，若违反该规则，会触发验证错误并提示 "name不能为空"。
     */
    @NotBlank(message = "name不能为空")
    @JsonProperty("name")
    private String name;

    /**
     * 进行核酸检测的人员的登录密码。
     * 该字段可用于系统验证检测人员身份，确保操作的安全性，是创建核酸检测记录的必要信息。
     * 在进行 JSON 序列化和反序列化时，对应的 JSON 字段名为 "password"。
     * 此属性不允许为空或仅包含空白字符，若违反该规则，会触发验证错误并提示 "password不能为空"。
     */
    @NotBlank(message = "password不能为空")
    @JsonProperty("password")
    private String password;
}