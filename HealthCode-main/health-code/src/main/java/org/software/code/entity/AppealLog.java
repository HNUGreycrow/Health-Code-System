package org.software.code.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 该表用于记录用户的申诉信息，包括申诉原因、材料、状态以及创建和更新时间等
 *
 * @author “101”计划《软件工程》实践教材案例团队
 *
 * @TableName appeal_log
 */
@TableName(value ="appeal_log")
@Data
public class AppealLog implements Serializable {
    /**
     * 申诉记录的唯一标识，自增主键
     */
    @TableId(value = "appeal_id", type = IdType.AUTO)
    private Integer appealId;

    /**
     * 用户唯一标识
     */
    @TableField(value = "uid")
    private Long uid;

    /**
     * 申诉的具体原因，可记录较长文本
     */
    @TableField(value = "appeal_reason")
    private String appealReason;

    /**
     * 申诉时提交的相关材料，以 Base64 编码形式存储
     */
    @TableField(value = "appeal_materials")
    private String appealMaterials;

    /**
     * 0: 未处理, 1: 已处理
     */
    @TableField(value = "appeal_status")
    private Integer appealStatus;

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
        AppealLog other = (AppealLog) that;
        return (this.getAppealId() == null ? other.getAppealId() == null : this.getAppealId().equals(other.getAppealId()))
            && (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()))
            && (this.getAppealReason() == null ? other.getAppealReason() == null : this.getAppealReason().equals(other.getAppealReason()))
            && (this.getAppealMaterials() == null ? other.getAppealMaterials() == null : this.getAppealMaterials().equals(other.getAppealMaterials()))
            && (this.getAppealStatus() == null ? other.getAppealStatus() == null : this.getAppealStatus().equals(other.getAppealStatus()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()))
            && (this.getUpdatedAt() == null ? other.getUpdatedAt() == null : this.getUpdatedAt().equals(other.getUpdatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getAppealId() == null) ? 0 : getAppealId().hashCode());
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        result = prime * result + ((getAppealReason() == null) ? 0 : getAppealReason().hashCode());
        result = prime * result + ((getAppealMaterials() == null) ? 0 : getAppealMaterials().hashCode());
        result = prime * result + ((getAppealStatus() == null) ? 0 : getAppealStatus().hashCode());
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
        sb.append(", appealId=").append(appealId);
        sb.append(", uid=").append(uid);
        sb.append(", appealReason=").append(appealReason);
        sb.append(", appealMaterials=").append(appealMaterials);
        sb.append(", appealStatus=").append(appealStatus);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}