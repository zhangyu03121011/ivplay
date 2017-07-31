package com.mm.dev.entity.wechat;
public class BaseSendMessage {
	/**
	 * 消息接收方openId数组
	 */
	private String[] touser;
	
	/**
	 * 消息消息类型
	 */
	private String msgtype;
	
	public String[] getTouser() {
		return touser;
	}

	public void setTouser(String[] touser) {
		this.touser = touser;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
}
