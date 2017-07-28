package com.common.base.model.base;

import java.io.Serializable;
import java.util.Date;

import com.common.base.annotation.Primary;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * BaseVO基础实现类
 * 
 * @author: HuiJunLuo
 * @date:2015年12月28日
 * @Copyright:Copyright (c) xxxx有限公司 2014 ~ 2015 版权所有
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseModel implements Serializable {

	private static final long serialVersionUID = 4962543125725269908L;

	/**
	 * 主键ID
	 */
	@Primary
	private String id;

	/**
	 * 系统ID
	 */
	private String appId;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 创建人
	 */
	private String createBy;

	/**
	 * 更新时间
	 */
	private Date updateTime;

	/**
	 * 更新人
	 */
	private String updateBy;

	/**
	 * 是否有效 0-无效 1-有效
	 */
	private String isavailable;

	/**
	 * 描述
	 */
	private String descr;

	/**
	 * 状态
	 */
	private String state;

	/**
	 * 标志，用于判断
	 */
	@JsonIgnore
	private boolean flag;

	/**
	 * 标志，用于判断
	 */
	@JsonIgnore
	private boolean validFlag;

	/**
	 * SessionID
	 */
	@JsonIgnore
	private String sessionId;

	/**
	 * token安全认证
	 */
	private String token;

	/**
	 * 是否查询当前用户（1-是，0-否）
	 */
	private String currUser;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public boolean isValidFlag() {
		return validFlag;
	}

	public void setValidFlag(boolean validFlag) {
		this.validFlag = validFlag;
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

	public String getIsavailable() {
		return isavailable;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state == null ? null : state.trim();
	}

	public void setIsavailable(String isavailable) {
		this.isavailable = isavailable == null ? null : isavailable.trim();
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy == null ? null : createBy.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy == null ? null : updateBy.trim();
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr == null ? null : descr.trim();
	}

	public String getCurrUser() {
		return currUser;
	}

	public void setCurrUser(String currUser) {
		this.currUser = currUser;
	}

	@Override
	public String toString() {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.toString();
	}

}
