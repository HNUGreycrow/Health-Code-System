package org.software.code.dto;

import lombok.Data;

/**
 * 核酸检测记录的数据传输对象（DTO），用于在不同层之间传递核酸检测记录相关信息，
 * 例如从数据访问层传递到服务层，或者从服务层传递到控制器层。
 * 借助 Lombok 的 @Data 注解，自动生成了 getter、setter、toString、equals 和 hashCode 等方法。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class NucleicAcidTestRecordDto {
    /**
     * 核酸检测记录的创建时间，采用时间戳格式存储。
     * 该字段记录了核酸检测结果录入系统的具体时间，可用于后续的统计和查询。
     */
    private String created_at; //timestamp

    /**
     * 核酸检测结果，用整数表示。
     * 不同的整数值对应不同的检测结果，具体的映射关系需根据业务逻辑确定。
     */
    private int result;

    /**
     * 进行核酸检测的机构名称。
     * 该字段记录了负责此次核酸检测的机构，有助于追溯检测的来源。
     */
    private String testing_organization;
}