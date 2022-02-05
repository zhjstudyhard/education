package com.education.dto.course;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author jubai
 */
public class CourseDto {
    /**
     * 课程ID
     */
    private String id;
    /**
     * 当前页
     */
    private Integer currentPage = 1;
    /**
     * 每页显示的数量
     */
    private Integer pageSize = 10;
    /**
     * 课程讲师ID
     */
    @NotBlank(message = "请选择讲师")
    private String teacherId;
    /**
     * 二级分类ID
     */
    @NotBlank(message = "请选择二级分类")
    private String subjectId;
    /**
     * 一级分类级ID
     */
    @NotBlank(message = "请选择一级分类")
    private String subjectParentId;
    /**
     * 课程标题
     */
    @NotBlank(message = "请填写课程标题")
    private String title;
    /**
     * 总课时
     */
    @NotNull(message = "请填写总课时")
    private Integer lessonNum;

    /**
     * 课程封面图片路径
     */
    @NotBlank(message = "请上传课程封面图片")
    private String cover;
    /**
     * 课程简介
     */
    @NotBlank(message = "请填写课程简介")
    private String description;
    /**
     * 课程发布状态
     */
    private String status;

    /**
     * 作者id
     */

    private String userId;

    /**
     * 是否后台请求（0:否，1是）
     */
    private Integer isAdmin;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }
}
