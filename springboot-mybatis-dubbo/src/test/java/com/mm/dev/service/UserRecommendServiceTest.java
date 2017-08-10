package com.mm.dev.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.alibaba.fastjson.JSONObject;
import com.common.util.UUIDGenerator;
import com.mm.dev.constants.WechatConstant;
import com.mm.dev.entity.user.UserRecommend;
import com.mm.dev.service.user.IUserRecommendService;

public class UserRecommendServiceTest extends BaseTest{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IUserRecommendService userRecommendService;
	
	@Test
	public void testSave() throws Exception {
		UserRecommend userRecommend = new UserRecommend();
		userRecommend.setId(UUIDGenerator.generate());
		userRecommend.setRefOpenid("123");
		userRecommend.setRefedOpenid("789");
		userRecommend.setCreateTime(new Date());
		userRecommend.setUpdateTime(new Date());
		userRecommendService.save(userRecommend);
	}
	
	@Test
	public void testFindAll() throws Exception {
		Sort sort = new Sort(Direction.DESC, "id");
		Pageable pageable = new PageRequest(0,50, sort);
    	List<Map<String, String>> findAllByOpenIdAndDelFlag = userRecommendService.findAllByOpenIdAndDelFlag("1234", WechatConstant.delete_flag_1, pageable);
    	String jsonString = JSONObject.toJSONString(findAllByOpenIdAndDelFlag);
    	logger.info("jpa分页第一页：{}",jsonString);
	}
}
