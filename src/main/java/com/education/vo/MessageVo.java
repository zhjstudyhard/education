package com.education.vo;

import java.util.Date;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2022-01-09-19-54
 */
public class MessageVo {
    /**
     * 主键id
     */
    private String id;

    /**
     * 消息发送人id
     */
    private String fromUserId;
    /**
     * 消息发送人名称
     */
    private String fromUserName;

    /**
     * 消息接收人id
     */
    private String toUserId;
    /**
     * 消息接收人名称
     */
    private String toUserName;
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
     * 父评论id
     */
    private String parentCommentId;
    /**
     * 父评论消息发送人id
     */
    private String parentFromUserId;

    /**
     * 父评论消息发送人名称
     */
    private String parentFromUserName;

    /**
     * 父评论消息接收人id
     */
    private String parentToUserId;
    /**
     * 父评论消息接收人名称
     */
    private String parentToUserName;
    /**
     * 夫评论消息内容
     */
    private String parentContent;
    /**
     * 创建消息时间
     */
    private Date gmtCreate;

    /**
     * 消息已读或未读（0：未读，1:已读）
     */
    private Integer status;
    /**
     * 头像
     */
    private String avatar;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
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

    public String getParentFromUserId() {
        return parentFromUserId;
    }

    public void setParentFromUserId(String parentFromUserId) {
        this.parentFromUserId = parentFromUserId;
    }

    public String getParentFromUserName() {
        return parentFromUserName;
    }

    public void setParentFromUserName(String parentFromUserName) {
        this.parentFromUserName = parentFromUserName;
    }

    public String getParentToUserId() {
        return parentToUserId;
    }

    public void setParentToUserId(String parentToUserId) {
        this.parentToUserId = parentToUserId;
    }

    public String getParentToUserName() {
        return parentToUserName;
    }

    public void setParentToUserName(String parentToUserName) {
        this.parentToUserName = parentToUserName;
    }

    public String getParentContent() {
        return parentContent;
    }

    public void setParentContent(String parentContent) {
        this.parentContent = parentContent;
    }

    public String getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(String parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
