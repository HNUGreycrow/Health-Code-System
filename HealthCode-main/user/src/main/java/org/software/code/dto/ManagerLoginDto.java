package org.software.code.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * ManagerLoginDto 是一个数据传输对象，用于封装管理员登录所需的信息。
 * 在管理员进行登录操作时，前端会将管理员的身份证号和密码封装成该对象发送到后端，
 * 后端使用该对象接收并验证管理员的登录信息。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class ManagerLoginDto {

    /**
     * 管理员的身份证号码，用于唯一标识管理员身份。
     * 该字段是管理员登录的必要信息，在进行 JSON 序列化和反序列化时，
     * 对应的 JSON 字段名为 "identity_card"。
     * 此属性不允许为空或仅包含空白字符，若违反该规则，会触发验证错误，
     * 提示信息为 "identity_card不能为空"。
     */
    @NotBlank(message = "identity_card不能为空")
    @JsonProperty("identity_card")
    private String identityCard;

    /**
     * 管理员登录使用的密码，用于验证管理员身份的合法性。
     * 该字段是管理员登录的必要信息，在进行 JSON 序列化和反序列化时，
     * 对应的 JSON 字段名为 "password"。
     * 此属性不允许为空或仅包含空白字符，若违反该规则，会触发验证错误，
     * 提示信息为 "password不能为空"。
     */
    @NotBlank(message = "password不能为空")
    @JsonProperty("password")
    private String password;
}