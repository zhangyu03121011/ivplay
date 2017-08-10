package com.mm.dev.controller.wechat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
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
import com.common.util.DateUtil;
import com.common.util.UUIDGenerator;
import com.mm.dev.config.ConfigProperties;
import com.mm.dev.constants.WechatConstant;
import com.mm.dev.entity.order.OrderPayment;
import com.mm.dev.entity.user.UserRecommend;
import com.mm.dev.entity.wechat.AccessToken;
import com.mm.dev.entity.wechat.WechatConfig;
import com.mm.dev.entity.wechat.WechatPayConfig;
import com.mm.dev.enums.PaymentMethodEnum;
import com.mm.dev.enums.PaymentStatusEnum;
import com.mm.dev.enums.WXBankTypeEnum;
import com.mm.dev.service.order.IOrderPaymentService;
import com.mm.dev.service.user.IUserRecommendService;
import com.mm.dev.service.user.IUserService;
import com.mm.dev.service.wechat.IWechatService;
import com.mm.dev.wechatUtils.CheckUtil;
import com.mm.dev.wechatUtils.MessageUtil;
import com.mm.dev.wechatUtils.UserSession;
/**
 * @ClassName: wechartController 
 * @Description: 微信业务接口
 * @author zhangxy
 * @date 2017年7月31日 下午3:03:05
 */
@Controller
@RequestMapping("/wechat")
public class WechartController{
	private Logger logger = LoggerFactory.getLogger(WechartController.class);
	
	@Autowired
	private IWechatService wechatService;
	
	@Autowired
	private ConfigProperties configProperties;
	
