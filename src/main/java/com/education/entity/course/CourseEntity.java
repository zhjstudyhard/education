package com.education.entity.course;


import com.baomidou.mybatisplus.annotation.TableName;
import com.education.entity.base.BaseEntity;


/**
 * @author jubai
 */
@TableName(value = "b_course")
public class CourseEntity extends BaseEntity {

    /**
     * 课程讲师ID
     */
    private String teacherId;
    /**
     * 课程专业ID
     */
    private String subjectId;
    /**
     * 一级分类ID
     */
    private String subjectParentId;
    /**
     * 课程标题
     */
    private String title;
    /**
     * 总课时
     */
    private Integer lessonNum;
    /**
     * 课程封面图片路径
     */
    private String cover;

    /**
     * 浏览数量
     */
    private Long viewCount;
    /**
     * 课程状态 Draft未发布  Normal已发布
     */
    private String status;

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectParentId() {
        return subjectParentId;
    }

    public void setSubjectParentId(String subjectParentId) {
        this.subjectParentId = subjectParentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Integer getLessonNum() {
        return lessonNum;
    }

    public void setLessonNum(Integer lessonNum) {
        this.lessonNum = lessonNum;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
