package com.mm.dev.dao.jpa.user;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mm.dev.entity.user.UserInOutDetail;

/**
 * @Description: UserInOutDao 收支明细
 * @author Jacky
 * @date 2017年8月6日 下午9:45:39
 */
@Repository
public interface UserInOutDetailDao extends JpaRepository<UserInOutDetail, String> {
	/**
     * @Description: 分页查询我的提现列表
     * @Datatime 2017年8月5日 下午6:15:42 
     * @return Page<User>    返回类型
     */
	List<Map<String, String>> findAllByOpenIdAndDelFlag(String openId,String delFlag,Pageable pageable) throws Exception;
}
