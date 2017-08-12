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
import com.mm.dev.entity.user.UserWithdrawalApply;
import com.mm.dev.enums.WithdrawalApplyStatusEnum;
import com.mm.dev.service.user.IUserWithdrawalApplyService;

public class WithdrawalApplyServiceTest extends BaseTest{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IUserWithdrawalApplyService userWithdrawalApplyService;
	
	@Test
	public void testSave() throws Exception {
		UserWithdrawalApply userWithdrawalApply = new UserWithdrawalApply();
		userWithdrawalApply.setId(UUIDGenerator.generate());
		userWithdrawalApply.setApplyAmount(new BigDecimal("19.999"));
		userWithdrawalApply.setApplyOpenId("123");
		userWithdrawalApply.setApplyPhone("17688707828");
		userWithdrawalApply.setApplyStatus(WithdrawalApplyStatusEnum.success.getIndex());
		userWithdrawalApply.setApplyTime(new Date());
		userWithdrawalApply.setApplyUserName("jacky");
		userWithdrawalApply.setBankName("中国银行");
		userWithdrawalApply.setBranchBankName("深圳支行");
		userWithdrawalApply.setBankTradeNo("123456789");
		userWithdrawalApply.setCardNo("4444444");
		userWithdrawalApply.setCardPersonName("张晓雨");
		userWithdrawalApply.setCreateTime(new Date());
		userWithdrawalApply.setUpdateTime(new Date());
		
		userWithdrawalApplyService.save(userWithdrawalApply);
	}
	
	@Test
	public void testFindAll() throws Exception {
		Sort sort = new Sort(Direction.DESC, "id");
		Pageable pageable = new PageRequest(0,50, sort);
    	List<Map<String, String>> findAllByOpenIdAndDelFlag = userWithdrawalApplyService.queryAllByApplyOpenIdAndDelFlag("o5z7ywGOmRIxxoVqQmNbouU9zvJ4", WechatConstant.delete_flag_1, pageable);
    	String jsonString = JSONObject.toJSONString(findAllByOpenIdAndDelFlag);
    	logger.info("jpa分页第一页：{}",jsonString);
	}
}
