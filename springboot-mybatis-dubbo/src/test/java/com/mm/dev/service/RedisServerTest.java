package com.mm.dev.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSONObject;
import com.mm.dev.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisServerTest {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IRedisService redisService;
	
	@Test
	public void test() throws Exception {
		redisService.set("name", "zhangxiaoyu");
		
		logger.info(redisService.get("name").toString());
		
		logger.info(String.valueOf(redisService.dbSize()));
		
		redisService.set("sex", "男");
		
		logger.info(redisService.get("sex"));
		
		List<String> arrayList = new ArrayList<String>();
		arrayList.add("hello");
		arrayList.add("redis");
		redisService.setList("ulist",arrayList);
		
		logger.info(redisService.getList("ulist", String.class).toString());
		
		System.out.println(redisService.keys("na"));
	}
}