	@Autowired
	private IOrderPaymentService orderPaymentService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IUserRecommendService userRecommendService;
	
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
					 message = MessageUtil.initText(toUserName, fromUserName,MessageUtil.welcomeMessage());
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
					//普通用户注册(openId注册)
					userService.weixinRegister(req,fromUserName, WechatConstant.attention_status_2);
					//带参数关注二维码
					String qrscene = map.get("EventKey"); // qrscene_535d1d35e035441e93aa18883d3d84af
					if (StringUtils.isNotEmpty(qrscene)) {
						UserRecommend userRecommend = new UserRecommend();
						userRecommend.setRefedOpenid(fromUserName);
						userRecommend.setRefOpenid(qrscene);
						userRecommend.setCreateTime(new Date());
						userRecommend.setUpdateTime(new Date());
						userRecommendService.save(userRecommend);
					}
				} else if (MessageUtil.MESSAGE_UNSUBSCRIBE.equals(eventType)) { // 取消关注
					logger.info("取消关注操作======");
					logger.info("消息的接收与响应信息======toUserName：" + toUserName);
					logger.info("消息的接收与响应信息======fromUserName：" + fromUserName);
					logger.info("消息的接收与响应信息======content：" + MessageUtil.secondMenu());
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.secondMenu());
					userService.unSubscribe(fromUserName);
				} else if (MessageUtil.MESSAGE_SCAN.equalsIgnoreCase(eventType)) {
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.firstMenu());
				} else {
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.firstMenu());
				}
			} else {
				message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.firstMenu());
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
			@RequestParam(value = "fileNewNames", required = false) String fileNewNames, HttpServletRequest request, HttpServletResponse response)
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
			userService.weixinRegister(request,openId, WechatConstant.attention_status_1);
			
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
        		imagePath.append("&fileNewNames=");
        		imagePath.append(fileNewNames);
				gotoPage = imagePath.toString();
			} else if("3".equals(state)) {
				//跳转支付查看页面
				StringBuilder imagePath = new StringBuilder();
        		imagePath.append("/");
        		imagePath.append("blur_image_wxpay.html?");
        		imagePath.append("id=");
        		imagePath.append(openId);
				gotoPage = imagePath.toString();
			} else if("4".equals(state)) {
				//跳转我的文件列表
				gotoPage = "/wx_my_list.html";
			} else if("5".equals(state)) {
				//跳转朋友圈首页
				gotoPage = "/home/home.html";
			} else if("6".equals(state)) {
				//跳转朋友圈首页
				gotoPage = "/pages/wx_upload_video.html";
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
	 * 获取微信支付签名
	 * @param url
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getWechatPayConfig")
	public WechatPayConfig getWechatPayConfig(String orderNo, HttpServletRequest request) throws Exception {
		logger.info("获取微信支付签名开始……orderNo:" + orderNo);
		WechatPayConfig config = new WechatPayConfig();
		try {
			String openId = (String) UserSession.getSession(WechatConstant.OPEN_ID);
			logger.info("获取微信支付……openId:" + openId);
			if (!StringUtils.isEmpty(orderNo) && !StringUtils.isEmpty(openId)) {
//				OrderTable order = orderService.getOrderByOrderNo(orderNo);
				 String amount = "0";
				// 应付款 = 订单总额 - 已付金额
//				String amount = order.getAmount().subtract(new BigDecimal(order.getAmountPaid())).toString();
//				logger.info("orderNo===" + orderNo);
//				logger.info("openId===" + openId);
//				logger.info("amount===" + amount);
				config = wechatService.getWechatPayConfig(orderNo, openId, amount, request);
			}
		} catch (Exception e) {
			logger.info("获取WechatPayConfig异常" + e.fillInStackTrace());
		}
		return config;
	}

	/**
	 * 微信支付回调
	 * 
	 * @throws Exception
	 *             void
	 */
	@ResponseBody
	@RequestMapping("/payCallBack")
	public void payCallBack(HttpServletRequest request, HttpServletResponse response) {
		try {
			logger.info("微信支付回调通知接口开始");
			InputStream inStream = request.getInputStream();
			ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = inStream.read(buffer)) != -1) {
				outSteam.write(buffer, 0, len);
			}
			outSteam.close();
			inStream.close();
			String resultStr = new String(outSteam.toByteArray(), "utf-8");
			Map<String, String> resultMap = MessageUtil.parseXml(resultStr);
			logger.info("微信支付异步回调返回结果" + resultStr);
			String return_code = resultMap.get("return_code");
			String result_code = resultMap.get("result_code");

			if ("SUCCESS".equals(return_code) && "SUCCESS".equals(result_code)) {
				logger.info("支付回调成功");
				// 更新订单状态 支付状态 商品库存
//				payService.weixinPayNotifyInfo(resultMap.get("out_trade_no"));
				// 根据订单号更新商品销量优惠券状态
//				orderService.updateProductSalesByOrderNO(resultMap.get("out_trade_no"));

				// 组装订单支付信息
				OrderPayment weixinPayResDto = new OrderPayment();
				weixinPayResDto.setId(UUIDGenerator.generate());
				weixinPayResDto.setAmount(new BigDecimal(resultMap.get("total_fee")));
				weixinPayResDto.setAccount(resultMap.get("appid"));
				weixinPayResDto.setPayer(resultMap.get("openid"));
				weixinPayResDto.setOrderNo(resultMap.get("out_trade_no"));
				weixinPayResDto.setPaymentTime(new Date(resultMap.get("time_end")));
				weixinPayResDto.setPaymentMethod(PaymentMethodEnum.WECHATPAY.getIndex());
				weixinPayResDto.setPaymentStatus(PaymentStatusEnum.success.getIndex());
				weixinPayResDto.setPaymentBank(WXBankTypeEnum.getDescription(resultMap.get("bank_type")));
				logger.info("orderNo====:" + resultMap.get("out_trade_no"));
//				OrderTable orderByOrderNo = orderService.getOrderByOrderNo(resultMap.get("out_trade_no"));
//				if (null != orderByOrderNo) {
//					String id = orderByOrderNo.getId();
//					weixinPayResDto.setOrderTableId(id);
//				}
				logger.info("保存微信付款信息开始....");
				orderPaymentService.savePayInfo(weixinPayResDto);
				logger.info("保存微信付款信息结束....");

				// 通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
				request.setAttribute("out_trade_no", resultMap.get("out_trade_no"));
				response.setContentType("text/html;charset=GBK");
				request.setCharacterEncoding("GBK");
				PrintWriter out = response.getWriter();
				out.print(MessageUtil.setXML("SUCCESS", "OK"));
				logger.info("已返回成功结果...");
			}
		} catch (Exception e) {
			logger.info("微信支付回调通知接口异常" + e);
			try {
				PrintWriter out = response.getWriter();
				out.print(MessageUtil.setXML("FAIL", "ERROR"));
			} catch (Exception e2) {

			}
		}
		logger.info("微信支付回调通知接口结束");
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

