package com.mm.dev.controller.wechat;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.mm.dev.config.ConfigProperties;
import com.mm.dev.entity.wechat.AccessToken;
import com.mm.dev.entity.wechat.WechatConfig;
import com.mm.dev.service.wechat.IWechatService;
import com.mm.dev.util.CheckUtil;
import com.mm.dev.util.MessageUtil;
import com.mm.dev.util.UserSession;
/**
 * @ClassName: wechartController 
 * @Description: 微信业务接口
 * @author zhangxy
 * @date 2017年7月31日 下午3:03:05
 */
@Controller
@RequestMapping("/wechat")
public class wechartController{
	private Logger logger = LoggerFactory.getLogger(wechartController.class);
	
	@Autowired
	private IWechatService wechatService;
	
	@Autowired
	private ConfigProperties configProperties;
	
	/**
	 * @Description: 接入验证
	 * @DateTime:2017年7月31日 上午11:03:31
	 * @return void
	 * @throws
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");
		PrintWriter out = resp.getWriter();
		if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
			out.print(echostr);
		}
	}

	/**
	 * @Description: 微信消息的接收和响应
	 * @DateTime:2017年7月31日 上午11:02:58
	 * @return void
	 * @throws
	 */
	@RequestMapping(value = "/index", method = RequestMethod.POST)
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("消息的接收与响应开始");
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		try {
			Map<String, String> map = MessageUtil.xmlToMap(req);
			String fromUserName = map.get("FromUserName");
			String toUserName = map.get("ToUserName");
			String msgType = map.get("MsgType");
			String content = map.get("Content");
			String message = null;
			if (MessageUtil.MESSAGE_TEXT.equals(msgType)) {
				 if ("?".equals(content) || "？".equals(content)) {
					 message = MessageUtil.initText(toUserName, fromUserName,MessageUtil.menuText());
				 }
			} else if (MessageUtil.MESSAGE_EVNET.equals(msgType)) {
				String eventType = map.get("Event");
				logger.info(" eventType={}", eventType);
				// 关注
				if (MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)) {
					logger.info("关注操作======");
					logger.info("消息的接收与响应信息======toUserName：" + toUserName);
					logger.info("消息的接收与响应信息======fromUserName：" + fromUserName);
					logger.info("消息的接收与响应信息======EventKey：" + map.get("EventKey"));
					logger.info("消息的接收与响应信息======Ticket：" + map.get("Ticket"));
					logger.info("消息的接收与响应信息======content：" + MessageUtil.secondMenu());
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.welcomeMessage());

					//带参数关注二维码
					String qrscene = map.get("EventKey"); // qrscene_535d1d35e035441e93aa18883d3d84af
					if (StringUtils.isNotEmpty(qrscene)) {
						
					}
				} else if (MessageUtil.MESSAGE_UNSUBSCRIBE.equals(eventType)) { // 取消关注
					logger.info("取消关注操作======");
					logger.info("消息的接收与响应信息======toUserName：" + toUserName);
					logger.info("消息的接收与响应信息======fromUserName：" + fromUserName);
					logger.info("消息的接收与响应信息======content：" + MessageUtil.secondMenu());
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.secondMenu());
					
				} else if (MessageUtil.MESSAGE_SCAN.equalsIgnoreCase(eventType)) {
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.welcomeMessage());
				} else {
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.welcomeMessage());
				}
			} else {
				message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.welcomeMessage());
			}
			logger.info("消息的接收与响应结束======" + message);
			if(null != out) {
				out.print(message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

	/**
	 * @Description: 微信授权回调函数
	 * @DateTime:2017年7月31日 上午11:01:49
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value = "/callback", produces = "text/plain;charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView oauth2authorize(@RequestParam(value = "code") String authcode, @RequestParam(value = "state") String state,
			@RequestParam(value = "fileNames", required = false) String fileNames, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("获取网页授权code回调开始======" + authcode);
		logger.info("获取网页授权state回调======" + state);
		String gotoPage = "http://blqjp.sanongyou.com/s/53/wx_upload_photo.html?p=decf913fd9ec&o=owO89wy4vGPpz1SnMtbfh0UyN3c8&ap=3cb47e68e961&code=0519yA3d1EpE6v0urU2d1SHk3d19yA3B&state=STATE";
		try {
			//从微信获取openId
			String openId = wechatService.queryWxuseridCallTX(authcode);
			logger.info("获取网页授权获取openId:{}======",openId);
			UserSession.setSession("openId", openId);
			//普通用户注册(openId注册)
			if ("1".equals(state)) {
				//上传图片
				gotoPage = "/wx_upload_photo.html";
			} else if("2".equals(state)) {
				//查看分享模糊图片
				StringBuilder imagePath = new StringBuilder();
        		imagePath.append("/");
        		imagePath.append("wx_blur_image_share.html?");
        		imagePath.append("id=");
        		imagePath.append(openId);
        		imagePath.append("&fileNames=");
        		imagePath.append(fileNames);
				gotoPage = imagePath.toString();
			}
		} catch (Exception e) {
			logger.error("获取网页授权code回调异常" + e);
		}
		logger.info("网页授权跳转URL：{}",gotoPage);
		return new ModelAndView("redirect:" + gotoPage);
	}
	
	/**
	 * @Description: 发送模板消息
	 * @DateTime:2017年7月31日 下午1:47:39
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "/sendMessages")
	@ResponseBody
	public String sendMessages(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AccessToken accessToken = wechatService.getAccessToken();
		Map<String, Object> inMap = new HashMap<String, Object>();
		Map<String, Object> sendMap = new HashMap<String, Object>();
		String sendMessages = null;
		inMap.put("industry_id1", "1");
		inMap.put("industry_id2", "4");
		// 设置所属行业
		/*
		 * String outStr=JsonMapper.getInstance().toJsonString(inMap); String
		 * industry = WeixinUtil.setIndustry(accessToken, outStr);
		 * logger.info("设置所属行业返回码===="+industry);
		 */
		Map<String, Object> TeMap = new HashMap<String, Object>();
		TeMap.put("template_id_short", "TM00015");
		// 获取模板Id
		String outStr1 = JSONObject.toJSONString(TeMap);
		JSONObject jsonObject = wechatService.getTemplateId(accessToken, outStr1);
		if (StringUtils.isNotEmpty(jsonObject.getString("template_id"))) {
			logger.info("获取模板ID=====" + jsonObject.getString("template_id"));
			Map<String, Object> map1 = new HashMap<String, Object>();
			Map<String, Object> map2 = new HashMap<String, Object>();
			Map<String, Object> map3 = new HashMap<String, Object>();
			Map<String, Object> map4 = new HashMap<String, Object>();
			Map<String, Object> map5 = new HashMap<String, Object>();
			Map<String, Object> map6 = new HashMap<String, Object>();
			map1.put("value", "恭喜你购买成功！");
			map1.put("color", "#173177");
			map2.put("value", "巧克力");
			map2.put("color", "#173177");
			map3.put("value", "39.8元");
			map3.put("color", "#173177");
			map4.put("value", "2017年1月13日");
			map4.put("color", "#173177");
			map5.put("value", "欢迎再次购买！");
			map5.put("color", "#173177");
			map6.put("remark", map5);
			map6.put("keynote3", map4);
			map6.put("keynote2", map3);
			map6.put("keynote1", map2);
			map6.put("first", map1);
			sendMap.put("data", map6);
			sendMap.put("url", "http://weixin.qq.com/download");
			sendMap.put("template_id", jsonObject.getString("template_id"));
			sendMap.put("touser", "ogQkqxN566aW4ZrSPRcQykjba8-o");
			String outStr2 = JSONObject.toJSONString(sendMap);
			sendMessages = wechatService.sendTemplateMang(accessToken, outStr2);
		}
		return sendMessages;
	}
	
	/**
	 * @Description: 获取微信页签
	 * @DateTime:2017年7月31日 下午1:46:10
	 * @return WechatConfig
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/getWechatConfig")
	public WechatConfig getWechatConfig(String url) throws Exception {
		logger.info("获取微信页签开始……" + url);
		WechatConfig config = new WechatConfig();
		try {
			config = wechatService.getWechatConfig(url);
		} catch (Exception e) {
			logger.info("获取微信页签异常:" + e.fillInStackTrace());
		}
		return config;
	}

	/**
	 * @Description: 从微信下载图片
	 * @DateTime:2017年7月31日 下午1:45:59
	 * @return String
	 * @throws
	 */
	@RequestMapping("/downloadImage")
	@ResponseBody
	public String downloadImage(String mediaId, HttpServletRequest request) {
		logger.info("从微信下载图片开始……" + mediaId);
		String rootPath = request.getSession().getServletContext().getRealPath("");
		String imgPath = null;
		try {
			imgPath = wechatService.downloadImage(mediaId, rootPath);
		} catch (Exception e) {
			logger.info("从微信下载图片异常:" + e.fillInStackTrace());
		}
		return imgPath;
	}
	
	/**
	 * @Description: 从服务器上删除图片
	 * @DateTime:2017年7月31日 下午1:46:33
	 * @return Boolean
	 * @throws
	 */
	@RequestMapping("/deleteFile")
	@ResponseBody
	public Boolean deleteFile(String rootPath, HttpServletRequest request) {
		try {
			return wechatService.deleteFile(rootPath);
		} catch (Exception e) {
			logger.error("从服务器上删除图片===path:{}",rootPath,e);
		}
		return false;
	}
}

