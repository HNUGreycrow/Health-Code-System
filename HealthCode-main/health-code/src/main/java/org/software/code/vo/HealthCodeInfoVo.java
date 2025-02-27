package org.software.code.vo;

import lombok.Data;

/**
 * HealthCodeInfoVo 类用于表示健康码信息的视图对象，包含用户唯一标识、姓名、状态和身份证号等信息。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class HealthCodeInfoVo {
    /**
     * 用户的唯一标识，在 JSON 序列化时将其格式化为字符串
     */
    private long uid;

    /**
     * 用户的姓名，不能为空
     */
    private String name;

    /**
     * 健康码状态，具体含义根据业务需求而定，不能为 null
     */
    private int status;

    /**
     * 用户的身份证号，不能为空且需要符合身份证号的格式
     */
    private String identityCard;
}