package org.software.code.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 用于封装获取行程信息响应结果的数据传输对象（Value Object）。
 * 该类借助 Lombok 的 @Data 注解，自动生成了 getter、setter、toString、equals 和 hashCode 等常用方法。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class GetItineraryVo {
    // 行程中途径地点的信息列表，每个地点信息由 PlaceStarVo 对象表示
    private List<PlaceStarVo> places;
    // 关联的用户身份证号码，用于标识行程所属用户
    private String identityCard;
    // 行程信息的创建时间，记录行程数据生成的具体时刻
    private Date created_at;
}