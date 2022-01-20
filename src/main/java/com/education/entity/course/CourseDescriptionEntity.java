package com.education.entity.course;

import com.baomidou.mybatisplus.annotation.TableName;
import com.education.entity.base.BaseEntity;

/**
 * @author jubai
 */
@TableName(value = "b_course_description")
public class CourseDescriptionEntity extends BaseEntity {
    /**
     * 课程简介
     */
    private String description;
    /**
     * 课程关联id
     */
    private String courseId;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
