package org.software.code.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * NucleicAcidTestingInstitutionsVo 是一个视图对象（VO），用于封装核酸检测机构的相关信息。
 * 该类主要用于在业务逻辑层和视图层之间传递数据，方便前端展示核酸检测机构的详细信息。
 * 使用 Lombok 的 @Data 注解自动生成 getter、setter、toString、equals 和 hashCode 等方法。
 * 通过 @JsonProperty 注解指定 JSON 序列化和反序列化时的字段名称，
 * 并使用 @NotNull 和 @NotBlank 注解对关键属性进行非空校验。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class NucleicAcidTestingInstitutionsVo {
    /**
     * 核酸检测机构的唯一标识 ID，在 JSON 序列化和反序列化时对应 "id" 字段。
     * 该字段不能为空，否则会触发校验错误。
     */
    @NotNull(message = "id不能为空")
    @JsonProperty("id")
    private Integer id;

    /**
     * 核酸检测机构的名称，在 JSON 序列化和反序列化时对应 "name" 字段。
     * 该字段不能为空，否则会触发校验错误。
     */
    @NotNull(message = "name不能为空")
    @JsonProperty("name")
    private String name;

    /**
     * 核酸检测机构所在的区域编号，在 JSON 序列化和反序列化时对应 "district" 字段。
     * 该字段不能为空，否则会触发校验错误。
     */
    @NotNull(message = "district不能为空")
    @JsonProperty("district")
    private Integer district;

    /**
     * 核酸检测机构所在的街道编号，在 JSON 序列化和反序列化时对应 "street" 字段。
     * 该字段不能为空，否则会触发校验错误。
     */
    @NotNull(message = "street不能为空")
    @JsonProperty("street")
    private Integer street;

    /**
     * 核酸检测机构所在的社区编号，在 JSON 序列化和反序列化时对应 "community" 字段。
     * 该字段不能为空，否则会触发校验错误。
     */
    @NotNull(message = "community不能为空")
    @JsonProperty("community")
    private Long community;

    /**
     * 核酸检测机构的详细地址，在 JSON 序列化和反序列化时对应 "address" 字段。
     * 该字段不能为空字符串，否则会触发校验错误。
     */
    @NotBlank(message = "address不能为空")
    @JsonProperty("address")
    private String address;

    /**
     * 核酸检测机构的检测时间，在 JSON 序列化和反序列化时对应 "testTime" 字段。
     * 该字段不能为空字符串，否则会触发校验错误。
     */
    @NotBlank(message = "testTime不能为空")
    @JsonProperty("testTime")
    private String testTime;

    /**
     * 核酸检测机构的联系电话，在 JSON 序列化和反序列化时对应 "contactNumber" 字段。
     * 该字段不能为空，否则会触发校验错误。
     */
    @NotNull(message = "contactNumber不能为空")
    @JsonProperty("contactNumber")
    private String contactNumber;
}