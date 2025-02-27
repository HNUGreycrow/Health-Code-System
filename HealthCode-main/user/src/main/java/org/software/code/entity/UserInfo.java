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
 * @TableName user_info
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@TableName(value ="user_info")
@Data
public class UserInfo implements Serializable {
    /**
     * 用户唯一标识
     */
    @TableId
    @Column(name = "uid", nullable = false)
    private Long uid;

    /**
     * 用户身份证号码
     */
    @Size(max = 18)
    @NotNull
    @Column(name = "identity_card", nullable = false, length = 18)
    private String identityCard;

    /**
     * 用户手机号码
     */
    @Size(max = 11)
    @NotNull
    @Column(name = "phone_number", nullable = false, length = 11)
    private String phoneNumber;

    /**
     * 用户姓名
     */
    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 用户所在区域的 ID
     */
    @NotNull
    @Column(name = "area_id", nullable = false)
    private Long areaId;

    /**
     * 用户详细地址
     */
    @Size(max = 255)
    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

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
        UserInfo other = (UserInfo) that;
        return (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()))
            && (this.getIdentityCard() == null ? other.getIdentityCard() == null : this.getIdentityCard().equals(other.getIdentityCard()))
            && (this.getPhoneNumber() == null ? other.getPhoneNumber() == null : this.getPhoneNumber().equals(other.getPhoneNumber()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getAreaId() == null ? other.getAreaId() == null : this.getAreaId().equals(other.getAreaId()))
            && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()))
            && (this.getUpdatedAt() == null ? other.getUpdatedAt() == null : this.getUpdatedAt().equals(other.getUpdatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        result = prime * result + ((getIdentityCard() == null) ? 0 : getIdentityCard().hashCode());
        result = prime * result + ((getPhoneNumber() == null) ? 0 : getPhoneNumber().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getAreaId() == null) ? 0 : getAreaId().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
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
        sb.append(", uid=").append(uid);
        sb.append(", identityCard=").append(identityCard);
        sb.append(", phoneNumber=").append(phoneNumber);
        sb.append(", name=").append(name);
        sb.append(", areaId=").append(areaId);
        sb.append(", address=").append(address);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}