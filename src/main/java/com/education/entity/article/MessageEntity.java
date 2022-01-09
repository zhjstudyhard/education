package com.education.entity.article;

import com.baomidou.mybatisplus.annotation.TableName;
import com.education.entity.base.BaseEntity;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2022-01-08-16-59
 */
@TableName(value = "b_message")
public class MessageEntity extends BaseEntity {
    /**
     * 消息发送人id
     */
    private String fromUserId;

    /**
     * 消息接收人id
     */
    private String toUserId;

    /**
     * 消息内容
     */
    private String content;
    /**
     * 消息关联的文章或则视频id
     */
    private String targetId;
    /**
     * 关联的类型（0:文章，1:视频）
     */
    private Integer targetType;
    /**
     * 消息类型(用数据字典维护)
     */
    private String type;
    /**
     * 消息已读或未读（0：未读，1:已读）
     */
    private Integer status = 0;
    /**
     * 父评论id
     */
    private String parentCommentId;

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(String parentCommentId) {
        this.parentCommentId = parentCommentId;
    }
}
