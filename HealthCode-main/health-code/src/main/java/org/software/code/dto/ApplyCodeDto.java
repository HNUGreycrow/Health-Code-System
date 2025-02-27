package org.software.code.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 申请健康码的数据传输对象（DTO）
 * 用于封装申请健康码时所需的用户信息
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class ApplyCodeDto {

    /**
     * 用户姓名，不能为空
     * 该字段在JSON数据中对应的属性名为 "name"
     */
    @NotBlank
    @JsonProperty("name")
    private String name;

    /**
     * 用户手机号码，不能为空
     * 该字段在JSON数据中对应的属性名为 "phone_number"
     */
    @NotBlank
    @JsonProperty("phone_number")
    private String phoneNumber;

    /**
     * 用户身份证号码，不能为空
     * 该字段在JSON数据中对应的属性名为 "identity_card"
     */
    @NotBlank
    @JsonProperty("identity_card")
    private String identityCard;

    /**
     * 用户所在地区的ID，不能为空
     * 该字段在JSON数据中对应的属性名为 "district_id"
     */
    @NotNull
    @JsonProperty("district_id")
    private Integer district;

    /**
     * 用户所在街道的ID，不能为空
     * 该字段在JSON数据中对应的属性名为 "street_id"
     */
    @NotNull
    @JsonProperty("street_id")
    private Integer street;

    /**
     * 用户所在社区的ID，不能为空
     * 该字段在JSON数据中对应的属性名为 "community_id"
     */
    @NotNull
    @JsonProperty("community_id")
    private Long community;

    /**
     * 用户详细地址，不能为空
     * 该字段在JSON数据中对应的属性名为 "address"
     */
    @NotBlank
    @JsonProperty("address")
    private String address;
}