package com.mm.dev.service;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.common.util.OrderNoGenerateUtil;
import com.common.util.UUIDGenerator;
import com.mm.dev.entity.order.OrderPayment;
import com.mm.dev.enums.PaymentMethodEnum;
import com.mm.dev.enums.PaymentStatusEnum;
import com.mm.dev.service.order.IOrderPaymentService;

public class OrderPaymentServiceTest extends BaseTest{
	
	@Autowired
	private IOrderPaymentService orderPaymentService;
	
	@Test
	public void test() throws Exception {
		OrderPayment orderPayment = new OrderPayment();
		orderPayment.setId(UUIDGenerator.generate());
		orderPayment.setOrderNo(OrderNoGenerateUtil.getOrderNo());
		orderPayment.setPayer("张晓雨");
		orderPayment.setPaymentMethod(PaymentMethodEnum.WECHATPAY.getIndex());
		orderPayment.setPaymentStatus(PaymentStatusEnum.success.getIndex());
		orderPayment.setPaymentTime(new java.util.Date());
		orderPayment.setAmount(new BigDecimal("100"));
		orderPayment.setAccount("微信");
		orderPayment.setBank("中国银行");
		orderPayment.setCreateTime(new Date());
		orderPayment.setUpdateTime(new Date());
		orderPaymentService.savePayInfo(orderPayment);
	}
}
