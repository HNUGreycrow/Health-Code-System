package org.software.code.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserInfoVo 是一个视图对象（Value Object），用于封装用户信息，
 * 主要用于在业务逻辑层和表示层之间传输用户相关数据，方便前端展示和处理。
 * 该类使用了 Lombok 注解，自动生成了 getter、setter、构造方法等，简化了代码编写。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVo {

    /**
     * 用户的唯一标识 ID
     */
    private Long uid;

    /**
     * 用户的姓名
     */
    private String name;

    /**
     * 用户的电话号码
     */
    private String phoneNumber;

    /**
     * 用户的身份证号码
     */
    private String identityCard;

    /**
     * 用户所在地区的区编码，以整数形式存储，用于标识用户所在的区。
     */
    private int district;

    /**
     * 用户所在地区的街道编码，以整数形式存储，用于标识用户所在的街道。
     */
    private int street;

    /**
     * 用户所在地区的社区编码，以长整数形式存储，用于标识用户所在的社区。
     */
    private long community;

    /**
     * 用户的详细地址，记录用户居住或工作的具体地址信息。
     */
    private String address;
}