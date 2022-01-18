package com.education.entity.subject;

import com.baomidou.mybatisplus.annotation.*;
import com.education.entity.base.BaseEntity;

import javax.validation.constraints.NotBlank;


@TableName(value = "b_subject")
public class SubjectEntity extends BaseEntity {
    /**
     * 类别名称
     */
    @NotBlank(message = "课程名称不能为空",groups = {update.class})
    private String title;
    /**
     * 父ID
     */
    private String parentId;
    /**
     * 排序字段
     */
    private Integer sort;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public interface update{}

}
