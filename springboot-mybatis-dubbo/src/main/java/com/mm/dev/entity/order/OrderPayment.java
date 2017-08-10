package com.mm.dev.entity.order;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.mm.dev.entity.BaseEntity;

/**
 * @Description:订单支付
 * @author Jacky
 * @date 2017年8月4日 下午7:21:45
 */
@Entity
@Table(name = "t_order_payment")
public class OrderPayment extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 订单号
	 */
	private String orderNo;
	
	/**
	 * 收款账号
	 */
	private String account;
	
	/**
	 * 收款银行
	 */
	private String bank;
	
	/**
	 * 支付手续费
	 */
	private String fee;
	
	/**
	 * 付款人
	 */
	private String payer;
	
	/**
	 * 付款金额
	 */
	private BigDecimal amount;
	
	/**
	 * 支付状态（1：待支付 2:支付成功 3：支付失败）
	 */
	private String paymentStatus;
	
	/**
	 * 支付方式(1:微信支付)
	 */
	private String paymentMethod;
	
	/**
	 * 付款时间
	 */
	private Date paymentTime;
	
	/**
	 * 付款银行
	 */
	private String paymentBank;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPaymentBank() {
		return paymentBank;
	}

	public void setPaymentBank(String paymentBank) {
		this.paymentBank = paymentBank;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Date getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}
