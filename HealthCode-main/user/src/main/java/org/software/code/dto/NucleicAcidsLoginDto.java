package org.software.code.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * NucleicAcidsLoginDto 是一个数据传输对象（DTO），用于封装核酸检测相关人员登录系统时所需的信息。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class NucleicAcidsLoginDto {

    /**
     * 核酸检测相关人员的身份证号码，用于唯一标识该人员。
     * 该字段是登录操作的必要信息，在进行 JSON 序列化和反序列化时，
     * 对应的 JSON 字段名为 "identity_card"。
     * 此属性不允许为空字符串或仅包含空白字符，若违反该规则，会触发验证错误，
     * 提示信息为 "identity_card 不能为空"。
     */
    @NotBlank(message = "identity_card不能为空")
    @JsonProperty("identity_card")
    private String identityCard;

    /**
     * 核酸检测相关人员登录系统使用的密码，用于验证其身份的合法性。
     * 该字段是登录操作的必要信息，在进行 JSON 序列化和反序列化时，
     * 对应的 JSON 字段名为 "password"。
     * 此属性不允许为空字符串或仅包含空白字符，若违反该规则，会触发验证错误，
     * 提示信息为 "password 不能为空"。
     */
    @NotBlank(message = "password不能为空")
    @JsonProperty("password")
    private String password;

}