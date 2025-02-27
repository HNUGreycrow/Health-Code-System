package org.software.code.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 区域编码数据传输对象，用于封装区域相关的编码信息，
 * 包含地区、街道和社区的编码，可在不同层级间传递区域编码数据。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AreaCodeDto {
    /**
     * 地区编码，该字段为必填项。
     * 在序列化和反序列化时，JSON 中的字段名为 "district"。
     */
    @NotNull(message = "district不能为空")
    @JsonProperty("district")
    private Integer district;

    /**
     * 街道编码，该字段为必填项。
     * 在序列化和反序列化时，JSON 中的字段名为 "street"。
     */
    @NotNull(message = "street不能为空")
    @JsonProperty("street")
    private Integer street;

    /**
     * 社区编码，该字段为必填项。
     * 在序列化和反序列化时，JSON 中的字段名为 "community"。
     */
    @NotNull(message = "community不能为空")
    @JsonProperty("community")
    private Long community;
}