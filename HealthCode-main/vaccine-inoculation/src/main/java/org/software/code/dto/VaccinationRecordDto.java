package org.software.code.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 疫苗接种记录的数据传输对象（DTO）类
 * 用于在不同层之间传输疫苗接种记录的相关信息
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class VaccinationRecordDto {

  /**
   * 接种点名称
   * 不能为空，若为空则会抛出异常，异常信息为“name不能为空”
   */
  @NotNull(message = "name不能为空")
  @JsonProperty("name")
  private String name;

  /**
   * 疫苗接种日期
   * 不能为空，若为空则会抛出异常，异常信息为“vaccDate不能为空”
   */
  @NotNull(message = "vaccDate不能为空")
  @JsonProperty("vaccDate")
  private Date vaccDate;

}