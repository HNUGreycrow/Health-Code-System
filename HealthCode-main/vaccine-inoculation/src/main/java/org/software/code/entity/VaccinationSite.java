package org.software.code.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Time;
import java.util.Objects;

/**
 * 疫苗接种点实体类
 * @author “101”计划《软件工程》实践教材案例团队
 */
@TableName(value = "vaccination_site")  // 映射数据库表名为 vaccination_site
@Data
public class VaccinationSite implements Serializable {

  /**
   * 接种点的唯一标识 ID
   */
  @TableId(value = "id")  // 映射数据库表的主键字段为 id
  private Long id;

  /**
   * 接种点的名称
   */
  @TableField(value = "name")  // 映射数据库表的字段名为 name
  private String name;

  /**
   * 接种点所属区域的 ID
   */
  @TableField(value = "area_id")  // 映射数据库表的字段名为 area_id
  private Long areaId;

  /**
   * 接种点的地址
   */
  @TableField(value = "address")  // 映射数据库表的字段名为 address
  private String address;

  /**
   * 接种点的预约时间
   */
  @TableField(value = "appointment_time")  // 映射数据库表的字段名为 appointment_time
  private String appointmentTime;

  /**
   * 序列化版本号，用于序列化和反序列化
   */
  @TableField(exist = false)  // 表示该字段在数据库表中不存在
  private static final long serialVersionUID = 1L;

  /**
   * 重写 equals 方法，用于比较两个 VaccinationSite 对象是否相等
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
    VaccinationSite that = (VaccinationSite) o;  // 强制类型转换
    return Objects.equals(id, that.id) && Objects.equals(name, that.name)
        && Objects.equals(areaId, that.areaId) && Objects.equals(address,
        that.address) && Objects.equals(appointmentTime, that.appointmentTime);  // 比较所有字段是否相等
  }

  /**
   * 重写 hashCode 方法，用于生成对象的哈希码
   * @return 对象的哈希码
   */
  @Override
  public int hashCode() {
    return Objects.hash(id, name, areaId, address, appointmentTime);  // 根据所有字段生成哈希码
  }

  /**
   * 重写 toString 方法，用于返回对象的字符串表示形式
   * @return 对象的字符串表示形式
   */
  @Override
  public String toString() {
    return "VaccinationSite{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", areaId=" + areaId +
        ", address='" + address + '\'' +
        ", appointmentTime=" + appointmentTime +
        '}';
  }
}