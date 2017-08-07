package com.mm.dev.controller.user;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mm.dev.controller.wechat.wechartController;
import com.mm.dev.entity.user.User;
import com.mm.dev.entity.wechat.ReturnMsg;
import com.mm.dev.service.user.IUserService;

@Controller
@RequestMapping("/user")
public class UserController {
	private Logger logger = LoggerFactory.getLogger(wechartController.class);
	
	@Autowired
	private IUserService userService;
	
	/**
	 * @Description: 根据openId获取用户信息
	 * @Datatime 2017年8月5日 下午3:16:51 
	 * @return void    返回类型
	 */
	@RequestMapping("/queryUserInfo/{openId}")
	@ResponseBody
	public ReturnMsg<User> queryUserInfo(@PathVariable String openId,HttpServletResponse response) throws Exception {
		ReturnMsg<User> returnMsg = new ReturnMsg<>();
		try {
			User userInfo = userService.getuserBaseInfoByopenId(openId);
			returnMsg.setStatus(true);
			returnMsg.setData(userInfo);
		} catch (Exception e) {
			logger.error("根据openId获取用户信息异常",e);
			returnMsg.setStatus(false);
		}
		return returnMsg;
	}
}
