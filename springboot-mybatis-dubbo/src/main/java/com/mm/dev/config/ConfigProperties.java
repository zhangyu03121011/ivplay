package com.mm.dev.config;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "PLAY")
public class ConfigProperties {
	
	/**
	 * APPID
	 */
	@Value("${PLAY.WECHAT.APPID}")
	private String APPID;
	
	/**
	 * APPKEY
	 */
	@Value("${PLAY.WECHAT.APPKEY}")
	private String APPSECRET;
	
	/**
	 * 图片上传目的文件夹
	 */
	@Value("${PLAY.UPLOAD.IMAGE.PATH}")
	private String imageUrl;
	
	/**
	 * 图片上传最大大小
	 */
	@Value("${PLAY.UPLOAD.IMAGE.SIZE}")
	private int imageMaxSize;
	
	/**
	 * 服务器地址
	 */
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
	 * 模糊图片模糊度
	 */
	@Value(value="${PLAY.BLURIMAGE.BLUR}")
	private int blur;
	
	/**
	 * 模糊图片压缩比例（1最高质量 原图）
	 */
	@Value(value="${PLAY.BLURIMAGE.SCALE}")
	private Float scale;
	
	/**
	 * 模糊图片最大值
	 */
	@Value(value="${PLAY.BLURIMAGE.PRICE}")
	private BigDecimal price;
	
	/**
	 * 模糊图片最小值
	 */
	@Value(value="${PLAY.BLURIMAGE.PRICEMIN}")
	private BigDecimal priceMin;
	
	/**
	 * 模糊图片金额是否随机(1：是 2:否)
	 */
	@Value(value="${PLAY.BLURIMAGE.RANDOM}")
	private String random;
	
	/**
	 * 二维码图片文件后缀
	 */
	@Value(value="${PLAY.UPLOAD.IMAGE.QRCODE.SUFFIX}")
	private String qrcodeSuffic;
	
	/**
	 * 模糊图片文件后缀
	 */
	@Value(value="${PLAY.UPLOAD.IMAGE.BLUR.SUFFIX}")
	private String blurSuffix;
	

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

	public int getBlur() {
		return blur;
	}

	public void setBlur(int blur) {
		this.blur = blur;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getPriceMin() {
		return priceMin;
	}

	public void setPriceMin(BigDecimal priceMin) {
		this.priceMin = priceMin;
	}

	public String getRandom() {
		return random;
	}

	public void setRandom(String random) {
		this.random = random;
	}

	public String getQrcodeSuffic() {
		return qrcodeSuffic;
	}

	public void setQrcodeSuffic(String qrcodeSuffic) {
		this.qrcodeSuffic = qrcodeSuffic;
	}

	public String getBlurSuffix() {
		return blurSuffix;
	}

	public void setBlurSuffix(String blurSuffix) {
		this.blurSuffix = blurSuffix;
	}

	public int getImageMaxSize() {
		return imageMaxSize;
	}

	public void setImageMaxSize(int imageMaxSize) {
		this.imageMaxSize = imageMaxSize;
	}

	public Float getScale() {
		return scale;
	}

	public void setScale(Float scale) {
		this.scale = scale;
	}

}
