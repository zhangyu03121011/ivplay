package com.mm.dev.service.order;

import com.mm.dev.entity.order.OrderPayment;

/**
 * @ClassName: IOrderPaymentService 
 * @Description: TODO
 * @author zhangxy
 * @date 2017年8月10日 下午2:00:09
 */
public interface IOrderPaymentService {
	
	/**
	 * 微信支付回调通知 更新订单、支付状态
	 * @param tranData
	 * @throws Exception
	 */
	public void weixinPayNotifyInfo(String orderNo) throws Exception;
	
	/**
	 * 保存微信支付详情
	 * @param weixinPayResDto
	 * @throws Exception
	 */
	public void savePayInfo(OrderPayment orderPayment) throws Exception;
}
