package com.education.dto.article;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2022-01-09-19-48
 */
public class MessageDto {
    /**
     * 主键id
     */
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
     * 用户id
     */
    private String userId;

    /**
     * 是否后台管理请求（0:否，1：是）
     */
    private Integer isAdmin;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }
}
