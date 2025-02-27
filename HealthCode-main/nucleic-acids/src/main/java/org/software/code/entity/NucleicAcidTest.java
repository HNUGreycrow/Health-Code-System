package org.software.code.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 
 * @TableName nucleic_acid_test
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@TableName(value ="nucleic_acid_test")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NucleicAcidTest implements Serializable {
    /**
     * 核酸检测记录的唯一标识
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户唯一标识
     */
    @TableField(value = "uid")
    private Long uid;

    /**
     * 核酸检测人员唯一标识
     */
    @TableField(value = "tid")
    private Long tid;

    /**
     * 检测管编号
     */
    @TableField(value = "tubeid")
    private Long tubeid;

    /**
     * 核酸检测地址
     */
    @TableField(value = "test_address")
    private String testAddress;

    /**
     * false：未复检，true：已复检
     */
    @TableField(value = "re_test")
    private Integer reTest;

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
        NucleicAcidTest other = (NucleicAcidTest) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()))
            && (this.getTid() == null ? other.getTid() == null : this.getTid().equals(other.getTid()))
            && (this.getTubeid() == null ? other.getTubeid() == null : this.getTubeid().equals(other.getTubeid()))
            && (this.getTestAddress() == null ? other.getTestAddress() == null : this.getTestAddress().equals(other.getTestAddress()))
            && (this.getReTest() == null ? other.getReTest() == null : this.getReTest().equals(other.getReTest()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()))
            && (this.getUpdatedAt() == null ? other.getUpdatedAt() == null : this.getUpdatedAt().equals(other.getUpdatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        result = prime * result + ((getTid() == null) ? 0 : getTid().hashCode());
        result = prime * result + ((getTubeid() == null) ? 0 : getTubeid().hashCode());
        result = prime * result + ((getTestAddress() == null) ? 0 : getTestAddress().hashCode());
        result = prime * result + ((getReTest() == null) ? 0 : getReTest().hashCode());
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
        sb.append(", uid=").append(uid);
        sb.append(", tid=").append(tid);
        sb.append(", tubeid=").append(tubeid);
        sb.append(", testAddress=").append(testAddress);
        sb.append(", reTest=").append(reTest);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}