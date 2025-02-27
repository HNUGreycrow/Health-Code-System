package org.software.code.vo;

import lombok.Data;

/**
 * @author “101”计划《软件工程》实践教材案例团队
 * 地区编码视图对象类，用于封装地区编码相关信息。
 */
@Data
public class AreaCodeVo {
  /**
   * 地区编码ID
   */
  private Long id;
  /**
   * 地区编号
   */
  private Integer district;

  /**
   * 街道编号
   */
  private Integer street;

  /**
   * 社区编号
   */
  private Long community;
}