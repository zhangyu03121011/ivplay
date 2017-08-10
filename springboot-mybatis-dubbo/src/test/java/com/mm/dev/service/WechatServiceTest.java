package com.mm.dev.service;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.mm.dev.service.wechat.IWechatService;


public class WechatServiceTest extends BaseTest{
Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IWechatService wechatService;
	
	@Test
	public void testGetQrcodeTicketByToken() throws Exception {
		JSONObject qrcodeTicketByToken = wechatService.getQrcodeTicketByToken("123");
		logger.info(JSONObject.toJSONString(qrcodeTicketByToken));
	}
}
