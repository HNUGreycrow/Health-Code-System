package org.software.code.dto;

import lombok.Data;

/**
 * NucleicAcidTestRecordDto 是一个数据传输对象（DTO），用于封装核酸检测记录的相关信息。
 * 借助 Lombok 的 @Data 注解，自动生成了 getter、setter、toString、equals 和 hashCode 等方法，
 * 方便在不同层之间传输和操作核酸检测记录数据。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class NucleicAcidTestRecordDto {
    /**
     * 用户的唯一标识，用于在系统中唯一确定进行核酸检测的用户。
     */
    private Long uid;

    /**
     * 检测人员的唯一标识，用于确定执行此次核酸检测的工作人员。
     */
    private Long tid;

    /**
     * 核酸检测的类型，例如单人单检、多人混检等，以不同的整数值表示不同类型。
     */
    private Integer kind;

    /**
     * 核酸检测样本所在的管编号，用于样本的追踪和管理。
     */
    private Long tubeid;

    /**
     * 被检测人员的身份证号码，是人员身份的重要标识。
     */
    private String identity_card;

    /**
     * 被检测人员的电话号码，方便后续联系。
     */
    private String phone_number;

    /**
     * 被检测人员的姓名。
     */
    private String name;

    /**
     * 被检测人员所在地区的编号，用于定位大致区域。
     */
    private Integer district;

    /**
     * 被检测人员所在街道的编号，进一步细化地理位置。
     */
    private Integer street;

    /**
     * 被检测人员所在社区的编号，更精确地确定居住范围。
     */
    private Long community;

    /**
     * 被检测人员的详细居住地址。
     */
    private String address;

    /**
     * 进行核酸检测的具体地址。
     */
    private String test_address;

    /**
     * 执行核酸检测的机构名称。
     */
    private String testing_organization;
}