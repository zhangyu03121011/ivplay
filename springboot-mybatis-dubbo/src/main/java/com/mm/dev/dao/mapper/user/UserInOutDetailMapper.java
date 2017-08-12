package com.mm.dev.dao.mapper.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

/**
 * @Description: UserInOutDetailMapper
 * @author Jacky
 * @date 2017年8月4日 下午10:01:26
 */
public interface UserInOutDetailMapper {

	public List<Map<String, String>> queryAllByOpenIdAndDelFlag(@Param("openId")String refOpenid,Pageable pageable) throws Exception;
   	
}
