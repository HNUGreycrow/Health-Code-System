package org.software.code.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 该类是创建场所码请求的数据传输对象（DTO），用于封装创建场所码所需的各种信息，
 * 并在不同层之间传递这些信息，比如从控制器层传递到服务层。
 * 借助 Lombok 的 @Data 注解，自动生成了 getter、setter、toString、equals 和 hashCode 等方法。
 * 同时使用 @NotBlank 和 @NotNull 注解对各字段进行校验，确保请求数据的完整性和有效性。
 * 通过 @JsonProperty 注解指定 JSON 序列化和反序列化时的字段名称。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class CreatePlaceCodeRequestDto {
    /**
     * 创建场所码关联用户的身份证号码，该字段不能为空白字符串。
     * 在 JSON 序列化和反序列化时，该字段对应的名称为 "identity_card"。
     */
    @NotBlank(message = "identity_card不能为空")
    @JsonProperty("identity_card")
    private String identity_card;

    /**
     * 场所的名称，该字段不能为空白字符串。
     * 在 JSON 序列化和反序列化时，该字段对应的名称为 "name"。
     */
    @NotBlank(message = "name不能为空")
    @JsonProperty("name")
    private String name;

    /**
     * 场所所在地区的编号，该字段不能为 null。
     * 在 JSON 序列化和反序列化时，该字段对应的名称为 "district_id"。
     */
    @NotNull(message = "district_id不能为空")
    @JsonProperty("district_id")
    private Integer district_id;

    /**
     * 场所所在街道的编号，该字段不能为 null。
     * 在 JSON 序列化和反序列化时，该字段对应的名称为 "street_id"。
     */
    @NotNull(message = "street_id不能为空")
    @JsonProperty("street_id")
    private Integer street_id;

    /**
     * 场所所在社区的编号，该字段不能为 null。
     * 在 JSON 序列化和反序列化时，该字段对应的名称为 "community_id"。
     */
    @NotNull(message = "community_id不能为空")
    @JsonProperty("community_id")
    private Long community_id;

    /**
     * 场所的详细地址，该字段不能为空白字符串。
     * 在 JSON 序列化和反序列化时，该字段对应的名称为 "address"。
     */
    @NotBlank(message = "address不能为空")
    @JsonProperty("address")
    private String address;
}