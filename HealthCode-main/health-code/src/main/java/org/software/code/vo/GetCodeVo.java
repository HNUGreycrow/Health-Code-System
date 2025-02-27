package org.software.code.vo;

import lombok.Data;

/**
 * GetCodeVo 类用于表示获取代码的视图对象，包含用户的身份验证令牌、状态码和名称信息。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class GetCodeVo {
    /**
     * 用户的身份验证令牌，不能为空
     */
    private String token;

    /**
     * 健康码状态，具体含义根据业务需求而定，不能为 null
     */
    private int status;

    /**
     * 用户的名称或其他相关名称信息，不能为空
     */
    private String name;
}