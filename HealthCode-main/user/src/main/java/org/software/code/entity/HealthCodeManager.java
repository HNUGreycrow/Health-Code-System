package org.software.code.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName health_code_manager
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@TableName(value ="health_code_manager")
@Data
public class HealthCodeManager implements Serializable {
    /**
     * 健康码管理人员唯一标识
     */
    @TableId
    @Column(name = "mid", nullable = false)
    private Long mid;

    /**
     * 健康码管理人员姓名
     */
    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 健康码管理人员身份证号码
     */
    @Size(max = 18)
    @NotNull
    @Column(name = "identity_card", nullable = false)
    private String identityCard;

    /**
     * 健康码管理人员登录密码的哈希值
     */
    @Size(max = 255)
    @NotNull
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    /**
     * false：不可登录，true：可以登录
     */
    @NotNull
    @Column(name = "status", nullable = false)
    private Boolean status;

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
        HealthCodeManager other = (HealthCodeManager) that;
        return (this.getMid() == null ? other.getMid() == null : this.getMid().equals(other.getMid()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getIdentityCard() == null ? other.getIdentityCard() == null : this.getIdentityCard().equals(other.getIdentityCard()))
            && (this.getPasswordHash() == null ? other.getPasswordHash() == null : this.getPasswordHash().equals(other.getPasswordHash()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()))
            && (this.getUpdatedAt() == null ? other.getUpdatedAt() == null : this.getUpdatedAt().equals(other.getUpdatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getMid() == null) ? 0 : getMid().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getIdentityCard() == null) ? 0 : getIdentityCard().hashCode());
        result = prime * result + ((getPasswordHash() == null) ? 0 : getPasswordHash().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
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
        sb.append(", mid=").append(mid);
        sb.append(", name=").append(name);
        sb.append(", identityCard=").append(identityCard);
        sb.append(", passwordHash=").append(passwordHash);
        sb.append(", status=").append(status);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}