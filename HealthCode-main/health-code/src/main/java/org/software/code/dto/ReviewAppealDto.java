package org.software.code.dto;

import lombok.Data;

/**
 * ReviewAppealDto 是一个数据传输对象（DTO），用于封装审核申诉相关信息。
 * 它主要在系统管理员审核申诉并处理健康码转码操作时，传递必要的数据。
 * 借助 Lombok 的 @Data 注解，自动生成了 getter、setter、toString、equals 和 hashCode 等方法。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class ReviewAppealDto {
  /**
   * 处理的申诉id，用于唯一标识要审核的申诉记录。
   * 系统依据该 ID 找到对应的申诉信息进行处理。
   */
  private Integer appealId;

  /**
   * 系统管理员审核申诉并转码，转码事件（0：转绿码，1：转黄码，2：转红码）。
   * 该字段使用特定的整数值代表不同的健康码转码操作，方便系统识别和执行相应的处理逻辑。
   */
  private Integer healthCodeChangeEvent;
}