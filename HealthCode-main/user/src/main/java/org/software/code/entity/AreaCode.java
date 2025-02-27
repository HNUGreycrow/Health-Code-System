package org.software.code.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName area_code
 *
 * @author “101”计划《软件工程》实践教材案例团队
 *
 */
@TableName(value ="area_code")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AreaCode implements Serializable {
    /**
     * 区域编码记录的唯一标识
     */
    @TableId(type = IdType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * 区编号
     */
    @NotNull
    @Column(name = "district", nullable = false)
    private Integer district;

    /**
     * 街道编号
     */
    @NotNull
    @Column(name = "street", nullable = false)
    private Integer street;

    /**
     * 社区编号
     */
    @NotNull
    @Column(name = "community", nullable = false)
    private Long community;

    /**
     * 记录创建时间
     */
    @Column(name = "created_at")
    private Date createdAt;

    /**
     * 记录更新时间
     */
    @Column(name = "updated_at")
    private Date updatedAt;

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
        AreaCode other = (AreaCode) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getDistrict() == null ? other.getDistrict() == null : this.getDistrict().equals(other.getDistrict()))
            && (this.getStreet() == null ? other.getStreet() == null : this.getStreet().equals(other.getStreet()))
            && (this.getCommunity() == null ? other.getCommunity() == null : this.getCommunity().equals(other.getCommunity()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()))
            && (this.getUpdatedAt() == null ? other.getUpdatedAt() == null : this.getUpdatedAt().equals(other.getUpdatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getDistrict() == null) ? 0 : getDistrict().hashCode());
        result = prime * result + ((getStreet() == null) ? 0 : getStreet().hashCode());
        result = prime * result + ((getCommunity() == null) ? 0 : getCommunity().hashCode());
        result = prime * result + ((getCreatedAt() == null) ? 0 : getCreatedAt().hashCode());
        result = prime * result + ((getUpdatedAt() == null) ? 0 : getUpdatedAt().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", district=").append(district);
        sb.append(", street=").append(street);
        sb.append(", community=").append(community);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}