package com.mm.dev.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Description: 基础实体类
 * @author Jacky
 * @date 2017年8月4日 下午7:16:40
 */
@MappedSuperclass
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 4962543125725269908L;
	
	/**
	 * 主键ID
	 */
	@Id
	private String id;

	/**
	 * 系统ID（表示entity忽略）
	 */
	@Transient
	private String appId;

	/**
	 * 创建时间
	 */
	@Column
	private Date createTime;

	/**
	 * 创建人
	 */
	@Column
	private String createBy;

	/**
	 * 更新时间
	 */
	@Column
	private Date updateTime;

	/**
	 * 更新人
	 */
	@Column
	private String updateBy;

	/**
	 * 是否有效 0-无效 1-有效
	 */
	@Transient
	private String isavailable;

	/**
	 * 描述
	 */
	private String descr = "-1";

	/**
	 * 状态
	 */
	private String state = "1";

	/**
	 * 标志，用于判断
	 */
	@Transient
	private boolean flag;

	/**
	 * 标志，用于判断
	 */
	@Transient
	private boolean validFlag;

	/**
	 * SessionID
	 */
	@Transient
	private String sessionId;

	/**
	 * token安全认证
	 */
	@Transient
	private String token;

	/**
	 * 是否查询当前用户（1-是，0-否）
	 */
	@Transient
	private String currUser;
	
	/**
	 * 删除标记（1：否 2：是）
	 */
	private String delFlag = "1";

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

}
