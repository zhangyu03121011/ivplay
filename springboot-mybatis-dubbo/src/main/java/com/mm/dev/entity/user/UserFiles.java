package com.mm.dev.entity.user;

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
	 * 用户ID
	 */
	private String userId;
	
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
	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

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
}
