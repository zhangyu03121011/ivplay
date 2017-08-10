package com.mm.dev.dao.mapper.user;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

/**
 * @Description: UserRecommendMapper
 * @author Jacky
 * @date 2017年8月4日 下午10:01:26
 */
public interface UserRecommendMapper {

	public List<Map<String, String>> findAllByOpenIdAndDelFlag(String refOpenid, String delFlag,Pageable pageable) throws Exception;
   	
}
