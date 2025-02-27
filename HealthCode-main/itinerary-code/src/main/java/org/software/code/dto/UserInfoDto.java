package org.software.code.dto;

import lombok.Data;

/**
 * 用户信息数据传输对象（DTO），用于在不同层之间传输用户相关信息。
 * 该类使用 Lombok 的 @Data 注解，自动生成 getter、setter、toString、equals 和 hashCode 等方法。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class UserInfoDto {
    // 用户唯一标识
    private Long uid;
    // 用户姓名
    private String name;
    // 用户电话号码
    private String phoneNumber;
    // 用户身份证号码
    private String identityCard;
    // 用户所在地区编号
    private int district;
    // 用户所在街道编号
    private int street;
    // 用户所在社区编号
    private long community;
    // 用户详细地址
    private String address;
}