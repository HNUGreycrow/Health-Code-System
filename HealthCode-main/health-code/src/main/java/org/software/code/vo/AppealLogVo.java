package org.software.code.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class AppealLogVo {
  /**
   * 申诉记录的唯一标识，自增主键
   */
  private Integer appealId;

  /**
   * 申述用户的id
   */
  private Long uid;

  /**
   * 用户名称
   */
  private String userName;

  /**
   * 用户身份证
   */
  private String identityCard;

  /**
   * 申诉的具体原因，可记录较长文本
   */
  private String appealReason;

  /**
   * 申诉时提交的相关材料，以 Base64 编码形式存储
   */
  private String appealMaterials;

  /**
   * 0: 未处理, 1: 已处理
   */
  private Integer appealStatus;

  /**
   * 健康码颜色，0：红色，1：黄色，2：绿色
   */
  private Integer healthCodeColor;

  /**
   * 记录创建时间
   */
  private LocalDateTime createdAt;

  /**
   * 记录更新时间
   */
  private LocalDateTime updatedAt;
}
