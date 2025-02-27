package org.software.code.vo;

import lombok.Data;

import java.sql.Time;

/**
 * @author “101”计划《软件工程》实践教材案例团队
 * 疫苗接种点视图对象类，用于封装疫苗接种点相关信息。
 */
@Data
public class VaccinationSiteVo {

  /**
   * 接种点名称
   */
  private String name;

  /**
   * 接种点所在地区
   */
  private String district;

  /**
   * 接种点所在街道
   */
  private String street;

  /**
   * 接种点所在社区
   */
  private String community;

  /**
   * 接种点详细地址
   */
  private String address;

  /**
   * 接种点预约时间
   */
  private String appointmentTime;

}