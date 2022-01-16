package com.education.dto.article;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-12-15-09-54
 */
public class ArticleDto {
    private String id;

    /**
     * 当前页
     */
    private Integer currentPage = 1;
    /**
     * 每页显示数量
     */
    private Integer pageSize = 10;
    /**
     * 文章标题
     */
    @NotBlank(message = "标题不能为空")
    private String title;

    /**
     * 文章首图，用于随机文章展示
     */
    private String firstPicture;

    /**
     * 描述
     */
    @NotBlank(message = "摘要不能为空")
    private String description;

    /**
     * 文章正文
     */
    @NotBlank(message = "内容不能为空")
    private String content;


    /**
     * 浏览次数
     */
    private Integer views;

    /**
     * 文章字数
     */
    @NotNull(message = "文章字数不能为空")
    private Integer words;



    /**
     * 文章分类
     */
    @NotBlank(message = "文章分类不能为空")
    private String type;

    /**
     * 文章作者id
     */

    private String userId;

    /**
     * 博客状态(0:未启用，1：启用)
     */
    private Integer status;

    /**
     * 是否后台请求（0:否，1是）
     */
    private Integer isAdmin;
    /**
     * 前端穿的所有图片路径
     */
    private String filePaths;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getWords() {
        return words;
    }

    public void setWords(Integer words) {
        this.words = words;
    }

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

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getFilePaths() {
        return filePaths;
    }

    public void setFilePaths(String filePaths) {
        this.filePaths = filePaths;
    }
}
