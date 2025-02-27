package org.software.code.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * AreaCodeDto 是一个数据传输对象（DTO），用于封装区域编码相关信息。
 * 该类提供了区域的行政区划信息，包含区、街道和社区的编码。
 * 使用 Lombok 注解来简化代码，提供了构建器模式、全参构造函数、无参构造函数以及自动生成的 getter、setter 等方法。
 * 通过 @JsonProperty 注解指定 JSON 序列化和反序列化时的字段名称，并使用 @NotNull 注解对关键属性进行非空校验。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AreaCodeDto {
    /**
     * 区域所在的区编码。
     * 在 JSON 序列化和反序列化时对应 "district" 字段，该字段不能为空，否则会触发校验错误。
     */
    @NotNull(message = "district不能为空")
    @JsonProperty("district")
    private Integer district;

    /**
     * 区域所在的街道编码。
     * 在 JSON 序列化和反序列化时对应 "street" 字段，该字段不能为空，否则会触发校验错误。
     */
    @NotNull(message = "street不能为空")
    @JsonProperty("street")
    private Integer street;

    /**
     * 区域所在的社区编码。
     * 在 JSON 序列化和反序列化时对应 "community" 字段，该字段不能为空，否则会触发校验错误。
     */
    @NotNull(message = "community不能为空")
    @JsonProperty("community")
    private Long community;
}