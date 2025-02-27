package org.software.code.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * UserModifyDto 是一个数据传输对象（DTO），用于封装修改用户信息时所需的相关数据。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class UserModifyDto {

    /**
     * 用户的姓名，用于修改用户的姓名信息。
     * 在进行 JSON 序列化和反序列化时，对应的 JSON 字段名为 "name"。
     * 该字段不允许为空字符串或仅包含空白字符，若违反此规则，会触发验证错误，
     * 错误提示信息为 "name 不能为空"。
     */
    @NotBlank(message = "name不能为空")
    @JsonProperty("name")
    private String name;

    /**
     * 用户的电话号码，用于修改用户的联系电话信息。
     * 在进行 JSON 序列化和反序列化时，对应的 JSON 字段名为 "phone_number"。
     * 该字段不允许为空字符串或仅包含空白字符，若违反此规则，会触发验证错误，
     * 错误提示信息为 "phone_number 不能为空"。
     */
    @NotBlank(message = "phone_number不能为空")
    @JsonProperty("phone_number")
    private String phoneNumber;

    /**
     * 用户所在地区的 ID，用于修改用户所在的地区信息。
     * 在进行 JSON 序列化和反序列化时，对应的 JSON 字段名为 "district_id"。
     * 该字段不允许为 null，若传入 null 值，会触发验证错误，
     * 错误提示信息为 "district_id 不能为空"。
     */
    @NotNull(message = "district_id不能为空")
    @JsonProperty("district_id")
    private Integer districtId;

    /**
     * 用户所在街道的 ID，用于修改用户所在的街道信息。
     * 在进行 JSON 序列化和反序列化时，对应的 JSON 字段名为 "street_id"。
     * 该字段不允许为 null，若传入 null 值，会触发验证错误，
     * 错误提示信息为 "street_id 不能为空"。
     */
    @NotNull(message = "street_id不能为空")
    @JsonProperty("street_id")
    private Integer streetId;

    /**
     * 用户所在社区的 ID，用于修改用户所在的社区信息。
     * 在进行 JSON 序列化和反序列化时，对应的 JSON 字段名为 "community_id"。
     * 该字段不允许为 null，若传入 null 值，会触发验证错误，
     * 错误提示信息为 "community_id 不能为空"。
     */
    @NotNull(message = "community_id不能为空")
    @JsonProperty("community_id")
    private Long communityId;

    /**
     * 用户的地址，用于修改用户的居住或工作地址信息。
     * 在进行 JSON 序列化和反序列化时，对应的 JSON 字段名为 "address"。
     * 该字段不允许为空字符串或仅包含空白字符，若违反此规则，会触发验证错误，
     * 错误提示信息为 "address 不能为空"。
     */
    @NotBlank(message = "address不能为空")
    @JsonProperty("address")
    private String address;
}