package com.education.dto.course;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2022-01-20-18-22
 */
public class ChapterDto {
    private String id;
    /**
     * 课程ID
     */
    private String courseId;
    /**
     * 章节名称
     */
    @NotBlank(message = "章节名称不能为空")
    private String title;
    /**
     * 显示排序
     */
    @NotNull(message = "请选择排序")
    private Integer sort;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
