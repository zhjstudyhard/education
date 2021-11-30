package com.education.common;


/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-07-29-09-14
 */
public enum ResultCode {
    //成功状态码
    SUCCESS_CODE(20000),
    //失败状态码
    FAILER_CODE(20001);
    /**
     * 返回码
     */
    private Integer Code;

    /**
     * 枚举类构造方法只允许私有
     *
     * @param Code
     */
    private ResultCode(Integer Code) {
        this.Code = Code;
    }

    public Integer getCode() {
        return Code;
    }

}
