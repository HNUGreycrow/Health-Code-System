package org.software.code.vo;

import lombok.Data;

/**
 * 地点状态信息的值对象（Value Object），用于在不同层之间传输地点及其状态相关信息。
 * 使用 Lombok 的 @Data 注解，自动生成 getter、setter、toString、equals 和 hashCode 等方法。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class PlaceStatusVo {
    // 地点名称，标识具体的地点
    private String place;
    // 地点的状态，布尔类型，true 或 false 代表不同的状态含义（具体含义需根据业务场景确定）
    private boolean status;
}