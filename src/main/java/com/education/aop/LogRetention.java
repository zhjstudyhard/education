package com.education.aop;

import com.education.common.LogTypeNum;

import java.lang.annotation.*;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2022-02-04-17-40
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogRetention {
    /**
     * 模块
     * */
    String model() default "";

    /**
     * 操作类型
     * */
    LogTypeNum logType() default LogTypeNum.OTHER;
}
