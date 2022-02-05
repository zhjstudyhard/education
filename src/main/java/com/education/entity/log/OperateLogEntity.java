package com.education.entity.log;

import com.baomidou.mybatisplus.annotation.TableName;
import com.education.entity.base.BaseEntity;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2022-02-04-17-18
 */
@TableName(value = "b_operate_log")
public class OperateLogEntity extends BaseEntity {
    /**
     * 模块名称
     */
    private String model;

    /**
     * 日志类型
     */
    private Integer logType;

    /**
     * 请求url
     */
    private String url;

    /**
     * 请求方式（get,post,put,delete等）
     */
    private String method;

    /**
     * 请求方法名
     */
    private String methodName;


    /**
     * 请求类名称
     */
    private String className;

    /**
     * 请求参数
     */
    private String params;

    /**
     * 请求ip
     */
    private String ip;

    /**
     * 操作人id
     */
    private String userId;

    /**
     * 操作人名称
     */
    private String userName;
    /**
     * 接口调用时间
     */
    private Long resultTime;

    /**
     * 接口调用结果(0:调用失败，1：调用成功)
     */
    private Integer result;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getLogType() {
        return logType;
    }

    public void setLogType(Integer logType) {
        this.logType = logType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getResultTime() {
        return resultTime;
    }

    public void setResultTime(Long resultTime) {
        this.resultTime = resultTime;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }
}
