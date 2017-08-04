package com.mm.dev.service.user;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mm.dev.entity.user.User;

/**
 * Created by Lipengfei on 2015/6/26.
 */
public interface IUserService {

    User getUser(Long id) throws Exception;

    Page<User> getAll(Pageable pageable) throws Exception;

    List<User> getUserList() throws Exception;

    Page<User> getUserAll(Pageable pageable) throws Exception;

    void save() throws Exception;

    void saveUser() throws Exception;
    
    
    /**
	 * 关注保存用户信息
	 * @param toUserName
	 */
	public void weixinRegister(String openId,int attention);
	
	/**
	 * 取消关注保存用户信息
	 * @param toUserName
	 */
	public void unSubscribe(String openId);

}
