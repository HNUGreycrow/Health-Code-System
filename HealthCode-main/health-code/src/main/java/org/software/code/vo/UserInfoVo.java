package org.software.code.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserInfoVo 是一个视图对象（VO），用于封装用户信息，
 * 主要用于在不同层之间传递和展示用户的相关数据，例如从服务层传递到控制器层，再展示给前端。
 * 借助 Lombok 注解，提供了便捷的对象创建和操作方式。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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