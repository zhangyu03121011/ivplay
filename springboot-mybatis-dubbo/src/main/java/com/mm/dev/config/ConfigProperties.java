package com.mm.dev.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "PLAY")
public class ConfigProperties {
	
	@Value("${PLAY.WECHAT.APPID}")
	private String APPID;
	
	@Value("${PLAY.WECHAT.APPKEY}")
	private String APPSECRET;
	
	@Value("${PLAY.UPLOAD.IMAGE.PATH}")
	private String imageUrl;
	
	@Value("${PLAY.WECHAT.HOST}")
	private String hostPath;
	
	@Value("${PLAY.WECHAT.MCHID}")
	private String mchId;
	
	/**
	 * 模糊图片宽度
	 */
	@Value(value="${PLAY.BLURIMAGE.WIDTH}")
	private int width;
	
	/**
	 * 模糊图片高度
	 */
	@Value(value="${PLAY.BLURIMAGE.HEIGHT}")
	private int height;
	
	/**
	 * 二维码图片文件前缀
	 */
	@Value(value="${PLAY.UPLOAD.IMAGE.QRCODE.PREFIX}")
	private String qrcodePrefix;
	
	/**
	 * 模糊图片文件前缀
	 */
	@Value(value="${PLAY.UPLOAD.IMAGE.BLUR.PREFIX}")
	private String blurPrefix;
	

	public String getAPPID() {
		return APPID;
	}

	public void setAPPID(String aPPID) {
		APPID = aPPID;
	}

	public String getAPPSECRET() {
		return APPSECRET;
	}

	public void setAPPSECRET(String aPPSECRET) {
		APPSECRET = aPPSECRET;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getHostPath() {
		return hostPath;
	}

	public void setHostPath(String hostPath) {
		this.hostPath = hostPath;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getQrcodePrefix() {
		return qrcodePrefix;
	}

	public void setQrcodePrefix(String qrcodePrefix) {
		this.qrcodePrefix = qrcodePrefix;
	}

	public String getBlurPrefix() {
		return blurPrefix;
	}

	public void setBlurPrefix(String blurPrefix) {
		this.blurPrefix = blurPrefix;
	}
}
