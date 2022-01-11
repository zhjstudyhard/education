package com.education.dto.article;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-12-27-20-26
 */
public class CommentDto {
    private Integer currentPage = 1;

    private Integer pageSize = 10;

    private String id;
    /**
     * 评论内容
     */
    @NotBlank(message = "评论内容不能为空")
    private String content;
    /**
     * 文章id
     */
    private String articleId;

    /**
     * 评论父id
     */
    private String parentId;
    /**
     * 一级评论的id
     */
    private String firstParentId;

    /**
     * 发表评论用户id
     */
    private String userId;

    /**
     * 关联的类型（0:文章，1:视频）
     */
    @NotNull(message = "关联类型不能为空")
    private Integer targetType;

    /**
     * 是否后台请求（0:否，1：是）
     */
    private Integer isAdmin;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getFirstParentId() {
        return firstParentId;
    }

    public void setFirstParentId(String firstParentId) {
        this.firstParentId = firstParentId;
    }

    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }
}
