package com.mm.dev.dao.mapper.user;

import java.util.Map;


/**
 * @Description: userMapper
 * @author Jacky
 * @date 2017年8月4日 下午10:01:26
 */
public interface UserFilesMapper {

	/**
	 * @Description: 根据ID删除
	 * @Datatime 2017年8月7日 下午9:55:50 
	 * @return void    返回类型
	 */
	public void deleteById(String id) throws Exception;
	
	/**
	 * @Description: 根据ID查询模糊图片信息
	 * @Datatime 2017年8月7日 下午9:55:50 
	 * @return void    返回类型
	 */
	public Map<String, String> queryBlurInfoById(String id) throws Exception;
}
