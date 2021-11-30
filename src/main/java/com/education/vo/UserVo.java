package com.education.vo;

import java.util.Date;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-11-24-20-32
 */
public class UserVo {
    private String id;
    /**
     * 过期时间
     */
    private Date expired;
    /**
     * 是否锁定（0：未锁定，1：已锁定）
     */
    private Integer locked;
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
     * 用户角色
     */
    private String roleId;

    /**
     * 角色名称
     */
    private String roleName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
