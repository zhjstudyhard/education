package com.education.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-08-09-17-56
 */
public class SubjectData {
    @ExcelProperty(value = "一级分类",index = 0)
    private String oneSubjectName;
    @ExcelProperty(value = "二级分类",index = 1)
    private String twoSubjectName;

    public String getOneSubjectName() {
        return oneSubjectName;
    }

    public void setOneSubjectName(String oneSubjectName) {
        this.oneSubjectName = oneSubjectName;
    }

    public String getTwoSubjectName() {
        return twoSubjectName;
    }

    public void setTwoSubjectName(String twoSubjectName) {
        this.twoSubjectName = twoSubjectName;
    }
}
