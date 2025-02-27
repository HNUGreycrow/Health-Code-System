package org.software.code.dto;

import lombok.Data;

/**
 * 用户信息的数据传输对象（DTO）
 * 用于封装和传输用户的相关信息
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class UserInfoDto {
    /**
     * 用户唯一标识
     */
    private Long uid;
    /**
     * 用户姓名
     */
    private String name;
    /**
     * 用户手机号码
     */
    private String phoneNumber;
    /**
     * 用户身份证号码
     */
    private String identityCard;
    /**
     * 用户所在地区的编码
     */
    private int district;
    /**
     * 用户所在街道的编码
     */
    private int street;
    /**
     * 用户所在社区的编码
     */
    private long community;
    /**
     * 用户详细地址
     */
    private String address;
}