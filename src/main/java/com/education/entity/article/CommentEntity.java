package com.education.entity.article;

import com.baomidou.mybatisplus.annotation.TableName;
import com.education.entity.base.BaseEntity;

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
     * 评论父id
     */
    private String parentId;

    /**
     * 发表评论id
     */
    private String userId;

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
}
