package com.mm.dev.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSONObject;
import com.common.util.JSONUtil;
import com.mm.dev.dao.mapper.UserMapper;
import com.mm.dev.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserMapper userMapper;
	
	@Test
	public void testJpa() {
		try {
			userService.save();
//			System.out.println(JSONObject.toJSONString(userService.getUser(1L)));
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("保存异常",e);
		}
	}
	
	@Test
	public void testMybatis() {
		try {
			userService.saveUser();
			List<User> userList = userService.getUserList();
			System.out.println(JSONObject.toJSONString(userList));
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("保存异常",e);
		}
	}
	
	@Test
	public void testJpaPage() throws Exception {
		Sort sort = new Sort(Direction.DESC, "id");
		Pageable pageable = new PageRequest(0,50, sort);
    	Page<User> all = userService.getAll(pageable);
    	String jsonString = JSONObject.toJSONString(all);
    	logger.info("jpa分页第一页：{}",jsonString);
    	
    	logger.info("jpa分页第二页：{}",JSONObject.toJSONString(userService.getAll(new PageRequest(1,50, sort))));
//    	
//    	Page<User> userAll = userService.getUserAll(pageable);
//    	logger.info("mybatis分页：{}",JSONObject.toJSONString(userAll));
	}
	
	@Test
	public void testMybatisPage() throws Exception {
		Sort sort = new Sort(Direction.DESC, "id");
		Pageable pageable = new PageRequest(0,50, sort);
		
		Page<User> userAll = userService.getUserAll(pageable);
		logger.info("第一页"+JSONObject.toJSONString(userAll));
		
		logger.info("第二页"+JSONObject.toJSONString(userService.getUserAll(new PageRequest(1,50, new Sort(Direction.DESC, "id")))));
	}
	
	public static void main(String[] args) {
		Sort sort = new Sort(Direction.DESC, "id");
		Pageable pageable = new PageRequest(0,50, sort);
		System.out.println(pageable.getOffset());
		System.out.println(pageable.getPageNumber());
		System.out.println(pageable.getPageSize());
	}
}
