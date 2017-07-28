package com.common.base.vo.log;

import java.io.Serializable;
import java.util.Date;

/**
 * 记录所有异常信息
 * 
 * @author: HuiJunLuo
 * @date:2016年3月2日
 * @Copyright:Copyright (c) xxxx有限公司 2014 ~ 2015 版权所有
 */
public class LogExceptionVo implements Serializable {

    private static final long serialVersionUID = -3191750350888635967L;

    /**
     * 主键ID
     */
    private String id;

    /**
     * 系统名称
     */
    private String appName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 完整URL
     */
    private String fullUrl;

    /**
     * 访问URL
     */
    private String reqUrl;

    /**
     * 访问URI
     */
    private String reqUri;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * class名称
     */
    private String className;

    /**
     * 执行class名称
     */
    private String execClassName;

    /**
     * 异常消息
     */
    private String exceptionMsg;

    /**
     * 异常原因
     */
    private String exceptionCause;

    /**
     * 异常名称
     */
    private String exceptionName;

    /**
     * 异常StackTraceElement
     */
    private String exceptionStackTrace;

    /**
     * SessionID
     */
    private String sessionId;

    /**
     * token安全认证
     */
    private String token;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    public String getReqUrl() {
        return reqUrl;
    }

    public void setReqUrl(String reqUrl) {
        this.reqUrl = reqUrl;
    }

    public String getReqUri() {
        return reqUri;
    }

    public void setReqUri(String reqUri) {
        this.reqUri = reqUri;
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

    public String getExecClassName() {
        return execClassName;
    }

    public void setExecClassName(String execClassName) {
        this.execClassName = execClassName;
    }

    public String getExceptionMsg() {
        return exceptionMsg;
    }

    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExceptionCause() {
        return exceptionCause;
    }

    public void setExceptionCause(String exceptionCause) {
        this.exceptionCause = exceptionCause;
    }

    public String getExceptionName() {
        return exceptionName;
    }

    public void setExceptionName(String exceptionName) {
        this.exceptionName = exceptionName;
    }

    public String getExceptionStackTrace() {
        return exceptionStackTrace;
    }

    public void setExceptionStackTrace(String exceptionStackTrace) {
        this.exceptionStackTrace = exceptionStackTrace;
    }

}
