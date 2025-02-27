package org.software.code.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 该类为添加场所信息的数据传输对象（DTO），用于在不同层之间传递添加场所所需的信息。
 * 借助 Lombok 的 @Data 注解，自动生成了 getter、setter、toString、equals 和 hashCode 等方法。
 * 同时使用 @NotNull 注解对各字段进行非空校验，确保添加场所时关键信息不会缺失。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class AddPlaceInputDto {
    /**
     * 场所所属用户的唯一标识，该字段不能为 null。
     * 用于关联场所与创建或管理该场所的用户。
     */
    @NotNull
    private Long uid;

    /**
     * 场所的名称，该字段不能为 null。
     * 用于明确标识该场所。
     */
    @NotNull
    private String name;

    /**
     * 场所所在的地区编号，该字段不能为 null。
     * 用于定位场所所在的大致区域。
     */
    @NotNull
    private Integer district;

    /**
     * 场所所在的街道编号，该字段不能为 null。
     * 进一步精确场所的地理位置。
     */
    @NotNull
    private Integer street;

    /**
     * 场所所在的社区编号，该字段不能为 null。
     * 更细致地确定场所的归属范围。
     */
    @NotNull
    private Long community;

    /**
     * 场所的详细地址，该字段不能为 null。
     * 提供场所的具体位置信息。
     */
    @NotNull
    private String address;
}