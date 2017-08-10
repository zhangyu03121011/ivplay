package com.mm.dev.entity.user;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.mm.dev.entity.BaseEntity;

/**
 * @ClassName: UserRecommend 
 * @Description: 推荐记录
 * @author zhangxy
 * @date 2017年8月9日 下午5:10:17
 */
@Entity
@Table(name = "t_recommend")
public class UserRecommend extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	/**
	 * 推荐人openid
	 */
	private String refOpenid;
	
	/**
	 * 被推荐人openid
	 */
	private String refedOpenid;

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
}
