package com.education.vo;

import com.education.entity.subject.SubjectEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2022-01-18-18-34
 */
public class SubjectVo {
    /**
     * 主键id
     */
    private String id;
    /**
     * 分类名称
     */
    private String title;
    /**
     * 添加时间
     */
    private Date gmtCreate;

    /**
     * 一个一级分类有多个二级分类
     */
    private List<SubjectEntity> children = new ArrayList<>();

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

    public List<SubjectEntity> getChildren() {
        return children;
    }

    public void setChildren(List<SubjectEntity> children) {
        this.children = children;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}
