package org.software.code.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author “101”计划《软件工程》实践教材案例团队
 * 疫苗接种记录视图对象类，用于封装疫苗接种相关信息。
 */
@Data
public class VaccinationRecordVo {

  /**
   * 接种记录ID
   */
  private Long id;

  /**
   * 用户ID
   */
  private Long uid;

  /**
   * 疫苗接种点名称
   */
  private String name;

  /**
   * 疫苗类型
   */
  private String vaccineType;

  /**
   * 接种日期
   */
  private Date vaccDate;
}