package com.education.dto.system;


import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.util.Date;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-11-23-21-13
 */
public class UserDto {
    private String id;

    /**
     * 当前页
     */
    private Integer currentPage = 1;
    /**
     * 每页显示的数量
     */
    private Integer pageSize = 10;
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
    @NotBlank(message = "用户名不能为空")
    private String username;
    /**
     * 登录密码
     */
    private String password;
    /**
     * 是否启用（0:启用，1：未启用)
     */
    @NotNull(message = "请选择账号是否启用")
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
     * 角色id
     */
    @NotBlank(message = "角色不能为空")
    private String roleId;

    /**
     * 文件id
     */
    @NotBlank(message = "请上传文件",groups = {updateAvater.class})
    private String fileId;

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

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public interface updateAvater{

    }
}
