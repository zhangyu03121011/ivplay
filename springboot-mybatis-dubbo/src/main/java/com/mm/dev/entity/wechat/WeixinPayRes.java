package com.mm.dev.entity.wechat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mm.dev.entity.BaseEntity;

@Entity
//@Table(name = "t_payment")
public class WeixinPayRes extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键ID
	 */
	@Id
	private String id;
	
	/**
	 * 订单编号
	 * @return
	 */
	private String orderNo;
	
	/**
	 * 收款账号
	 */
	private String account;
	
	/**
	 * 付款人
	 * @return
	 */
	private String payer;
	
	/**
	 * 付款金额
	 */
	private String amount;
	
	/**
	 * 付款日期
	 */
	private String paymentDate;
	
	/**
	 * 付款方式
	 */
	private String paymentMethod;
	
	/**
	 * 付款类型（1:储值卡 2：信用卡）
	 */
	private String type;
	
	/**
	 * 付款银行
	 * @return
	 */
	private String paymentBank;
	
	/**
	 * 支付状态
	 */
	private Integer paymentStatus;
	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPaymentBank() {
		return paymentBank;
	}

	public void setPaymentBank(String paymentBank) {
		this.paymentBank = paymentBank;
	}

	public Integer getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Integer paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}