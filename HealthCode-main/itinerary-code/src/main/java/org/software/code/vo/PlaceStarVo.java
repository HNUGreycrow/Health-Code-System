package org.software.code.vo;

import lombok.Data;

/**
 * 地点星级信息值对象（Value Object），用于封装地点及其是否为重点关注地点（星级标识）的信息，
 * 主要用于数据在不同层之间的传递，使用 Lombok 的 @Data 注解自动生成 getter、setter、toString、equals 和 hashCode 等方法。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class PlaceStarVo {
    // 地点名称
    private String place;
    // 该地点是否为重点关注地点的标识，true 表示是，false 表示否
    private boolean star;
}