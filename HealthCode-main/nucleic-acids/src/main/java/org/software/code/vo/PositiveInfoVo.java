package org.software.code.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * PositiveInfoVo 是一个视图对象（VO），用于封装核酸检测阳性信息，
 * 方便在业务逻辑层和视图层之间传递和展示阳性检测的相关数据。
 * 借助 Lombok 的 @Data 注解，自动生成了 getter、setter、toString、equals 和 hashCode 等方法。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class PositiveInfoVo {
    /**
     * 阳性检测人员的用户唯一标识。
     */
    private Long uid;

    /**
     * 核酸检测的类型，如单人单检、多人混检等，通常用整数表示不同类型。
     */
    private Integer kind;

    /**
     * 执行检测的检测人员唯一标识。
     */
    private Long tid;

    /**
     * 核酸检测样本所在的管编号，用于样本追踪和管理。
     */
    private Long tubeid;

    /**
     * 阳性检测人员的姓名。
     */
    private String name;

    /**
     * 阳性检测人员的电话号码，方便联系。
     */
    private String phoneNumber;

    /**
     * 阳性检测人员的身份证号码，作为身份的重要标识。
     */
    private String identityCard;

    /**
     * 阳性检测人员所在地区的编号，用于大致定位区域。
     */
    private Integer district;

    /**
     * 阳性检测人员所在街道的编号，进一步细化地理位置。
     */
    private Integer street;

    /**
     * 阳性检测人员所在社区的编号，更精确地确定居住范围。
     */
    private Long community;

    /**
     * 阳性检测人员的详细居住地址。
     */
    private String address;

    /**
     * 进行核酸检测的具体地址。
     */
    private String testAddress;

    /**
     * 核酸检测记录的创建时间，使用 @JsonFormat 注解将日期格式化为 "yyyy-MM-dd HH:mm:ss" 的字符串形式。
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    /**
     * 核酸检测记录的更新时间，使用 @JsonFormat 注解将日期格式化为 "yyyy-MM-dd HH:mm:ss" 的字符串形式。
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;

    /**
     * 核酸检测的结果，通常用整数表示，如 0 代表阴性，1 代表阳性等。
     */
    private Integer result;

    /**
     * 执行此次核酸检测的机构名称。
     */
    private String testingOrganization;
}