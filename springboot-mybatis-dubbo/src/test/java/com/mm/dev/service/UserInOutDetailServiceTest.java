package com.mm.dev.service;

import java.math.BigDecimal;
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
import com.mm.dev.entity.user.UserInOutDetail;
import com.mm.dev.service.user.IUserInOutDetailService;

public class UserInOutDetailServiceTest extends BaseTest{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IUserInOutDetailService userInOutDetailService;
	
	@Test
	public void testSave() throws Exception {
		UserInOutDetail userInOutDetail = new UserInOutDetail();
		userInOutDetail.setId(UUIDGenerator.generate());
		userInOutDetail.setOpenId("o5z7ywOP7qycrtAAxIqDfgMbfcFY");
		userInOutDetail.setTraderOpenId("o5z7ywOAZIaueAeIaChTPTowF_Os");
		userInOutDetail.setFee(new BigDecimal("1.23"));
		userInOutDetail.setAmount(new BigDecimal("10.23"));
		userInOutDetail.setPayType(WechatConstant.pay_type_2);
		userInOutDetail.setCreateTime(new Date());
		userInOutDetail.setUpdateTime(new Date());
		userInOutDetailService.save(userInOutDetail);
	}
	
	@Test
	public void testFindAll() throws Exception {
		Sort sort = new Sort(Direction.DESC, "id");
		Pageable pageable = new PageRequest(0,50, sort);
    	List<Map<String, String>> findAllByOpenIdAndDelFlag = userInOutDetailService.queryAllByOpenIdAndDelFlag("o5z7ywOP7qycrtAAxIqDfgMbfcFY", pageable);
    	String jsonString = JSONObject.toJSONString(findAllByOpenIdAndDelFlag);
    	logger.info("jpa分页第一页：{}",jsonString);
	}
}
