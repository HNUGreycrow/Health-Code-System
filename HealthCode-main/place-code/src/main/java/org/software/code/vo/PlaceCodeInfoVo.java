package org.software.code.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * PlaceCodeInfoVo 是一个视图对象（VO），用于封装场所码的详细信息，
 * 通常用于在前端页面展示或者在不同服务之间传递场所码相关的数据。
 * 使用 Lombok 的 @Data 注解自动生成 getter、setter、toString、equals 和 hashCode 等方法。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class PlaceCodeInfoVo {
    /**
     * 场所的唯一标识 ID，使用 @JsonFormat 注解将其序列化为字符串类型，
     * 避免前端在处理长整型数据时可能出现的精度丢失问题。
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long pid;

    /**
     * 关联的用户身份证号码，用于标识创建或关联该场所码的用户身份。
     */
    private String identityCard;

    /**
     * 场所的名称，用于直观地识别该场所。
     */
    private String placeName;

    /**
     * 场所所在地区的编号，用于定位场所所在的行政区域。
     */
    private Integer districtId;

    /**
     * 场所所在街道的编号，进一步细化场所的地理位置。
     */
    private Integer streetId;

    /**
     * 场所所在社区的编号，更精确地确定场所的位置归属。
     */
    private Long communityId;

    /**
     * 场所的详细地址，提供场所的具体位置信息。
     */
    private String address;

    /**
     * 场所码的状态，使用布尔值表示，例如 true 表示正常，false 表示异常等，
     * 具体状态含义根据业务逻辑定义。
     */
    private Boolean status;
}