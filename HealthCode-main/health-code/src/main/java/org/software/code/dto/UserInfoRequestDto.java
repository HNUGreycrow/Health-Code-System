package org.software.code.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 用户信息请求的数据传输对象（DTO）
 * 用于封装客户端向服务端发送的用户信息请求数据。
 * 该 DTO 类使用了 Lombok 注解，提供了全参构造函数、无参构造函数、Builder 模式以及 Getter 和 Setter 方法。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoRequestDto {

    /**
     * 用户唯一标识，不能为空。
     * 当该字段为空时，会抛出验证错误，错误信息为 "uid不能为空"。
     * 该字段在 JSON 数据中对应的属性名为 "uid"。
     */
    @NotNull(message = "uid不能为空")
    @JsonProperty("uid")
    private Long uid;

    /**
     * 用户姓名，不能为空。
     * 当该字段为空字符串或 null 时，会抛出验证错误，错误信息为 "name不能为空"。
     * 该字段在 JSON 数据中对应的属性名为 "name"。
     */
    @NotBlank(message = "name不能为空")
    @JsonProperty("name")
    private String name;

    /**
     * 用户手机号码，不能为空。
     * 当该字段为空字符串或 null 时，会抛出验证错误，错误信息为 "phone_number不能为空"。
     * 该字段在 JSON 数据中对应的属性名为 "phone_number"。
     */
    @NotBlank(message = "phone_number不能为空")
    @JsonProperty("phone_number")
    private String phoneNumber;

    /**
     * 用户身份证号码，不能为空。
     * 当该字段为空字符串或 null 时，会抛出验证错误，错误信息为 "identity_card不能为空"。
     * 该字段在 JSON 数据中对应的属性名为 "identity_card"。
     */
    @NotBlank(message = "identity_card不能为空")
    @JsonProperty("identity_card")
    private String identityCard;

    /**
     * 用户所在地区的编码，不能为空。
     * 当该字段为空时，会抛出验证错误，错误信息为 "district不能为空"。
     * 该字段在 JSON 数据中对应的属性名为 "district"。
     */
    @NotNull(message = "district不能为空")
    @JsonProperty("district")
    private Integer district;

    /**
     * 用户所在街道的编码，不能为空。
     * 当该字段为空时，会抛出验证错误，错误信息为 "street不能为空"。
     * 该字段在 JSON 数据中对应的属性名为 "street"。
     */
    @NotNull(message = "street不能为空")
    @JsonProperty("street")
    private Integer street;

    /**
     * 用户所在社区的编码，不能为空。
     * 当该字段为空时，会抛出验证错误，错误信息为 "community不能为空"。
     * 该字段在 JSON 数据中对应的属性名为 "community"。
     */
    @NotNull(message = "community不能为空")
    @JsonProperty("community")
    private Long community;

    /**
     * 用户详细地址，不能为空。
     * 当该字段为空字符串或 null 时，会抛出验证错误，错误信息为 "address不能为空"。
     * 该字段在 JSON 数据中对应的属性名为 "address"。
     */
    @NotBlank(message = "address不能为空")
    @JsonProperty("address")
    private String address;
}