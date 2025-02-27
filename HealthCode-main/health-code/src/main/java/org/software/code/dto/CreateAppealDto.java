package org.software.code.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * CreateAppealDto 是一个数据传输对象（DTO），用于封装创建申诉信息时所需的数据。
 * 该类使用 Lombok 的 @Data 注解自动生成 getter、setter、toString、equals 和 hashCode 等方法，
 * 并通过 @JsonProperty 注解指定 JSON 序列化和反序列化时的字段名称，
 * 同时使用 @NotBlank 注解确保关键字段不为空或仅包含空白字符。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class CreateAppealDto {
  /**
   * 申诉的具体原因，可记录较长文本。
   * 该字段在 JSON 序列化和反序列化时对应的名称为 "appealReason"，且不能为空或仅包含空白字符。
   */
  @NotBlank
  @JsonProperty("appealReason")
  private String appealReason;

  /**
   * 申诉时提交的相关材料，以 Base64 编码形式存储。
   * 该字段在 JSON 序列化和反序列化时对应的名称为 "appealMaterials"，且不能为空或仅包含空白字符。
   */
  @NotBlank
  @JsonProperty("appealMaterials")
  private String appealMaterials;
}