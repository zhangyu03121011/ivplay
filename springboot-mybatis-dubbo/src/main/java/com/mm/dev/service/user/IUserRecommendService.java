package com.mm.dev.service.user;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.mm.dev.entity.user.UserRecommend;

/**
 * @Description: IUserRecommendService
 * @author Jacky
 * @date 2017年8月4日 下午10:04:02
 */
public interface IUserRecommendService {
	
	/**
	 * @Description: 保存推荐信息
	 * @Datatime 2017年8月5日 下午6:19:51 
	 * @return User    返回类型
	 */
	void save(UserRecommend userRecommend) throws Exception;
    
    /**
     * @Description: 分页查询我的推荐列表
     * @Datatime 2017年8月5日 下午6:15:42 
     * @return Page<User>    返回类型
     */
	List<Map<String, String>> queryAllByOpenIdAndDelFlag(String openId,String delFlag,Pageable pageable) throws Exception;
}
