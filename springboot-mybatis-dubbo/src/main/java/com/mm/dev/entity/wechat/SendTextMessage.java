package com.mm.dev.entity.wechat;
import java.util.Map;

public class SendTextMessage extends BaseSendMessage {
	/**
	 * 消息内容
	 */
	private Map<String, String> text;

	public Map<String, String> getText() {
		return text;
	}

	public void setText(Map<String, String> text) {
		this.text = text;
	}
}
