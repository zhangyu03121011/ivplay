package com.mm.dev.controller;

import com.alibaba.fastjson.JSONObject;
import com.common.util.JSONUtil;
import com.mm.dev.entity.user.User;
import com.mm.dev.service.user.IUserService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.type.SortedSetType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Description: test mybatis+jpa 操作数据库
 * @author Jacky
 * @date 2017年7月29日 下午6:48:50
 */
@Controller
@RequestMapping("/")
public class TestSpringBootController {

    private final static Logger log = LoggerFactory.getLogger(TestSpringBootController.class);

    @Autowired
    private IUserService userService;

    @Transactional
    @RequestMapping("/")
    @ResponseBody
    public String sayHello(Pageable pageable) throws Exception{

        Page<User> all = userService.getAll(pageable);

        User user = userService.getUser(1L);

//        List<User> userList = userService.getUserList();
//
//        Page<User> allList = userService.getUserAll(pageable);

        userService.saveUser();
        
        userService.save();
        
        log.info("info");
        log.warn("warn");
        log.debug("debug");
        log.error("error");
        return "hello SpringBoot! -- userId:";
    }
    
    @RequestMapping("/page")
    @ResponseBody
    public String queryUserListByPagination(Pageable pageable) throws Exception {
    	
    	Sort sort = new Sort(Direction.DESC, "id");
    	pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sort);
    	Page<User> all = userService.getAll(pageable);
    	String jsonString = JSONObject.toJSONString(all);
    	log.info("jpa分页：{}",jsonString);
    	
    	Page<User> userAll = userService.getUserAll(pageable);
    	log.info("mybatis分页：{}",JSONObject.toJSONString(userAll));
    	
    	return "hello SpringBoot! -- userId:";
    }

}
