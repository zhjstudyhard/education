package com.education.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.education.entity.base.BaseEntity;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-11-22-13-40
 */
@TableName(value = "b_dictionary")
public class DictionaryEntity extends BaseEntity {
    /**
     * 字典编码
     */
    private String dictionaryCode;

    /**
     * 字典内容
     */
    private String dictionaryValue;

    /**
     * 字典类型
     */
    private String dictionaryType;

    public String getDictionaryCode() {
        return dictionaryCode;
    }

    public void setDictionaryCode(String dictionaryCode) {
        this.dictionaryCode = dictionaryCode;
    }

    public String getDictionaryValue() {
        return dictionaryValue;
    }

    public void setDictionaryValue(String dictionaryValue) {
        this.dictionaryValue = dictionaryValue;
    }

    public String getDictionaryType() {
        return dictionaryType;
    }

    public void setDictionaryType(String dictionaryType) {
        this.dictionaryType = dictionaryType;
    }
}
