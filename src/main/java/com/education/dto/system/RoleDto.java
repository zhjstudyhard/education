package com.education.dto.system;

import javax.validation.constraints.NotBlank;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-11-23-17-27
 */
public class RoleDto {

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
     * 角色编码
     */
    @NotBlank(message = "角色编码为空")
    private String roleCode;
    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称为空")
    private String roleName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}
