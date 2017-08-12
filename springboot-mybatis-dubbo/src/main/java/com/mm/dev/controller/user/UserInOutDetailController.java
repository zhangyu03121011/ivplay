package com.mm.dev.controller.user;

import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.mm.dev.entity.wechat.ReturnMsg;
import com.mm.dev.service.user.IUserInOutDetailService;
import com.mm.dev.wechatUtils.ReturnMsgUtil;
/**
 * @ClassName: wechartController 
 * @Description: 我的推荐
 * @author zhangxy
 * @date 2017年7月31日 下午3:03:05
 */
@Controller
@RequestMapping("/userInOutDetail")
public class UserInOutDetailController{
	private Logger logger = LoggerFactory.getLogger(UserInOutDetailController.class);
	
	@Autowired
	private IUserInOutDetailService userInOutDetailService;
	
	/**
	 * @Description: 分页查询我的提现记录
	 * @DateTime:2017年8月11日 下午12:09:34
	 * @return ReturnMsg<Object>
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/{start}/{count}/record/list")
	@ResponseBody
	public ReturnMsg<Object> getUserWithdrawalRecordList(@PathVariable("start") int start,@PathVariable("count") int count, HttpServletResponse response){
		List<Map<String, String>> userWithdrawalRecordList = null;
		try {
//			String openId = (String)UserSession.getSession(WechatConstant.OPEN_ID);
			String openId = "o5z7ywOP7qycrtAAxIqDfgMbfcFY";
			logger.info("分页查询我的推荐记录查询参数openId=",openId);
			if(StringUtils.isNotEmpty(openId)) {
				Sort sort = new Sort(Direction.DESC, "createTime");
				Pageable pageable = new PageRequest(start,count, sort);
				userWithdrawalRecordList = userInOutDetailService.queryAllByOpenIdAndDelFlag(openId, pageable);
			}
		} catch (Exception e) {
			logger.error("分页查询我的推荐记录异常",e);
		} 
		return ReturnMsgUtil.success(userWithdrawalRecordList);
	}
}

