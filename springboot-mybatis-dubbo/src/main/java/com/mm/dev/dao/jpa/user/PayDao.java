package com.mm.dev.dao.jpa.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mm.dev.entity.wechat.WeixinPayRes;

/**
 * @Description: PayDao
 * @author Jacky
 * @date 2017年8月6日 下午9:46:15
 */
@Repository
public interface PayDao  extends JpaRepository<WeixinPayRes, String> {

	/**
	 * 保存微信支付详情
	 * @param weixinPayResDto
	 * @throws Exception
	 */
//	public void save(WeixinPayRes weixinPayResDto) throws Exception;
}
