package com.education.dto.system;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-11-22-14-00
 */
@Data
public class DictionaryDto {
    /**
     * 主键id
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
     * 字典编码
     */
    @NotBlank(message = "字典编码不能为空")
    private String dictionaryCode;

    /**
     * 字典内容
     */
    @NotBlank(message = "字典内容不能为空")
    private String dictionaryValue;

    /**
     * 字典类型
     */
    @NotBlank(message = "字典类型不能为空")
    private String dictionaryType;
}
