package org.software.code.vo;

import lombok.Data;

/**
 * UserInfoVo 是一个视图对象（Value Object），用于封装用户的相关信息。
 * 它主要用于在业务逻辑层和视图层之间传递用户信息，方便前端展示用户的基本情况。
 * 通过 Lombok 的 @Data 注解，自动生成了 getter、setter、toString、equals 和 hashCode 等方法。
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
     * 用户的姓名。
     */
    private String name;

    /**
     * 用户的电话号码，方便与用户进行联系。
     */
    private String phoneNumber;

    /**
     * 用户的身份证号码，作为用户身份的重要标识。
     */
    private String identityCard;

    /**
     * 用户所在地区的编号，用于表示用户所在的行政区范围。
     */
    private int district;

    /**
     * 用户所在街道的编号，进一步细化用户的地理位置信息。
     */
    private int street;

    /**
     * 用户所在社区的编号，更精确地定位用户所属的社区范围。
     */
    private long community;

    /**
     * 用户的详细居住地址。
     */
    private String address;
}