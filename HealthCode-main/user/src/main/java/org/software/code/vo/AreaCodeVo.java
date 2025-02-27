package org.software.code.vo;

import lombok.Data;

/**
 * AreaCodeVo 是一个视图对象（Value Object），用于封装区域编码相关信息，
 * 主要用于在业务逻辑层和表示层之间传输区域编码数据，方便前端展示和处理。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class AreaCodeVo {
    /**
     * 区域编码的唯一标识 ID，通常对应数据库表中的主键。
     * 用于唯一确定一条区域编码记录。
     */
    private Long id;

    /**
     * 区域编码中的区（district）编码，使用 Integer 类型存储。
     * 表示该区域所在的区的编码信息。
     */
    private Integer district;

    /**
     * 区域编码中的街道（street）编码，使用 Integer 类型存储。
     * 表示该区域所在街道的编码信息。
     */
    private Integer street;

    /**
     * 区域编码中的社区（community）编码，使用 Long 类型存储。
     * 表示该区域所在社区的编码信息。
     */
    private Long community;
}