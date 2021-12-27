package com.education.vo;

import java.util.Date;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-12-27-21-48
 */
public class CommentVo {
    /**
     * 主键
     */
    private String id;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论时间
     */
    private Date gmtCreate;

    /**
     * 评论人id
     */
    private String userId;

    /**
     * 是否博主自己发布的评论（0：否，1：是）
     */
    private Integer articleUser;
    /**
     * 评论人头像
     */
    private String avatar;
    /**
     * 评论人名称
     */
    private String username;

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

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getArticleUser() {
        return articleUser;
    }

    public void setArticleUser(Integer articleUser) {
        this.articleUser = articleUser;
    }
}
