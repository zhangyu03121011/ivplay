package com.mm.dev.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mm.dev.service.user.IUserService;

/**
 * @Description: test mybatis+jpa 操作数据库
 * @author Jacky
 * @date 2017年7月29日 下午6:48:50
 */
@Controller
@RequestMapping("/")
public class TestSpringBootController {

    private final static Logger logger = LoggerFactory.getLogger(TestSpringBootController.class);

    @Autowired
    private IUserService userService;

    @Transactional
    @RequestMapping("/")
    @ResponseBody
    public String sayHello(Pageable pageable) throws Exception{
        return "hello SpringBoot! -- userId:";
    }

}
