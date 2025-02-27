package org.software.code.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @TableName tube_info
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@TableName(value ="tube_info")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TubeInfo implements Serializable {
    /**
     * 检测管信息记录的唯一标识
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 检测管编号
     */
    @TableField(value = "tubeid")
    private Long tubeid;

    /**
     * 0 单管， 1 十人混管， 2 二十人混管
     */
    @TableField(value = "kind")
    private Integer kind;

    /**
     * 0：阴性，1：阳性，2未出
     */
    @TableField(value = "result")
    private Integer result;

    /**
     * 检测机构名称
     */
    @TableField(value = "testing_organization")
    private String testingOrganization;

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
        TubeInfo other = (TubeInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTubeid() == null ? other.getTubeid() == null : this.getTubeid().equals(other.getTubeid()))
            && (this.getKind() == null ? other.getKind() == null : this.getKind().equals(other.getKind()))
            && (this.getResult() == null ? other.getResult() == null : this.getResult().equals(other.getResult()))
            && (this.getTestingOrganization() == null ? other.getTestingOrganization() == null : this.getTestingOrganization().equals(other.getTestingOrganization()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()))
            && (this.getUpdatedAt() == null ? other.getUpdatedAt() == null : this.getUpdatedAt().equals(other.getUpdatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTubeid() == null) ? 0 : getTubeid().hashCode());
        result = prime * result + ((getKind() == null) ? 0 : getKind().hashCode());
        result = prime * result + ((getResult() == null) ? 0 : getResult().hashCode());
        result = prime * result + ((getTestingOrganization() == null) ? 0 : getTestingOrganization().hashCode());
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
        sb.append(", tubeid=").append(tubeid);
        sb.append(", kind=").append(kind);
        sb.append(", result=").append(result);
        sb.append(", testingOrganization=").append(testingOrganization);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}