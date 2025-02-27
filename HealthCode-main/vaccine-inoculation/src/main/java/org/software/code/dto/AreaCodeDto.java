package org.software.code.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 地区编码的数据传输对象（DTO）类
 * 用于在不同层之间传输地区编码的相关信息
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AreaCodeDto {
    /**
     * 地区编码（区）
     * 不能为空，若为空则会抛出异常，异常信息为“district不能为空”
     */
    @NotNull(message = "district不能为空")
    @JsonProperty("district")
    private Integer district;

    /**
     * 地区编码（街道）
     * 不能为空，若为空则会抛出异常，异常信息为“street不能为空”
     */
    @NotNull(message = "street不能为空")
    @JsonProperty("street")
    private Integer street;

    /**
     * 地区编码（社区）
     * 不能为空，若为空则会抛出异常，异常信息为“community不能为空”
     */
    @NotNull(message = "community不能为空")
    @JsonProperty("community")
    private Long community;
}