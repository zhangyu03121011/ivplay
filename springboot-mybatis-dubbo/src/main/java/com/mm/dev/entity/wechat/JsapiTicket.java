package com.mm.dev.entity.wechat;
import java.io.Serializable;

/**
 * @Todo
 * @date 2016年6月14日
 * @author Ly
 */
public class JsapiTicket implements Serializable{
	private static final long serialVersionUID = 1L;
	private String errcode;
	private String errmsg;
	private String ticket;
	private String expiresIn;

	public String getErrcode() {
		return errcode;
	}

	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}

}
