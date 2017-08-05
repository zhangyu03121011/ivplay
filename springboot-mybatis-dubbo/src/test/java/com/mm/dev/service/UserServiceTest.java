package com.mm.dev.service;

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
import com.mm.dev.dao.mapper.user.UserMapper;
import com.mm.dev.entity.user.User;
import com.mm.dev.service.user.IUserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private UserMapper userMapper;
	
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
	
	
	public static void main(String[] args) {
		Sort sort = new Sort(Direction.DESC, "id");
		Pageable pageable = new PageRequest(0,50, sort);
		System.out.println(pageable.getOffset());
		System.out.println(pageable.getPageNumber());
		System.out.println(pageable.getPageSize());
	}
}
