package org.software.code.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * CreateManageDto 是一个数据传输对象，用于封装创建管理员相关信息。
 * 当需要创建一个新的管理员账户时，使用该类来传递必要的信息，确保信息的完整性和有效性。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class CreateManageDto {

    /**
     * 管理员的身份证号码，此属性为必填项。
     * 在进行 JSON 序列化和反序列化时，该属性在 JSON 数据中对应的字段名为 "identity_card"。
     * 若该属性为空字符串或仅包含空白字符，会触发验证错误，提示 "identity_card 不能为空"。
     */
    @NotBlank(message = "identity_card不能为空")
    @JsonProperty("identity_card")
    private String identityCard;

    /**
     * 管理员的姓名，此属性为必填项。
     * 在进行 JSON 序列化和反序列化时，该属性在 JSON 数据中对应的字段名为 "name"。
     * 若该属性为空字符串或仅包含空白字符，会触发验证错误，提示 "name 不能为空"。
     */
    @NotBlank(message = "name不能为空")
    @JsonProperty("name")
    private String name;

    /**
     * 管理员的登录密码，此属性为必填项。
     * 在进行 JSON 序列化和反序列化时，该属性在 JSON 数据中对应的字段名为 "password"。
     * 若该属性为空字符串或仅包含空白字符，会触发验证错误，提示 "password 不能为空"。
     */
    @NotBlank(message = "password不能为空")
    @JsonProperty("password")
    private String password;
}