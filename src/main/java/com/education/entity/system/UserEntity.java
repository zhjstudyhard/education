package com.education.entity.system;


import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.education.entity.base.BaseEntity;

import java.util.Date;

/**
 * @author zhj
 */
@TableName(value = "b_user")
public class UserEntity extends BaseEntity {

    /**
     * 过期时间
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date expired;
    /**
     * 是否锁定（0：未锁定，1：已锁定）
     */
    private Integer locked;
    /**
     * 密码
     */
    private String password;
    /**
     * 用户名
     */
    private String username;
    /**
     * 是否启用（0:启用，1：未启用）
     */
    private Integer enabled;
    /**
     * 是否过期（0:未过期，1：过期）
     */
    private Integer isExpired;

    /**
     * 用户真正的名称
     */
    private String realName;
    /**
     * 头像
     */
    private String avatar;

    public UserEntity() {
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    public Integer getLocked() {
        return locked;
    }

    public void setLocked(Integer locked) {
        this.locked = locked;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Integer getIsExpired() {
        return isExpired;
    }

    public void setIsExpired(Integer isExpired) {
        this.isExpired = isExpired;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}