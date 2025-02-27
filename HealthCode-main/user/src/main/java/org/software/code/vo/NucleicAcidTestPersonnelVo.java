package org.software.code.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * NucleicAcidTestPersonnelVo 是一个视图对象（Value Object），用于封装核酸检测人员的相关信息。
 * 该类主要用于在业务逻辑层和表示层之间传输数据，方便前端展示和处理核酸检测人员的信息。
 * 借助 Lombok 注解，简化了代码的编写，自动生成了 getter、setter、构造方法等。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NucleicAcidTestPersonnelVo {

    /**
     * 核酸检测人员的唯一标识 ID，使用 Long 类型存储。
     * 通过 @JsonFormat(shape = JsonFormat.Shape.STRING) 注解，在进行 JSON 序列化时将其转换为字符串类型，
     * 目的是防止在前端处理大整数时出现精度丢失的情况。
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tid;

    /**
     * 核酸检测人员的身份证号码，使用 String 类型存储。
     * 用于唯一标识核酸检测人员的身份信息。
     */
    private String identityCard;

    /**
     * 核酸检测人员的姓名，使用 String 类型存储。
     * 代表核酸检测人员的真实姓名。
     */
    private String name;

    /**
     * 核酸检测人员的状态，使用 Boolean 类型存储。
     * true 表示核酸检测人员处于可用状态，false 表示核酸检测人员处于不可用状态。
     */
    private Boolean status;
}