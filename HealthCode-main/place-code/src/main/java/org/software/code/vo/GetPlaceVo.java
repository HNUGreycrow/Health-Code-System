package org.software.code.vo;

import lombok.Data;

/**
 * GetPlaceVo 是一个视图对象（VO），用于封装获取场所信息时所需展示的数据。
 * 使用 Lombok 的 @Data 注解自动生成了 getter、setter、toString、equals 和 hashCode 等方法，
 * 方便在视图层展示和传递场所相关的数据。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class GetPlaceVo {
    /**
     * 关联的用户唯一标识，表明该场所与哪个用户相关。
     */
    private Long uid;

    /**
     * 用户的身份证号码，可用于识别用户身份。
     */
    private String identityCard;

    /**
     * 用户的电话号码，方便与用户取得联系。
     */
    private String phoneNumber;

    /**
     * 场所的唯一标识，用于唯一确定一个场所。
     */
    private long pid;

    /**
     * 场所的名称，用于标识该场所。
     */
    private String name;

    /**
     * 场所所在地区的编号，用于定位场所的大致区域。
     */
    private Integer district;

    /**
     * 场所所在街道的编号，进一步细化场所的地理位置。
     */
    private Integer street;

    /**
     * 场所所在社区的编号，更精确地确定场所的位置。
     */
    private Long community;

    /**
     * 场所的详细地址，明确场所的具体位置。
     */
    private String address;
}