package org.software.code.dto;

import lombok.Data;

/**
 * NucleicAcidTestRecordInputDto 是一个数据传输对象（DTO），
 * 主要用于封装核酸检测记录录入时所需的关键信息。
 * 通过 Lombok 的 @Data 注解，自动生成了 getter、setter、toString、equals 和 hashCode 等方法，
 * 方便在不同层之间传递和处理核酸检测记录的录入数据。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class NucleicAcidTestRecordInputDto {
    /**
     * 核酸检测样本所在的管编号，用于唯一标识一个样本管，
     * 可帮助追踪和管理该管内样本的检测情况。
     */
    private Long tubeid;

    /**
     * 核酸检测的类型，例如单人单检、多人混检等，
     * 通常使用不同的整数值来代表不同的检测类型。
     */
    private Integer kind;

    /**
     * 核酸检测的结果，一般用不同的整数值表示，
     * 比如 0 可能代表阴性，1 可能代表阳性等，具体含义根据系统定义。
     */
    private Integer result;

    /**
     * 执行此次核酸检测的机构名称，用于明确检测的来源和责任主体。
     */
    private String testing_organization;
}