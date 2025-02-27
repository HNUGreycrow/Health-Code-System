package org.software.code.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Time;

/**
 * 疫苗接种点的数据传输对象（DTO）类
 * 用于在不同层之间传输疫苗接种点的相关信息
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class VaccinationSiteDto {

  /**
   * 接种点的唯一标识ID
   * 不能为空，若为空则会抛出异常，异常信息为“id不能为空”
   */
  @NotNull(message = "id不能为空")
  @JsonProperty("uid")
  private Long id;

  /**
   * 接种点名称
   * 不能为空且不能为空白字符串，若为空或空白则会抛出异常，异常信息为“name不能为空”
   */
  @NotBlank(message = "name不能为空")
  @JsonProperty("name")
  private String name;

  /**
   * 接种点所在地区编码（区）
   * 不能为空，若为空则会抛出异常，异常信息为“district不能为空”
   */
  @NotNull(message = "district不能为空")
  @JsonProperty("district")
  private Integer district;

  /**
   * 接种点所在街道编码
   * 不能为空，若为空则会抛出异常，异常信息为“street不能为空”
   */
  @NotNull(message = "street不能为空")
  @JsonProperty("street")
  private Integer street;

  /**
   * 接种点所在社区编码
   * 不能为空，若为空则会抛出异常，异常信息为“community不能为空”
   */
  @NotNull(message = "community不能为空")
  @JsonProperty("community")
  private Long community;

  /**
   * 接种点地址
   * 不能为空且不能为空白字符串，若为空或空白则会抛出异常，异常信息为“address不能为空”
   */
  @NotBlank(message = "address不能为空")
  @JsonProperty("address")
  private String address;

  /**
   * 接种点预约时间
   * 不能为空且不能为空白字符串，若为空或空白则会抛出异常，异常信息为“appointmentTime不能为空”
   */
  @NotBlank(message = "appointmentTime不能为空")
  @JsonProperty("appointmentTime")
  private String  appointmentTime;

}