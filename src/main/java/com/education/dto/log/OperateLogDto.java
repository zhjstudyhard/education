package com.education.dto.log;

import java.util.Date;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2022-02-05-16-06
 */
public class OperateLogDto {
    private String id;
    /**
     * 当前页
     */
    private Integer currentPage = 1;
    /**
     * 每页显示数量
     */
    private Integer pageSize = 10;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 模块名称
     */
    private String model;

    /**
     * 日志类型
     */
    private Integer logType;
    /**
     * 日志类型中文名
     */
    private String logTypeName;

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
    /**
     * 接口调用结果中文(0:调用失败，1：调用成功)
     */
    private String resultName;

    /**
     * 操作结束时间
     */
    private Date queryEndTime;
    /**
     * 操作开始时间
     */
    private Date queryStartTime;
    /**
     * 是否登录日志（0：false,1:是）
     */
    private Integer isLogin = 0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

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

    public String getLogTypeName() {
        return logTypeName;
    }

    public void setLogTypeName(String logTypeName) {
        this.logTypeName = logTypeName;
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

    public String getResultName() {
        return resultName;
    }

    public void setResultName(String resultName) {
        this.resultName = resultName;
    }

    public Date getQueryEndTime() {
        return queryEndTime;
    }

    public void setQueryEndTime(Date queryEndTime) {
        this.queryEndTime = queryEndTime;
    }

    public Date getQueryStartTime() {
        return queryStartTime;
    }

    public void setQueryStartTime(Date queryStartTime) {
        this.queryStartTime = queryStartTime;
    }

    public Integer getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(Integer isLogin) {
        this.isLogin = isLogin;
    }
}
