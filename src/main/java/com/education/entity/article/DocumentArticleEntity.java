package com.education.entity.article;


/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2022-01-11-18-36
 */
public class DocumentArticleEntity {
    /**
     * 主键
     */
    private String id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 文章内容描述
     */
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
}
