package com.education.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
     * 文章标题
     */
    private String title;

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
     * 文章id
     */
    private String articleId;

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

    /**
     * 回复人名称
     */
    private String applyParentName;

    /**
     * 评论父id
     */
    private String parentId;
    /**
     * 子评论
     */
    private List<CommentVo> replyComments = new ArrayList<>();

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

    public List<CommentVo> getReplyComments() {
        return replyComments;
    }

    public void setReplyComments(List<CommentVo> replyComments) {
        this.replyComments = replyComments;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getApplyParentName() {
        return applyParentName;
    }

    public void setApplyParentName(String applyParentName) {
        this.applyParentName = applyParentName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
