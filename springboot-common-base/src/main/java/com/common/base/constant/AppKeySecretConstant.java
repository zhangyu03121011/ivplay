package com.common.base.constant;

/**
 * 项目类型签名常量
 * 
 * @author: HuiJunLuo
 * @date:2016年1月9日
 * @Copyright:Copyright (c) xxxx有限公司 2014 ~ 2015 版权所有
 */
public enum AppKeySecretConstant {

    IOS("ios", "#oo!theeg#hiex@eeji%Qu6sha^ej0&Aet*a_9o@"), ANDROID("android",
            "~heo1i@eheib#oRai0$va%ezabeiZ^ohp_hik$"), WEB("web", "ua4$ahg%oo6&ie*q_u8aeQu@ahkae!SahK5ma("), WECHAT(
                    "wechat", "jare)e7*do_o5w$ae%Li@e7ie#ceipu!esiepe");

    /**
     * key
     */
    private String appKey;

    /**
     * 值
     */
    private String appSecret;

    // 构造方法
    private AppKeySecretConstant(String appKey, String appSecret) {
        this.appKey = appKey;
        this.appSecret = appSecret;
    }

    // 普通方法
    public static String getAppSecret(String appKey) {
        for (AppKeySecretConstant appKeySecretConstant : AppKeySecretConstant.values()) {
            if (appKeySecretConstant.getAppKey().equalsIgnoreCase(appKey)) {
                return appKeySecretConstant.getAppSecret();
            }
        }
        return null;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

}
