package com.mm.dev.dao.jpa.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mm.dev.entity.user.UserRecommend;

/**
 * @Description: UserRecommendDao
 * @author Jacky
 * @date 2017年8月6日 下午9:45:39
 */
@Repository
public interface UserRecommendDao extends JpaRepository<UserRecommend, String> {

	
	/**
	 * @Description: 根据opoenId分页查询我的推荐人列表
	 * @Datatime 2017年8月6日 下午10:57:27 
	 * @return List<UserFiles>    返回类型
	 */
	public Page<UserRecommend> findAllByRefOpenidAndDelFlag(String refOpenid,String delFlag,Pageable pageable) throws Exception;
	
}
