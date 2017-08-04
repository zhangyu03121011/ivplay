package com.mm.dev.service.pay;

import com.mm.dev.entity.wechat.WeixinPayRes;

public interface PayService {
	
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
	public void savePayInfo(WeixinPayRes weixinPayResDto) throws Exception;
}
