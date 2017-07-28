package com.common.base.model.atta;

import com.common.base.annotation.Validate;
import com.common.base.model.base.BaseModel;

/**
 * 附件Model
 * 
 * @author: HuiJunLuo
 * @date:2016年1月21日
 * @Copyright:Copyright (c) xxxx有限公司 2014 ~ 2015 版权所有
 */
public class BaseAttaModel extends BaseModel {

	private static final long serialVersionUID = 7045675743925065934L;

	/**
	 * 源ID
	 */
	@Validate
	private String sourceId;

	/**
	 * 源类别
	 */
	private String sourceType;

	/**
	 * 文件名称
	 */
	@Validate
	private String fileName;

	/**
	 * 文件路径
	 */
	@Validate
	private String filePath;

	/**
	 * 截图路径
	 */
	private String cutFilePath;

	/**
	 * 文件大小
	 */
	@Validate
	private Long fileSize;

	/**
	 * 文件类型
	 */
	private String fileType;

	/**
	 * 文件扩展名
	 */
	@Validate
	private String fileExtend;

	/**
	 * 文件时长
	 */
	private long fileDuration;

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName == null ? null : fileName.trim();
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath == null ? null : filePath.trim();
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileExtend() {
		return fileExtend;
	}

	public void setFileExtend(String fileExtend) {
		this.fileExtend = fileExtend == null ? null : fileExtend.trim();
	}

	public long getFileDuration() {
		return fileDuration;
	}

	public void setFileDuration(long fileDuration) {
		this.fileDuration = fileDuration;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getCutFilePath() {
		return cutFilePath;
	}

	public void setCutFilePath(String cutFilePath) {
		this.cutFilePath = cutFilePath;
	}

}