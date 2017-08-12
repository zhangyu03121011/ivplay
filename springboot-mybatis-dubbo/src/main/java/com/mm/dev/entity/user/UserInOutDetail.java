package com.mm.dev.entity.user;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.mm.dev.entity.BaseEntity;

/**
 * @Description:收支明细
 * @author Jacky
 * @date 2017年8月4日 下午7:21:45
 */
@Entity
@Table(name = "t_in_out_detail")
public class UserInOutDetail extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 交易人openId
	 */
	private String openId;
	
	/**
	 * 被交易人openId
	 */
	private String traderOpenId;
	
	
	/**
	 * 交易金额
	 */
	private BigDecimal amount;
	
	/**
	 * 交易手续费
	 */
	private BigDecimal fee;
	
	/**
	 * 交易类型(0:支出打赏 1:进账打赏 2:提现进账 :3：分享打赏 4：退还的赏金 5：内容审核费 6：获得的提成 7：系统退还)
	 */
	private String payType;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getTraderOpenId() {
		return traderOpenId;
	}

	public void setTraderOpenId(String traderOpenId) {
		this.traderOpenId = traderOpenId;
	}
}
