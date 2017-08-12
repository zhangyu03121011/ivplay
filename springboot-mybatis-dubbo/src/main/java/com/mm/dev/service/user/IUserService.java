package com.mm.dev.service.user;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mm.dev.entity.user.User;

/**
 * @Description: IuserService
 * @author Jacky
 * @date 2017年8月4日 下午10:04:02
 */
public interface IUserService {
	
	/**
	 * @Description: 根据ID查询
	 * @Datatime 2017年8月5日 下午6:19:51 
	 * @return User    返回类型
	 */
    User getUser(String id) throws Exception;
    
    /**
     * @Description: 分页查询列表
     * @Datatime 2017年8月5日 下午6:15:42 
     * @return Page<User>    返回类型
     */
    Page<User> getAll(Pageable pageable) throws Exception;

    /**
	 * 关注保存用户信息
	 * @param toUserName
	 */
	public void weixinRegister(HttpServletRequest request,String openId,String attention) throws Exception;
	
	/**
	 * 取消关注保存用户信息
	 * @param toUserName
	 */
	public void unSubscribe(String openId) throws Exception;
	
	/**
	 * 根据openId查询会员ID
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	public User queryUserBaseInfoByopenId(String openId) throws Exception;
	
	/**
	 * @Description:朋友圈首页分页查询列表
	 * @Datatime 2017年8月6日 下午9:42:44 
	 * @return List<UserFiles>    返回类型
	 */
	public List<Map<String, String>> queryUserFilesList(String openId,Pageable pageable) throws Exception;
	
}
