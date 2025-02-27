package org.software.code.vo;

import lombok.Data;

/**
 * UserInfoVo 是一个视图对象（VO），用于封装用户的详细信息。
 * 该对象主要用于在视图层展示用户信息，或者在不同服务之间传递用户信息。
 * 通过 Lombok 的 @Data 注解，自动生成了 getter、setter、toString、equals 和 hashCode 等方法，
 * 方便对用户信息进行操作和展示。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class UserInfoVo {
    /**
     * 用户的唯一标识，用于在系统中唯一确定一个用户。
     */
    private Long uid;

    /**
     * 用户的姓名，用于识别用户的身份。
     */
    private String name;

    /**
     * 用户的电话号码，方便与用户进行联系。
     */
    private String phoneNumber;

    /**
     * 用户的身份证号码，是用户身份的重要标识。
     */
    private String identityCard;

    /**
     * 用户所在地区的编号，用于定位用户所在的大致区域。
     */
    private int district;

    /**
     * 用户所在街道的编号，进一步细化用户的地理位置。
     */
    private int street;

    /**
     * 用户所在社区的编号，更精确地确定用户的居住范围。
     */
    private long community;

    /**
     * 用户的详细地址，明确用户的居住位置。
     */
    private String address;
}