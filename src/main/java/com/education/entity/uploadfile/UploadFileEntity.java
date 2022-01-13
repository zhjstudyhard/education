package com.education.entity.uploadfile;

import com.baomidou.mybatisplus.annotation.TableName;
import com.education.entity.base.BaseEntity;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2022-01-13-17-42
 */
@TableName("b_uploadfile")
public class UploadFileEntity extends BaseEntity {
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 原生文件名
     */
    private String originFileName;
    /**
     * 文件后缀类型
     */
    private String fileSuffix;
    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 文件关联的id
     */
    private String relativeId;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getOriginFileName() {
        return originFileName;
    }

    public void setOriginFileName(String originFileName) {
        this.originFileName = originFileName;
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getRelativeId() {
        return relativeId;
    }

    public void setRelativeId(String relativeId) {
        this.relativeId = relativeId;
    }
}
