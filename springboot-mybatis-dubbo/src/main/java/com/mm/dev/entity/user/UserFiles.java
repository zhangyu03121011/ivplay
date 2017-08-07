package com.mm.dev.entity.user;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.mm.dev.entity.BaseEntity;

/**
 * @Description: 用户文件实体类
 * @author Jacky
 * @date 2017年8月4日 下午7:16:40
 */
@Entity
@Table(name = "t_user_files")
public class UserFiles extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	/**
	 * openId
	 */
	private String openId;
	
	/**
	 * 文件全名
	 */
	private String fileNames;
	
	/**
	 * uuid生成唯一文件全名
	 */
	private String fileNewNames;
	
	/**
	 * 文件存储路径
	 */
	private String filePath;
	
	/**
	 * 文件后缀
	 */
	private String fileSuffic;
	
	/**
	 * 文件大小
	 */
	private String fileSize;
	
	/**
	 * 文件分类（1：图片 2：视频）
	 */
	private String fileCategory;
	
	/**
	 * 是否选择随机打赏（1： 是 2：否）
	 */
	private String random = "1";
	
	/**
	 * 打赏金额最大值
	 */
	private BigDecimal price;
	
	/**
	 * 打赏最小值
	 */
	private BigDecimal priceMin;
	
	private String title;
	
	/**
	 * 打赏图片模糊度
	 */
	private int blur;
	
	public String getFileNames() {
		return fileNames;
	}

	public void setFileNames(String fileNames) {
		this.fileNames = fileNames;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileCategory() {
		return fileCategory;
	}

	public void setFileCategory(String fileCategory) {
		this.fileCategory = fileCategory;
	}

	public String getFileSuffic() {
		return fileSuffic;
	}

	public void setFileSuffic(String fileSuffic) {
		this.fileSuffic = fileSuffic;
	}

	public String getFileNewNames() {
		return fileNewNames;
	}

	public void setFileNewNames(String fileNewNames) {
		this.fileNewNames = fileNewNames;
	}

	public String getRandom() {
		return random;
	}

	public void setRandom(String random) {
		this.random = random;
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

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public int getBlur() {
		return blur;
	}

	public void setBlur(int blur) {
		this.blur = blur;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
