package com.mm.dev.dao.mapper.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import com.mm.dev.entity.user.User;

/**
 * @Description: userMapper
 * @author Jacky
 * @date 2017年8月4日 下午10:01:26
 */
public interface UserMapper {

    /**
   	 * 根据openId查询会员id
   	 * @param openId
   	 * @return
   	 * @throws Exception
   	 */
   	public User queryUserBaseInfoByopenId(@Param("openId") String openId) throws Exception;
   	
   	/**
	 * @Description: 根据openId获取用户账号信息
	 * @Datatime 2017年8月5日 下午3:16:51 
	 * @return void    返回类型
	 */
	public User queryUserBalanceInfoByOpenId(String openId) throws Exception;
   	
   	/**
   	 * @Description: 更新用户信息
   	 * @Datatime 2017年8月4日 下午10:02:10 
   	 * @return void    返回类型
   	 */
   	public void updateUserInfo(User user);
   	
	/**
   	 * @Description: 保存用户信息
   	 * @Datatime 2017年8月4日 下午10:02:10 
   	 * @return void    返回类型
   	 */
   	public void save(User user);
   	
   	/**
	 * @Description:朋友圈首页分页查询列表
	 * @Datatime 2017年8月6日 下午9:42:44 
	 * @return List<UserFiles>    返回类型
	 */
	public List<Map<String, String>> queryUserFilesList(@Param("pageable")Pageable pageable,@Param("openId") String openId) throws Exception;
}
