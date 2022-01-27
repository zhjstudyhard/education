package com.education.entity.article;

import com.baomidou.mybatisplus.annotation.TableName;
import com.education.entity.base.BaseEntity;

import javax.validation.constraints.NotBlank;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-12-26-19-05
 */
@TableName(value = "b_comment")
public class CommentEntity extends BaseEntity {
    /**
     * 评论内容
     */
    private String content;
    /**
     * 文章id
     */
    private String articleId;

    /**
     * 课程id
     */
    private String courseId;
    /**
     * 评论父id
     */
    private String parentId;

    /**
     * 一级评论id
     */
    private String firstParentId;

    /**
     * 发表评论用户id
     */
    private String userId;

    /**
     * 是否博主自己发布的评论（0：否，1：是）
     */
    private Integer articleUser;

    /**
     * 是否作者自己发布的评论（0：否，1：是）
     */
    private Integer courseUser;


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

    public Integer getArticleUser() {
        return articleUser;
    }

    public void setArticleUser(Integer articleUser) {
        this.articleUser = articleUser;
    }

    public String getFirstParentId() {
        return firstParentId;
    }

    public void setFirstParentId(String firstParentId) {
        this.firstParentId = firstParentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public Integer getCourseUser() {
        return courseUser;
    }

    public void setCourseUser(Integer courseUser) {
        this.courseUser = courseUser;
    }
}
