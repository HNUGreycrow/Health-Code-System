package org.software.code.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 
 * @TableName place_info
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@TableName(value ="place_info")
@Data
public class PlaceInfo implements Serializable {
    /**
     * 场所唯一标识
     */
    @TableId(value = "pid")
    private Long pid;

    /**
     * 关联的用户 ID
     */
    @TableField(value = "uid")
    private Long uid;

    /**
     * 场所名称
     */
    @TableField(value = "place_name")
    private String placeName;

    /**
     * 场所所在区域的 ID
     */
    @TableField(value = "area_id")
    private Long areaId;

    /**
     * 场所详细地址
     */
    @TableField(value = "address")
    private String address;

    /**
     * false：关停，true：开启
     */
    @TableField(value = "status")
    private Boolean status;

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
        PlaceInfo other = (PlaceInfo) that;
        return (this.getPid() == null ? other.getPid() == null : this.getPid().equals(other.getPid()))
            && (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()))
            && (this.getPlaceName() == null ? other.getPlaceName() == null : this.getPlaceName().equals(other.getPlaceName()))
            && (this.getAreaId() == null ? other.getAreaId() == null : this.getAreaId().equals(other.getAreaId()))
            && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()))
            && (this.getUpdatedAt() == null ? other.getUpdatedAt() == null : this.getUpdatedAt().equals(other.getUpdatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPid() == null) ? 0 : getPid().hashCode());
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        result = prime * result + ((getPlaceName() == null) ? 0 : getPlaceName().hashCode());
        result = prime * result + ((getAreaId() == null) ? 0 : getAreaId().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
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
        sb.append(", pid=").append(pid);
        sb.append(", uid=").append(uid);
        sb.append(", placeName=").append(placeName);
        sb.append(", areaId=").append(areaId);
        sb.append(", address=").append(address);
        sb.append(", status=").append(status);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}