package com.mm.dev.controller.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.zxing.EncodeHintType;
import com.mm.dev.config.ConfigProperties;
import com.mm.dev.entity.wechat.ReturnMsg;
import com.mm.dev.service.user.IUserRecommendService;
import com.mm.dev.service.wechat.IWechatService;
import com.mm.dev.util.ReturnMsgUtil;
/**
 * @ClassName: wechartController 
 * @Description: 微信业务接口
 * @author zhangxy
 * @date 2017年7月31日 下午3:03:05
 */
@Controller
@RequestMapping("/userRecommend")
public class UserRecommendController{
	private Logger logger = LoggerFactory.getLogger(UserRecommendController.class);
	
	@Autowired
	private IWechatService wechatService;
	
	@Autowired
	private IUserRecommendService userRecommendService;
	
	@Autowired
	private ConfigProperties configProperties;
	
	/**
	 * @Description: 从微信获取推荐关注二维码路径
	 * @DateTime:2017年8月10日 上午11:29:22
	 * @return ReturnMsg<Object>
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/userRecommendQrImage")
	@ResponseBody
	public ReturnMsg<Object> getUserRecommendQrImage(String refOpenid, HttpServletResponse response){
		String qrcodeImagePath = null;
		try {
			Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			logger.info("推荐人openId==={}",refOpenid);
			JSONObject qrcodeTicketByToken = wechatService.getQrcodeTicketByToken(refOpenid);
			if(null != qrcodeTicketByToken && StringUtils.isEmpty(qrcodeTicketByToken.getString("errcode"))) {
				logger.info("从微信获取推荐关注二维码路径path={}",qrcodeTicketByToken.getString("url"));
				qrcodeImagePath = qrcodeTicketByToken.getString("url");
			} 
		} catch (Exception e) {
			logger.error("从微信获取推荐关注二维码路径异常",e);
		} 
		return ReturnMsgUtil.success(qrcodeImagePath);
	}
}

