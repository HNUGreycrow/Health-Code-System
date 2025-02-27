package org.software.code.vo;

import lombok.Data;

/**
 * HealthQRCodeVo 类用于表示健康码二维码相关信息的视图对象。
 * 该类包含二维码令牌和状态信息。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class HealthQRCodeVo {
    /**
     * 二维码令牌，用于标识或获取特定的二维码信息。
     */
    private String qrcode_token;
    /**
     * 健康码二维码的状态信息，具体状态含义根据业务需求而定。
     */
    private int status ;
}