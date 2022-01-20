package com.education.entity.course;

import com.baomidou.mybatisplus.annotation.TableName;
import com.education.entity.base.BaseEntity;

/**
 * @author jubai
 */
@TableName(value = "b_chapter")
public class ChapterEntity extends BaseEntity {

    /**
     * 课程ID
     */
    private String courseId;
    /**
     * 章节名称
     */
    private String title;
    /**
     * 显示排序
     */
    private Integer sort;

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
