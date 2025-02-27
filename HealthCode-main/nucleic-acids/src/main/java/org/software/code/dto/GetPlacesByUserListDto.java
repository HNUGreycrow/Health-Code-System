package org.software.code.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * GetPlacesByUserListDto 是一个数据传输对象（DTO），用于封装根据用户列表和时间范围查询地点信息所需的数据。
 * 该类使用 Lombok 的 @Data 注解自动生成 getter、setter、toString、equals 和 hashCode 等方法，
 * 并通过 @JsonProperty 注解指定 JSON 序列化和反序列化时的字段名称，
 * 同时使用 @NotNull 和 @Size 注解对关键属性进行非空和长度校验。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class GetPlacesByUserListDto {

    /**
     * 用户 ID 列表，用于指定要查询地点信息的用户集合。
     * 在 JSON 序列化和反序列化时对应 "uidList" 字段。
     * 该列表不能为空，且至少包含一个元素，否则会抛出校验错误。
     */
    @NotNull(message = "uidList不能为空")
    @Size(min = 1, message = "uidList不能为空")
    @JsonProperty("uidList")
    private List<Long> uidList;

    /**
     * 查询的开始时间，用于限定查询地点信息的时间范围起始点。
     * 在 JSON 序列化和反序列化时对应 "start_time" 字段。
     * 该字段不能为空，否则会抛出校验错误。
     */
    @NotNull(message = "开始时间不能为空")
    @JsonProperty("start_time")
    private String start_time;

    /**
     * 查询的结束时间，用于限定查询地点信息的时间范围结束点。
     * 在 JSON 序列化和反序列化时对应 "end_time" 字段。
     * 该字段不能为空，否则会抛出校验错误。
     */
    @NotNull(message = "结束时间不能为空")
    @JsonProperty("end_time")
    private String end_time;

    /**
     * 构造函数，用于创建 GetPlacesByUserListDto 对象。
     *
     * @param uidList 用户 ID 列表
     * @param start_time 查询的开始时间
     * @param end_time 查询的结束时间
     */
    public GetPlacesByUserListDto(List<Long> uidList, String start_time, String end_time) {
        this.uidList = uidList;
        this.start_time = start_time;
        this.end_time = end_time;
    }
}