package com.education.entity.course;

import com.baomidou.mybatisplus.annotation.TableName;
import com.education.entity.base.BaseEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * @author 橘白
 */
@TableName(value = "b_video")
public class VideoEntity extends BaseEntity {

    /**
     * 课程ID
     */
    private String courseId;

    /**
     * 章节ID
     */
    private String chapterId;
    /**
     * 节点名称
     */
    @NotBlank(message = "标题不能为空")
    private String title;
    /**
     * 云端视频资源
     */
    private String videoSourceId;
    /**
     * 原始文件名称
     */
    private String videoOriginalName;

    /**
     * 排序字段
     */
    @NotEmpty(message = "请选择排序")
    private Integer sort;
    /**
     * 播放次数
     */
    private Long playCount;
    /**
     * 视频时长（秒）
     */
    private Float duration;
    /**
     * Empty未上传 Transcoding转码中  Normal正常
     */
    private String status;
    /**
     * 视频源文件大小（字节）
     */
    private Long size;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoSourceId() {
        return videoSourceId;
    }

    public void setVideoSourceId(String videoSourceId) {
        this.videoSourceId = videoSourceId;
    }

    public String getVideoOriginalName() {
        return videoOriginalName;
    }

    public void setVideoOriginalName(String videoOriginalName) {
        this.videoOriginalName = videoOriginalName;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getPlayCount() {
        return playCount;
    }

    public void setPlayCount(Long playCount) {
        this.playCount = playCount;
    }

    public Float getDuration() {
        return duration;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}
