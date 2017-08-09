package com.mm.dev.entity.user;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.mm.dev.entity.BaseEntity;

/**
 * @Description:用户实体类
 * @author Jacky
 * @date 2017年8月4日 下午7:21:45
 */
@Entity
@Table(name = "t_user")
public class User extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 微信昵称
	 */
	private String nickName;
	
	/**
	 * 用户登录名
	 */
    private String userName;
    
    /**
     * 登录密码
     */
    private String passWord;
    
    /**
     * openId
     */
    private String openId;
    
    /**
     * 手机号码
     */
    private String phone;
    
    /**
     * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
     */
    private String sex;
    
    /**
     * 最后登录ip地址
     */
    private String lastLoginIpAddress;
    
    /**
     * 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
     */
    private String attenation;
    
    /**
     * 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
     */
    private String attenationTime;
    
    /**
     * 微信端用户的语言，简体中文为zh_CN
     */
    private String language;
    
    /**
     * 是否取消关注（ 0:未 1:已）
     */
    private String unAttenation;
    
    /**
     * 国家，如中国为CN
     */
    private String country;
    
    /**
     * 用户个人资料填写的省份
     */
    private String province;
    
    /**
     * 普通用户个人资料填写的城市
     */
    private String city;
    
    /**
     * 微信头像URL
     */
    private String headimgurl;
    
    @Transient
    private List<UserFiles> userFilesList;
    
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getLastLoginIpAddress() {
		return lastLoginIpAddress;
	}

	public void setLastLoginIpAddress(String lastLoginIpAddress) {
		this.lastLoginIpAddress = lastLoginIpAddress;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getAttenation() {
		return attenation;
	}

	public void setAttenation(String attenation) {
		this.attenation = attenation;
	}

	public String getUnAttenation() {
		return unAttenation;
	}

	public void setUnAttenation(String unAttenation) {
		this.unAttenation = unAttenation;
	}

	public String getAttenationTime() {
		return attenationTime;
	}

	public void setAttenationTime(String attenationTime) {
		this.attenationTime = attenationTime;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public List<UserFiles> getUserFilesList() {
		return userFilesList;
	}

	public void setUserFilesList(List<UserFiles> userFilesList) {
		this.userFilesList = userFilesList;
	}
}
