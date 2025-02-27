package org.software.code.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * UserInfoDto 是一个数据传输对象（DTO），用于封装用户的基本信息。
 * 在系统中，当需要在不同层（如前端与后端、服务层与数据访问层）之间传递用户信息时，
 * 可以使用该 DTO 来确保信息的完整性和一致性。该 DTO 包含了用户的唯一标识、姓名、
 * 电话号码、身份证号、所在地区编码、街道编码、社区编码以及详细地址等必要信息。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class UserInfoDto {

    /**
     * 用户的唯一标识。
     * 该字段用于在系统中唯一确定一个用户，在进行 JSON 序列化和反序列化时，
     * 对应的 JSON 字段名为 "uid"。此属性是必需的，不允许为 null，
     * 若传入 null 值，会触发验证错误，提示信息为 "uid 不能为空"。
     */
    @NotNull(message = "uid不能为空")
    @JsonProperty("uid")
    private Long uid;

    /**
     * 用户的姓名。
     * 该字段记录用户的真实姓名，在进行 JSON 序列化和反序列化时，
     * 对应的 JSON 字段名为 "name"。此属性不能为空字符串或仅包含空白字符，
     * 若违反该规则，会触发验证错误，提示信息为 "name 不能为空"。
     */
    @NotBlank(message = "name不能为空")
    @JsonProperty("name")
    private String name;

    /**
     * 用户的电话号码。
     * 该字段用于存储用户的联系电话，在进行 JSON 序列化和反序列化时，
     * 对应的 JSON 字段名为 "phone_number"。此属性不能为空字符串或仅包含空白字符，
     * 若违反该规则，会触发验证错误，提示信息为 "phone_number 不能为空"。
     */
    @NotBlank(message = "phone_number不能为空")
    @JsonProperty("phone_number")
    private String phoneNumber;

    /**
     * 用户的身份证号码。
     * 该字段用于唯一标识用户的身份信息，在进行 JSON 序列化和反序列化时，
     * 对应的 JSON 字段名为 "identity_card"。此属性不能为空字符串或仅包含空白字符，
     * 若违反该规则，会触发验证错误，提示信息为 "identity_card 不能为空"。
     */
    @NotBlank(message = "identity_card不能为空")
    @JsonProperty("identity_card")
    private String identityCard;

    /**
     * 用户所在地区的编码。
     * 该字段用于标识用户所在的地区，在进行 JSON 序列化和反序列化时，
     * 对应的 JSON 字段名为 "district"。此属性是必需的，不允许为 null，
     * 若传入 null 值，会触发验证错误，提示信息为 "district 不能为空"。
     */
    @NotNull(message = "district不能为空")
    @JsonProperty("district")
    private Integer district;

    /**
     * 用户所在街道的编码。
     * 该字段用于标识用户所在的街道，在进行 JSON 序列化和反序列化时，
     * 对应的 JSON 字段名为 "street"。此属性是必需的，不允许为 null，
     * 若传入 null 值，会触发验证错误，提示信息为 "street 不能为空"。
     */
    @NotNull(message = "street不能为空")
    @JsonProperty("street")
    private Integer street;

    /**
     * 用户所在社区的编码。
     * 该字段用于标识用户所在的社区，在进行 JSON 序列化和反序列化时，
     * 对应的 JSON 字段名为 "community"。此属性是必需的，不允许为 null，
     * 若传入 null 值，会触发验证错误，提示信息为 "community 不能为空"。
     */
    @NotNull(message = "community不能为空")
    @JsonProperty("community")
    private Long community;

    /**
     * 用户的详细地址。
     * 该字段记录用户的居住或工作地址，在进行 JSON 序列化和反序列化时，
     * 对应的 JSON 字段名为 "address"。此属性不能为空字符串或仅包含空白字符，
     * 若违反该规则，会触发验证错误，提示信息为 "address 不能为空"。
     */
    @NotBlank(message = "address不能为空")
    @JsonProperty("address")
    private String address;
}