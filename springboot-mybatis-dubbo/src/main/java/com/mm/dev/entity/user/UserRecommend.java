package com.mm.dev.entity.user;

import java.util.Date;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mm.dev.entity.BaseEntity;

/**
 * @ClassName: UserRecommend 
 * @Description: 推荐记录
 * @author zhangxy
 * @date 2017年8月9日 下午5:10:17
 */
@Entity
public class UserRecommend extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	public static final String  effect_disable = "0"; 	//失效
	public static final String  effect_enable = "1";	//生效
	
	private String id;									//主键
	private String refOpenid;							//推荐人openid
	private String refedOpenid;							//被推荐人openid
	private String isEffect;							//记录是否生效
	private String createBy;

	private String createDate;
	private String updateBy;
	private Date updateDate;
	
	//非持久化参数
	private String name;								//微信昵称或用户名
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRefOpenid() {
		return refOpenid;
	}
	public void setRefOpenid(String refOpenid) {
		this.refOpenid = refOpenid;
	}
	public String getRefedOpenid() {
		return refedOpenid;
	}
	public void setRefedOpenid(String refedOpenid) {
		this.refedOpenid = refedOpenid;
	}
	public String getIsEffect() {
		return isEffect;
	}
	public void setIsEffect(String isEffect) {
		this.isEffect = isEffect;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
}
