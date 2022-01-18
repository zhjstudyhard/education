package com.education.vo;

import java.util.Date;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2022-01-18-15-32
 */
public class TeacherVo {
    /**
     * 主键id
     */
    private String id;
    /**
     * 添加时间
     */
    private Date gmtCreate;
    /**
     * 讲师姓名
     */
    private String name;

    /**
     * 讲师简介
     */
    private String intro;

    /**
     * 讲师资历,一句话说明讲师
     */
    private String career;

    /**
     * "头衔 高级讲师 首席讲师"
     */
    private String level;
    /**
     * "头衔 高级讲师 首席讲师"中文名
     */
    private String levelName;
    /**
     * "讲师头像"
     */
    private String avatar;
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

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }
}
