package com.mm.dev.service.pay.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mm.dev.dao.jpa.user.PayDao;
import com.mm.dev.entity.wechat.WeixinPayRes;
import com.mm.dev.service.pay.IPayService;

/**
 * 支付处理service
 * @author kevin
 *
 */
@Service
public class PayServiceImpl implements IPayService{
	
	@Autowired
	private PayDao paydao;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 微信支付回调通知 更新订单、支付状态
	 * @param tranData
	 * @throws Exception
	 */
	public void weixinPayNotifyInfo(String orderNo) throws Exception {
		logger.info("更新商品库存,订单状态,支付状态开始"+orderNo);
		//支付成功情况下修改订单状态以及商品库存
//		orderService.updateOrderStatusAndPayStatus(orderNo, OrderStatus.paid_shipped.getIndex(),PaymentStatusEnum.paid.getIndex()); 
	}
	
	/**
	 * 保存微信支付详情
	 * @param weixinPayResDto
	 * @throws Exception
	 */
	public void savePayInfo(WeixinPayRes weixinPayResDto) throws Exception {
		//保存微信支付详情
		paydao.save(weixinPayResDto); 
	}

}
