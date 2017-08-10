package com.mm.dev.controller.user;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mm.dev.controller.wechat.WechartController;
import com.mm.dev.entity.user.User;
import com.mm.dev.entity.wechat.ReturnMsg;
import com.mm.dev.enums.ExceptionEnum;
import com.mm.dev.service.user.IUserService;
import com.mm.dev.wechatUtils.ReturnMsgUtil;

@Controller
@RequestMapping("/user")
public class UserController {
	private Logger logger = LoggerFactory.getLogger(WechartController.class);
	
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
	
	/**
	 * @Description:朋友圈首页分页查询列表
	 * @Datatime 2017年8月6日 下午9:42:44 
	 * @return List<UserFiles>    返回类型
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/{start}/{count}/userFiles/list", method = RequestMethod.GET)
	@ResponseBody
	public ReturnMsg<Object> findUserFilesList(@PathVariable("start") int start,@PathVariable("count") int count,HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Map<String, String>> userFileList = null;
		try {
//			String openId = (String)UserSession.getSession(WechatConstant.OPEN_ID);
			String openId = "o5z7ywOP7qycrtAAxIqDfgMbfcFY";
			if(StringUtils.isNotEmpty(openId)) {
				Sort sort = new Sort(Direction.DESC, "updateTime");
				Pageable pageable = new PageRequest(start,count, sort);
				userFileList = userService.findUserFilesList(null,pageable);
			}
		} catch (Exception e) {
			logger.error("根据opoenId,文件分类查询列表异常",e);
			return ReturnMsgUtil.error(ExceptionEnum.system_error);
		}
		return ReturnMsgUtil.success(userFileList);
	}
}
