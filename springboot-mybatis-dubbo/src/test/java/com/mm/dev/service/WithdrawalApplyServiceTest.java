package com.mm.dev.service;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.common.util.UUIDGenerator;
import com.mm.dev.entity.user.UserWithdrawalApply;
import com.mm.dev.enums.WithdrawalApplyStatusEnum;
import com.mm.dev.service.user.IUserWithdrawalApplyService;

public class WithdrawalApplyServiceTest extends BaseTest{
	
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
}
