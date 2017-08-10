package com.mm.dev.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSONObject;
import com.mm.dev.service.wechat.IWechatService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class WechatServiceTest {
Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IWechatService wechatService;
	
	@Test
	public void testGetQrcodeTicketByToken() throws Exception {
		JSONObject qrcodeTicketByToken = wechatService.getQrcodeTicketByToken("123");
		logger.info(JSONObject.toJSONString(qrcodeTicketByToken));
	}
}
