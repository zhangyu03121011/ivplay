package com.mm.dev.service.impl.user;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mm.dev.dao.jpa.user.UserRecommendDao;
import com.mm.dev.dao.mapper.user.UserRecommendMapper;
import com.mm.dev.entity.user.UserRecommend;
import com.mm.dev.service.user.IUserRecommendService;

/**
 * @ClassName: UserRecommendServiceImpl.java 
 * @Description: 用户推荐业务层逻辑
 * @author zhangxy
 * @date 2017年8月4日 上午9:32:58
 */
@Service
public class UserRecommendServiceImpl implements IUserRecommendService {
	
	Logger logger = LoggerFactory.getLogger(UserRecommendServiceImpl.class);
	
	@Autowired
	private UserRecommendDao userRecommendDao;
	
	@Autowired
	private UserRecommendMapper userRecommendMapper;

	/**
	 * @Description: 保存推荐信息
	 * @Datatime 2017年8月5日 下午6:19:51 
	 * @return User    返回类型
	 */
	public void save(UserRecommend userRecommend) throws Exception {
		userRecommendDao.saveAndFlush(userRecommend);
	}

	/**
     * @Description: 分页查询我的推荐列表
     * @Datatime 2017年8月5日 下午6:15:42 
     * @return Page<User>    返回类型
     */
	public List<Map<String, String>> queryAllByOpenIdAndDelFlag(String refOpenid, String delFlag,Pageable pageable) throws Exception {
		return userRecommendMapper.queryAllByOpenIdAndDelFlag(refOpenid, delFlag, pageable);
	}

  

  
}
