package org.software.code.vo;

import lombok.Data;

/**
 * NucleicAcidTestInfoVo 是一个视图对象（Value Object），
 * 用于封装核酸检测相关的统计信息，方便在业务逻辑层和视图层之间传递数据，以展示核酸检测的整体情况。
 * 通过 Lombok 的 @Data 注解，自动生成了 getter、setter、toString、equals 和 hashCode 等方法。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class NucleicAcidTestInfoVo {
    /**
     * 核酸检测的总记录数，代表了进行过核酸检测的所有记录的数量。
     */
    private long record;

    /**
     * 未检测的数量，可能表示已经安排但尚未进行核酸检测的人数或样本数。
     */
    private long uncheck;

    /**
     * 单管阳性的数量，即单管核酸检测结果为阳性的数量。
     */
    private long onePositive;

    /**
     * 总的阳性数量，包含了单管阳性以及其他检测方式下的阳性结果数量。
     */
    private long positive;
}