package com.mm.dev.dao.jpa.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mm.dev.entity.wechat.WeixinPayRes;

@Repository
public interface PayDao  extends JpaRepository<WeixinPayRes, Long> {

	/**
	 * 保存微信支付详情
	 * @param weixinPayResDto
	 * @throws Exception
	 */
	public void savePayInfo(WeixinPayRes weixinPayResDto) throws Exception;
}
