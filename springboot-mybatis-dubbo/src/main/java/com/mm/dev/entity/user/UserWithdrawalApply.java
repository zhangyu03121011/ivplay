package com.mm.dev.entity.user;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.mm.dev.entity.BaseEntity;

/**
 * @Description:提现申请
 * @author Jacky
 * @date 2017年8月4日 下午7:21:45
 */
@Entity
@Table(name = "t_withdrawal_apply")
public class UserWithdrawalApply extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 申请人openId
	 */
	private String applyOpenId;
	
	/**
	 * 申请人用户名
	 */
	private String applyUserName;
	
	/**
	 * 申请人手机号
	 */
	private String applyPhone;
	
	/**
	 * 申请提现金额
	 */
	private BigDecimal applyAmount;
	
	/**
	 * 提现状态,0:未处理,1:处理失败,2:处理成功
	 */
	private String applyStatus;
	
	/**
	 * 申请提现时间
	 */
	private Date applyTime;
	
	/**
	 * 提现卡号
	 */
	private String cardNo;
	
	/**
	 * 持卡人姓名
	 */
	private String cardPersonName;
	
	/**
	 * 所属城市
	 */
	private String provinceCity;
	
	/**
	 * 提现银行
	 */
	private String bankName;
	
	/**
	 * 分行名称
	 */
	private String branchBankName;
	
	/**
	 * 银行交易流水
	 */
	private String bankTradeNo;
	
	/**
	 * 处理时间
	 */
	private Date disposeTime;
	
	/**
	 * 操作人
	 */
	private String operator;

	public String getApplyOpenId() {
		return applyOpenId;
	}

	public void setApplyOpenId(String applyOpenId) {
		this.applyOpenId = applyOpenId;
	}

	public String getApplyUserName() {
		return applyUserName;
	}

	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}

	public String getApplyPhone() {
		return applyPhone;
	}

	public void setApplyPhone(String applyPhone) {
		this.applyPhone = applyPhone;
	}

	public String getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardPersonName() {
		return cardPersonName;
	}

	public void setCardPersonName(String cardPersonName) {
		this.cardPersonName = cardPersonName;
	}

	public String getProvinceCity() {
		return provinceCity;
	}

	public void setProvinceCity(String provinceCity) {
		this.provinceCity = provinceCity;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranchBankName() {
		return branchBankName;
	}

	public void setBranchBankName(String branchBankName) {
		this.branchBankName = branchBankName;
	}

	public String getBankTradeNo() {
		return bankTradeNo;
	}

	public void setBankTradeNo(String bankTradeNo) {
		this.bankTradeNo = bankTradeNo;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public BigDecimal getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(BigDecimal applyAmount) {
		this.applyAmount = applyAmount;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public Date getDisposeTime() {
		return disposeTime;
	}

	public void setDisposeTime(Date disposeTime) {
		this.disposeTime = disposeTime;
	}
}
