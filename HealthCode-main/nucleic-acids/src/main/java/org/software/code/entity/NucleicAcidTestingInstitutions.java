package org.software.code.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 
 * @TableName nucleic_acid_testing_institutions
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@TableName(value ="nucleic_acid_testing_institutions")
@Data
public class NucleicAcidTestingInstitutions implements Serializable {
    /**
     * 核酸检测机构的唯一标识
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 机构名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 所在区域的 ID
     */
    @TableField(value = "area_id")
    private Long areaId;

    /**
     * 详细地址
     */
    @TableField(value = "address")
    private String address;

    /**
     * 检测时间
     */
    @TableField(value = "test_time")
    private String testTime;

    /**
     * 联系电话
     */
    @TableField(value = "contact_number")
    private String contactNumber;

    /**
     * 记录创建时间
     */
    @TableField(value = "created_at")
    private LocalDateTime createdAt;

    /**
     * 记录更新时间
     */
    @TableField(value = "updated_at")
    private LocalDateTime updatedAt;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        NucleicAcidTestingInstitutions other = (NucleicAcidTestingInstitutions) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getAreaId() == null ? other.getAreaId() == null : this.getAreaId().equals(other.getAreaId()))
            && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
            && (this.getTestTime() == null ? other.getTestTime() == null : this.getTestTime().equals(other.getTestTime()))
            && (this.getContactNumber() == null ? other.getContactNumber() == null : this.getContactNumber().equals(other.getContactNumber()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getAreaId() == null) ? 0 : getAreaId().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getTestTime() == null) ? 0 : getTestTime().hashCode());
        result = prime * result + ((getContactNumber() == null) ? 0 : getContactNumber().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", areaId=").append(areaId);
        sb.append(", address=").append(address);
        sb.append(", testTime=").append(testTime);
        sb.append(", contactNumber=").append(contactNumber);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}