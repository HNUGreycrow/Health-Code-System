package org.software.code.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 地区码数据传输对象（DTO），用于在不同层之间传递地区码相关信息。
 * 该类使用 Lombok 注解简化代码，支持建造者模式创建对象，
 * 同时提供全参和无参构造函数，方便对象的创建和使用。
 * 借助 @JsonProperty 注解指定 JSON 序列化和反序列化时的字段名称，
 * 并使用 @NotNull 注解对字段进行非空校验，确保关键信息不缺失。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AreaCodeDto {
    /**
     * 地区编号，代表地区的行政区划代码。
     * 该字段在 JSON 序列化和反序列化时对应的字段名为 "district"，且不能为 null。
     */
    @NotNull(message = "district不能为空")
    @JsonProperty("district")
    private Integer district;

    /**
     * 街道编号，用于进一步细化地区的划分。
     * 该字段在 JSON 序列化和反序列化时对应的字段名为 "street"，且不能为 null。
     */
    @NotNull(message = "street不能为空")
    @JsonProperty("street")
    private Integer street;

    /**
     * 社区编号，用于精确标识地区内的社区。
     * 该字段在 JSON 序列化和反序列化时对应的字段名为 "community"，且不能为 null。
     */
    @NotNull(message = "community不能为空")
    @JsonProperty("community")
    private Long community;
}