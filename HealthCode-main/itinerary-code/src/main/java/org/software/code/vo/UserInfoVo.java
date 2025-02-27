package org.software.code.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户信息视图对象（Value Object），用于封装用户信息并在不同层之间进行数据传输，
 * 比如从服务层传递到表现层，便于前端展示用户相关数据。
 * 借助 Lombok 注解简化代码，提供了自动生成 getter、setter、toString、equals、hashCode 等方法，
 * 同时支持使用建造者模式创建对象以及全参和无参构造函数。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVo {
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