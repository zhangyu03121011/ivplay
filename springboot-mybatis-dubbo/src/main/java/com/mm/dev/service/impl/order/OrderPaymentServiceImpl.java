package com.mm.dev.service.impl.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mm.dev.dao.jpa.user.OrderPaymentDao;
import com.mm.dev.entity.order.OrderPayment;
import com.mm.dev.service.order.IOrderPaymentService;


/**
 * @ClassName: OrderPaymentServiceImpl 
 * @Description: 订单管理
 * @author zhangxy
 * @date 2017年8月10日 下午2:00:09
 */
@Service
public class OrderPaymentServiceImpl  implements IOrderPaymentService{
	Logger logger = LoggerFactory.getLogger(OrderPaymentServiceImpl.class);
	
	@Autowired
	private OrderPaymentDao orderPaymentDao;
	
	/**
	 * 微信支付回调通知 更新订单、支付状态
	 * @param tranData
	 * @throws Exception
	 */
	public void weixinPayNotifyInfo(String orderNo) throws Exception{
		
	}
	
	/**
	 * 保存微信支付详情
	 * @param weixinPayResDto
	 * @throws Exception
	 */
	public void savePayInfo(OrderPayment orderPayment) throws Exception{
		orderPaymentDao.save(orderPayment);
	}
}
