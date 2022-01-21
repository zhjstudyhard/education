package com.education.vo;

import com.education.entity.course.VideoEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 86181
 */
public class ChapterVo {

    private String id;

    private String title;

    //表示小节
    private List<VideoEntity> children = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<VideoEntity> getChildren() {
        return children;
    }

    public void setChildren(List<VideoEntity> children) {
        this.children = children;
    }
}
