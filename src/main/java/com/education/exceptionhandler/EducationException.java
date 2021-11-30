package com.education.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-07-31-22-04
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class EducationException extends RuntimeException{
    /**
     * 异常状态码
     */
    private Integer code;
    /**
     * 自定义异常信息
     */
    private String msg;
}
