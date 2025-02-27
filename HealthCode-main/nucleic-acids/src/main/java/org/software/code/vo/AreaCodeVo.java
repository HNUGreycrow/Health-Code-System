package org.software.code.vo;

import lombok.Data;

/**
 * AreaCodeVo 是一个视图对象（Value Object），用于封装区域编码相关信息，
 * 主要用于在业务逻辑层和视图层之间传递区域编码数据，方便展示和使用。
 * 借助 Lombok 的 @Data 注解，自动生成了 getter、setter、toString、equals 和 hashCode 等方法。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class AreaCodeVo {
    /**
     * 区域编码的唯一标识，用于在系统中唯一确定一个区域编码记录。
     */
    private Long id;

    /**
     * 区域所在的区编码，用于表示该区域所属的行政区范围。
     */
    private Integer district;

    /**
     * 区域所在的街道编码，进一步细化区域的地理位置信息。
     */
    private Integer street;

    /**
     * 区域所在的社区编码，更精确地定位区域所属的社区范围。
     */
    private Long community;
}