package org.software.code.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 该类是用于获取用户列表对应场所信息请求的数据传输对象（DTO），
 * 主要用于封装获取场所信息所需的用户 ID 列表以及时间范围信息，
 * 并在不同层之间传递这些信息，比如从控制器层传递到服务层。
 * 借助 Lombok 的 @Data 注解，自动生成了 getter、setter、toString、equals 和 hashCode 等方法。
 * 同时使用 @NotNull 和 @Size 注解对各字段进行校验，确保请求数据的完整性和有效性。
 * 通过 @JsonProperty 注解指定 JSON 序列化和反序列化时的字段名称。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class GetPlacesByUserListRequestDto {

    /**
     * 用户 ID 列表，用于指定要查询场所信息的用户。
     * 该字段不能为 null，且列表至少包含一个元素。
     * 在 JSON 序列化和反序列化时，该字段对应的名称为 "uidList"。
     */
    @NotNull(message = "uidList不能为空")
    @Size(min = 1, message = "uidList不能为空")
    @JsonProperty("uidList")
    private List<Long> uidList;

    /**
     * 查询的开始时间，用于限定查询场所信息的时间范围起始点。
     * 该字段不能为 null。
     * 在 JSON 序列化和反序列化时，该字段对应的名称为 "start_time"。
     */
    @NotNull(message = "开始时间不能为空")
    @JsonProperty("start_time")
    private String start_time;

    /**
     * 查询的结束时间，用于限定查询场所信息的时间范围结束点。
     * 该字段不能为 null。
     * 在 JSON 序列化和反序列化时，该字段对应的名称为 "end_time"。
     */
    @NotNull(message = "结束时间不能为空")
    @JsonProperty("end_time")
    private String end_time;
}