package org.software.code.vo;

import lombok.Data;

/**
 * AreaCodeVo 是一个视图对象（VO），用于封装地区码相关信息。
 * 该类使用 Lombok 的 @Data 注解自动生成 getter、setter、toString、equals 和 hashCode 等方法，
 * 方便在视图层展示和传递地区码相关的数据。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class AreaCodeVo {

    /**
     * 地区码的唯一标识 ID，用于在系统中唯一标识一个地区码记录。
     */
    private Long id;

    /**
     * 地区编号，代表地区的行政区划代码。
     */
    private Integer district;

    /**
     * 街道编号，用于进一步细化地区的划分。
     */
    private Integer street;

    /**
     * 社区编号，用于精确标识地区内的社区。
     */
    private Long community;
}