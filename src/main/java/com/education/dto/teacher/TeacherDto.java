package com.education.dto.teacher;

import javax.validation.constraints.NotBlank;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2022-01-17-19-45
 */
public class TeacherDto {

    private String id;
    /**
     * 讲师姓名
     */
    @NotBlank(message = "讲师姓名不能为空")
    private String name;

    /**
     * 讲师简介
     */
    @NotBlank(message = "讲师简介不能为空")
    private String intro;

    /**
     * 讲师资历,一句话说明讲师
     */
    @NotBlank(message = "讲师资历不能为空")
    private String career;

    /**
     * "头衔 高级讲师 首席讲师"
     */
    @NotBlank(message = "讲师头衔不能为空")
    private String level;

    /**
     * "讲师头像"
     */
//    @NotBlank(message = "讲师头像不能为空",groups = {updateAvater.class})
    private String avatar;

    /**
     * "图片id"
     */
    private String fileId;
    /**
     * "排序"
     */
    private Integer sort;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public interface updateAvater{

    }
}
