package com.mm.dev.entity.user;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Created by Lipengfei on 2015/6/26.
 */
@Entity
@Table(name = "user")
public class User implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String passWord;
    
    private String openId;
    
    private String phone;
    
    private String sex;
    
    /**
     * 是否已关注（0:未 1:已）
     */
    private int attenation;
    
    /**
     * 是否取消关注（ 0:未 1:已）
     */
    private int unAttenation;
    
    private String headimgurl;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    

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

	public int getAttenation() {
		return attenation;
	}

	public void setAttenation(int attenation) {
		this.attenation = attenation;
	}

	public int getUnAttenation() {
		return unAttenation;
	}

	public void setUnAttenation(int unAttenation) {
		this.unAttenation = unAttenation;
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
}
