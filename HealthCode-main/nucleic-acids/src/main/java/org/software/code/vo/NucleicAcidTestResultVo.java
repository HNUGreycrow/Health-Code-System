package org.software.code.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Data;

import java.util.Date;

/**
 * NucleicAcidTestResultVo 是一个视图对象（Value Object），
 * 用于封装核酸检测结果的相关信息，主要用于在业务逻辑层和视图层之间传递数据，
 * 方便前端展示核酸检测结果的详细情况。借助 Lombok 的 @Data 注解，
 * 自动生成了 getter、setter、toString、equals 和 hashCode 等方法。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class NucleicAcidTestResultVo {
    /**
     * 核酸检测结果的创建时间。
     * 方便前端接收和展示日期信息。
     */
    private LocalDateTime createdAt;

    /**
     * 核酸检测的结果，通常用不同的整数值来表示，
     * 例如 0 可能代表阴性，1 可能代表阳性等，具体含义根据系统定义。
     */
    private Integer result;

    /**
     * 执行此次核酸检测的机构名称，用于明确检测的来源和责任主体。
     */
    private String testingOrganization;
}