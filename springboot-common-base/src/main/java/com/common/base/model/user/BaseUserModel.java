package com.common.base.model.user;

import com.common.base.model.base.BaseModel;

/**
 * 用户基本信息Model
 * 
 * @author: HuiJunLuo
 * @date:2016年1月11日
 * @Copyright:Copyright (c) xxxx有限公司 2014 ~ 2015 版权所有
 */
public class BaseUserModel extends BaseModel {

    private static final long serialVersionUID = 629831372823938004L;

    /**
     * IDcode
     */
    private String idCode;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 别名
     */
    private String aliasName;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户类别：1-用户，2-企业
     */
    private String userType;

    /**
     * 检验码
     */
    private String randomCode;

    /**
     * 头像
     */
    private String headPhotoUrl;

    /**
     * 来源类别：1-Android，2-IOS，3-Web，4-微信，5-其它
     */
    private String sourceType;

    /**
     * 是否是管理员
     */
    private boolean adminFlag;

    public boolean isAdminFlag() {
        return adminFlag;
    }

    public void setAdminFlag(boolean adminFlag) {
        this.adminFlag = adminFlag;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode == null ? null : idCode.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType == null ? null : userType.trim();
    }

    public String getRandomCode() {
        return randomCode;
    }

    public void setRandomCode(String randomCode) {
        this.randomCode = randomCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHeadPhotoUrl() {
        return headPhotoUrl;
    }

    public void setHeadPhotoUrl(String headPhotoUrl) {
        this.headPhotoUrl = headPhotoUrl;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

}