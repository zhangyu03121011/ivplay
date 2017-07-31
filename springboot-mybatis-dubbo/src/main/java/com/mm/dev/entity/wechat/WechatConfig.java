package com.mm.dev.entity.wechat;
import java.io.Serializable;

/**
 * @Todo
 * @date 2016年6月14日
 * @author Ly
 */
public class WechatConfig implements Serializable{

	private static final long serialVersionUID = 1L;
	private String appId;
	private String timestamp;
	private String nonceStr;
	private String signature;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

}
