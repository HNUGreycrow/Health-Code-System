package org.software.code.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 疫苗接种记录实体类
 * @author “101”计划《软件工程》实践教材案例团队
 */
@TableName(value = "vaccination_record")  // 映射数据库表名为 vaccination_record
@Data
public class VaccinationRecord implements Serializable {

  /**
   * 接种记录的唯一标识 ID
   */
  @TableId(value = "id")  // 映射数据库表的主键字段为 id
  private Long id;

  /**
   * 用户的唯一标识 ID
   */
  @TableField(value = "uid")  // 映射数据库表的字段名为 uid
  private Long uid;

  /**
   * 接种点名称
   */
  @TableField(value = "name")  // 映射数据库表的字段名为 name
  private String name;

  /**
   * 疫苗的类型
   */
  @TableField(value = "vaccine_type")  // 映射数据库表的字段名为 vaccine_type
  private String vaccineType;

  /**
   * 接种日期
   */
  @TableField(value = "vacc_date")  // 映射数据库表的字段名为 vacc_date
  private Date vaccDate;

  /**
   * 序列化版本号，用于序列化和反序列化
   */
  @TableField(exist = false)  // 表示该字段在数据库表中不存在
  private static final long serialVersionUID = 1L;

  /**
   * 重写 equals 方法，用于比较两个 VaccinationRecord 对象是否相等
   * @param o 要比较的对象
   * @return 如果两个对象相等则返回 true，否则返回 false
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {  // 如果是同一个对象，直接返回 true
      return true;
    }
    if (o == null || getClass() != o.getClass()) {  // 如果对象为 null 或者类不相同，返回 false
      return false;
    }
    VaccinationRecord that = (VaccinationRecord) o;  // 强制类型转换
    return Objects.equals(id, that.id) && Objects.equals(uid, that.uid)
        && Objects.equals(name, that.name) && Objects.equals(vaccineType,
        that.vaccineType) && Objects.equals(vaccDate, that.vaccDate);  // 比较所有字段是否相等
  }

  /**
   * 重写 hashCode 方法，用于生成对象的哈希码
   * @return 对象的哈希码
   */
  @Override
  public int hashCode() {
    return Objects.hash(id, uid, name, vaccineType, vaccDate);  // 根据所有字段生成哈希码
  }

  /**
   * 重写 toString 方法，用于返回对象的字符串表示形式
   * @return 对象的字符串表示形式
   */
  @Override
  public String toString() {
    return "VaccinationRecord{" +
        "id=" + id +
        ", uid=" + uid +
        ", name=" + name +
        ", vaccineType='" + vaccineType + '\'' +
        ", vaccDate=" + vaccDate +
        '}';
  }
}