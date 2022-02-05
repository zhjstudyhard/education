package com.education.common;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2022-02-04-17-27
 */
public enum LogTypeNum {
    OTHER(0, "其它"),
    ADD(1, "新增"),
    UPDATE(2, "修改"),
    DEL(3, "删除"),
    IMPORT(6, "导入"),
    LOGIN(8, "登录"),
    LOGOUT(9, "登出");

    /**
     * 日志类型
     */
    private Integer logType;

    /**
     * 日志类型描述
     */
    private String logDesc;

    //根据key获取对象
    public static LogTypeNum getLogTypeByKey(Integer logType){
        for (LogTypeNum logTypeNum : values()) {
            if (logTypeNum.getLogType().equals(logType)){
                return logTypeNum;
            }
        }
        return null;
    }


    public Integer getLogType() {
        return logType;
    }

    public void setLogType(Integer logType) {
        this.logType = logType;
    }

    public String getLogDesc() {
        return logDesc;
    }

    public void setLogDesc(String logDesc) {
        this.logDesc = logDesc;
    }

    LogTypeNum(Integer logType, String logDesc) {
        this.logType = logType;
        this.logDesc = logDesc;
    }
}
