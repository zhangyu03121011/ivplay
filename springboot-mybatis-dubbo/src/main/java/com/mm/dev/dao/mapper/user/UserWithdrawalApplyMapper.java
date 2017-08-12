package com.mm.dev.dao.mapper.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

/**
 * @Description: UserRecommendMapper
 * @author Jacky
 * @date 2017年8月4日 下午10:01:26
 */
public interface UserWithdrawalApplyMapper {

	public List<Map<String, String>> queryAllByApplyOpenIdAndDelFlag(@Param("openId")String refOpenid, @Param("delFlag")String delFlag,Pageable pageable) throws Exception;
   	
}
