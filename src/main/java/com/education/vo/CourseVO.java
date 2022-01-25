package com.education.vo;


import java.util.Date;
import java.util.List;

/**
 * @author jubai
 */
public class CourseVO {
    private String id;

    /**
     * 课程讲师ID
     */
    private String teacherId;
    /**
     * 课程讲师姓名
     */
    private String teacherName;
    /**
     * 课程讲师头像
     */
    private  String teacherAvatar;

    /**
     * 课程讲师头衔
     */
    private  String career;
    /**
     * 课程专业ID
     */
    private String subjectId;
    /**
     * 分类中文名
     */
    private String subjectName;
    /**
     * 一级分类ID
     */
    private String subjectParentId;
    /**
     * 一级分类中文名
     */
    private String subjectParentName;
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
    /**
     * 课程状态中文名Draft未发布  Normal已发布
     */
    private String statusName;
    /**
     * 课程描述
     */
    private String description;
    /**
     * 课程添加时间
     */
    private Date gmtCreate;
    /**
     * 课程章节和小节
     */
    private List<ChapterVo> chapterVos;

    public List<ChapterVo> getChapterVos() {
        return chapterVos;
    }

    public void setChapterVos(List<ChapterVo> chapterVos) {
        this.chapterVos = chapterVos;
    }

    public String getTeacherAvatar() {
        return teacherAvatar;
    }

    public void setTeacherAvatar(String teacherAvatar) {
        this.teacherAvatar = teacherAvatar;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectParentName() {
        return subjectParentName;
    }

    public void setSubjectParentName(String subjectParentName) {
        this.subjectParentName = subjectParentName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
