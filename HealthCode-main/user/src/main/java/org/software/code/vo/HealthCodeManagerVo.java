package org.software.code.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * HealthCodeManagerVo 是一个视图对象（Value Object），用于封装健康码管理人员的相关信息。
 * 该类主要用于在业务逻辑层和表示层之间传输数据，方便前端展示和处理健康码管理人员的信息。
 * 它使用了 Lombok 注解来简化代码，提供了数据的 getter、setter、构造方法等。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HealthCodeManagerVo {

    /**
     * 健康码管理人员的唯一标识 ID，使用 Long 类型存储。
     * 使用 @JsonFormat(shape = JsonFormat.Shape.STRING) 注解，在进行 JSON 序列化时将其转换为字符串类型，
     * 避免在前端处理大整数时可能出现的精度丢失问题。
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long mid;

    /**
     * 健康码管理人员的身份证号码，使用 String 类型存储。
     * 用于唯一标识管理人员的身份信息。
     */
    private String identityCard;

    /**
     * 健康码管理人员的姓名，使用 String 类型存储。
     * 表示管理人员的真实姓名。
     */
    private String name;

    /**
     * 健康码管理人员的状态，使用 Boolean 类型存储。
     * true 表示管理人员处于可用状态，false 表示管理人员处于不可用状态。
     */
    private Boolean status;
}