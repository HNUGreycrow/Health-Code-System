package org.software.code.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * UpdateAppealDto 是一个数据传输对象（DTO），用于封装更新申诉信息所需的数据。
 * 该类借助 Lombok 的 @Data 注解，自动生成了 getter、setter、toString、equals 和 hashCode 等方法，
 * 方便对申诉信息进行操作和传输。同时，使用 @NotBlank 注解对关键字段进行非空校验。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class UpdateAppealDto {
  /**
   * 要更新的申诉记录的唯一标识 ID，该字段不能为空。
   * 通过该 ID 可以准确定位到需要更新的具体申诉信息。
   */
  @NotBlank
  private Integer appealId;

  /**
   * 申诉的具体原因，可记录较长文本。
   * 该字段用于更新申诉的原因描述，如果不设置则保持原有值。
   */
  private String appealReason;

  /**
   * 申诉时提交的相关材料，以文本形式存储。
   * 该字段用于更新申诉时所提交的材料信息，如果不设置则保持原有值。
   */
  private String appealMaterials;

  /**
   * 申诉的处理状态，使用整数表示不同的状态。
   * 该字段用于更新申诉的当前处理状态，如果不设置则保持原有值。
   */
  private Integer appealStatus;
}