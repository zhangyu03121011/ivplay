package com.mm.dev.service.impl.wechat;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.common.util.DateUtil;
import com.mm.dev.config.ConfigProperties;
import com.mm.dev.entity.user.User;
import com.mm.dev.entity.wechat.AccessToken;
import com.mm.dev.entity.wechat.Data;
import com.mm.dev.entity.wechat.Data_first;
import com.mm.dev.entity.wechat.Data_name;
import com.mm.dev.entity.wechat.Data_remark;
import com.mm.dev.entity.wechat.JsapiTicket;
import com.mm.dev.entity.wechat.PayInfo;
import com.mm.dev.entity.wechat.SendTemplateMessage;
import com.mm.dev.entity.wechat.SendTextMessage;
import com.mm.dev.entity.wechat.WechatConfig;
import com.mm.dev.entity.wechat.WechatPayConfig;
import com.mm.dev.service.impl.redis.RedisServiceImpl;
import com.mm.dev.service.wechat.IWechatService;
import com.mm.dev.util.MD5Util;
import com.mm.dev.util.MessageUtil;
import com.mm.dev.util.WeixinUtil;

/**
 * @ClassName: IWechatService 
 * @Description: 微信相关业务服务接口
 * @author zhangxy
 * @date 2017年7月31日 上午11:10:36
 */
@SuppressWarnings("deprecation")
@Service
public class WechatServiceImpl implements IWechatService{

	private final Logger logger = LoggerFactory.getLogger(WechatServiceImpl.class);
	
	@Autowired
	private RedisServiceImpl redisService;
	
	@Autowired
	private ConfigProperties configProperties;
	
	private static final String WEIXIN_KEY = "0026571875344ae39183e5fc4b22f590";

	// access_token_url
	private String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	// jsapi_ticket_url
	private String JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
	// download_img_url
	private String DOWNLOAD_IMG_URL = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
	
	//微信上传
	private static final String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	
	//群发接口发送消息
	private String SEND_MESSAGE="https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=ACCESS_TOKEN";
	
	// 获得用户基本信息，头像
	private String GETUSERINFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
		
	// 刷新ACCESS_TOKEN
	private String RESH_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
	
	//获取openId
	private static final String OAUTH2_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=APPSECRET&code=CODE&grant_type=authorization_code";
	
	//客服接口-发消息
	private String MESSAGE_CUSTOM_URL    = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
	
	//发送模板消息接口
	private String MESSAGE_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
	
	// 统一下单
	private static final String WEIXIN_UNIFIED_ORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	
	//设置所属行业
	private  static final String INDUSTRY_URI="https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=ACCESS_TOKEN";
	
	//获取模板ID
	private static final String ADD_TEMPLETE_URL="https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=ACCESS_TOKEN";
	
	//发送模板消息
	private static final String SEND_TEMPLETE_URL="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
		
	//支付成功微信回调URL
	private static final String PAY_CALL_BACK = "http://mall.m.pingbyt.cn/f/wechat/member/user/payCallBack";
	/**
	 * 获取永久二维码:
	 * 用户扫描带场景值二维码时，可能推送以下两种事件：
	 * 如果用户还未关注公众号，则用户可以关注公众号，关注后微信会将带场景值关注事件推送给开发者。
	 * 如果用户已经关注公众号，在用户扫描后会自动进入会话，微信也会将带场景值扫描事件推送给开发者。
	 * 获取带参数的二维码的过程包括两步，首先创建二维码ticket，然后凭借ticket到指定URL换取二维码
	 * 
	 */
	private String CREATE_QRCODE = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKENPOST";
	
	//通过ticket换取二维码
//	private String SHOW_QRCODE = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";
	
	/**
	 * @Description: 获取微信页签
	 * @DateTime:2017年7月31日 下午2:13:38
	 * @throws
	 */
	public WechatConfig getWechatConfig(String url) throws Exception {
		logger.info("获取微信页签开始……" + url);
		WechatConfig config = new WechatConfig();
		config.setAppId(configProperties.getAPPID());
		String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");
		config.setNonceStr(nonceStr);
		String timestamp = Long.toString(System.currentTimeMillis() / 1000);
		config.setTimestamp(timestamp);
		String str = "jsapi_ticket=" + getJsapiTicket().getTicket() + "&noncestr=" + nonceStr + "&timestamp=" + timestamp + "&url=" + url;
		MessageDigest crypt = MessageDigest.getInstance("SHA-1");
		crypt.reset();
		crypt.update(str.getBytes("UTF-8"));
		String signature = WeixinUtil.byteToHex(crypt.digest());
		config.setSignature(signature);
		logger.info("wechatConfig:" + JSONObject.toJSONString(config));
		return config;
	}

	/**
	 * @Description: 获取jsapiTicket
	 * @DateTime:2017年7月31日 下午2:13:17
	 * @throws
	 */
	public JsapiTicket getJsapiTicket() throws Exception {
		JsapiTicket jsapiTicket = new JsapiTicket();
		JSONObject jsonObject = new JSONObject();
		AccessToken accessToken = getAccessToken();
		if(null != accessToken && StringUtils.isNotEmpty(accessToken.getToken())) {
			logger.info("从微信API获取jsapiTicket页签");
			String url = JSAPI_TICKET_URL.replace("ACCESS_TOKEN",accessToken.getToken());
			jsonObject = WeixinUtil.doGetStr(url);
			if (jsonObject != null && "0".equals(jsonObject.getString("errcode"))) {
				jsapiTicket.setErrcode(jsonObject.getString("errcode"));
				jsapiTicket.setErrmsg(jsonObject.getString("errmsg"));
				jsapiTicket.setExpiresIn(jsonObject.getString("expires_in"));
				jsapiTicket.setTicket(jsonObject.getString("ticket"));
			} 
		}
		logger.info("获取jsapiTicket页签返回值==="+JSONObject.toJSONString(jsapiTicket));
		return jsapiTicket;
	}

	/**
	 * @Description: 获取accessToken
	 * @DateTime:2017年7月31日 下午2:13:05
	 * @throws
	 */
	public AccessToken getAccessToken() throws ParseException, IOException {
		logger.info("获取AccessToken开始：");
		AccessToken token = new AccessToken();
		String url = ACCESS_TOKEN_URL.replace("APPID", configProperties.getAPPID()).replace("APPSECRET", configProperties.getAPPSECRET());
//		String accessToken = (String) CacheUtils.get(WechatConstant.EH_CACHE_KEY, WechatConstant.EH_CACHE_KEY_ACCESSTOKEN);
//		if(StringUtils.isBlank(accessToken) || StringUtils.isEmpty(accessToken)){
			logger.info("ehcache获取AccessToken失败：");
			JSONObject jsonObject = WeixinUtil.doGetStr(url);
			if (jsonObject != null) {
				token.setToken(jsonObject.getString("access_token"));
				token.setExpiresIn(jsonObject.getIntValue("expires_in"));
//				CacheUtils.put(WechatConstant.EH_CACHE_KEY, WechatConstant.EH_CACHE_KEY_ACCESSTOKEN, jsonObject.getString("access_token"));
			}			
//		}else {
//			logger.info("ehcache获取AccessToken成功：");
//			logger.info("读取ehcache获取AccessToken:"+accessToken);
//			token.setToken(accessToken);
//		}
		logger.info("AccessToken:==={}",JSONObject.toJSONString(token));
		return token;
	}
	
	/**
	 * @Description: 上传文件到微信服务器
	 * @DateTime:2017年7月31日 下午2:28:02
	 * @return String
	 * @throws
	 */
	public String upload(String filePath, String accessToken, String type) throws IOException, NoSuchAlgorithmException,
			NoSuchProviderException, KeyManagementException {
		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			throw new IOException("文件不存在");
		}

		String url = UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);

		URL urlObj = new URL(url);
		// 连接
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

		con.setRequestMethod("POST");
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false);

		// 设置请求头信息
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");

		// 设置边界
		String BOUNDARY = "----------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

		StringBuilder sb = new StringBuilder();
		sb.append("--");
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");

		byte[] head = sb.toString().getBytes("utf-8");

		// 获得输出流
		OutputStream out = new DataOutputStream(con.getOutputStream());
		// 输出表头
		out.write(head);

		// 文件正文部分
		// 把文件已流文件的方式 推入到url中
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();

		// 结尾部分
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线

		out.write(foot);

		out.flush();
		out.close();

		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		String result = null;
		try {
			// 定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
		} catch (IOException e) {
			logger.error("文件上传失败",e);
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		JSONObject jsonObj = JSONObject.parseObject(result);
		String typeName = "media_id";
		if (!"image".equals(type)) {
			typeName = type + "_media_id";
		}
		String mediaId = jsonObj.getString(typeName);
		return mediaId;
	}
	
	/**
	 * @Description: 从微信下载图片
	 * @DateTime:2017年7月31日 下午2:12:47
	 * @throws
	 */
	public String downloadImage(String mediaId, String rootPath) throws Exception {
		String url = DOWNLOAD_IMG_URL.replace("ACCESS_TOKEN", getAccessToken().getToken()).replace("MEDIA_ID", mediaId);
		@SuppressWarnings({ "resource" })
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		try {
			HttpResponse response = client.execute(get);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return uploadImage(response, rootPath);
			} else {
				return "";
			}
		} catch (Exception e) {
			logger.error("从微信服务器下载失败：" + e);
			return "";
		} finally {
			get.abort();
			get.releaseConnection();
			client.getConnectionManager().shutdown();
		}

	}

	/**
	 * @Description: 上传文件到服务器
	 * @DateTime:2017年7月31日 下午2:12:04
	 * @throws
	 */
	public String uploadImage(HttpResponse httpResponse, String rootPath) throws Exception {
		String imgName = httpResponse.getFirstHeader("Content-disposition").getValue();
		if (StringUtils.isNotEmpty(imgName) && imgName.indexOf("\"") != -1) {
			imgName = imgName.substring(imgName.indexOf("\"") + 1, imgName.lastIndexOf("\""));
			logger.info("从微信服务器下载成功：" + imgName);
		}
		File file = new File(rootPath);
		StringBuilder sb = new StringBuilder("/upload/image");
		sb.append(new SimpleDateFormat("/yyyyMMdd/").format(new Date()));
		File newFile = new File(file.getParent() + sb.toString());
		if (!newFile.exists())
			newFile.mkdirs();
		HttpEntity entity = httpResponse.getEntity();
		InputStream is = null;
		FileOutputStream fos = null;
		ByteArrayOutputStream bos = null;
		try {
			is = entity.getContent();
			bos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = is.read(buffer)) != -1) {
				bos.write(buffer, 0, len);
			}
			fos = new FileOutputStream(newFile.getPath() + File.separator + imgName);
			fos.write(bos.toByteArray());
			return sb.toString() + imgName;
		} catch (Exception e) {
			logger.error("保存图片到服务器失败：",e);
			return "";
		} finally {
			if (is != null) {
				is.close();
			}
			if (fos != null) {
				fos.close();
			}
		}
	}
	
	/**
	 * @Description: 通过openID获取微信头像，昵称等基本信息
	 * @DateTime:2017年7月31日 下午2:10:14
	 * @return Map<String,Object>
	 * @throws
	 */
	public Map<String, Object> getWeChatUserInfo(String openId, String accessToken, String reshToken) throws Exception{
		String nickname = "";
		String sex = "";
		String headimgurl = "";
		// String headimg = "";
		String errcode = "";
		String getUserInfoUrl = "";
		JSONObject getUserInfoUrlObject;
		Map<String, Object> userInfo = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotEmpty(openId)) {
				AccessToken token = getAccessToken();
				accessToken = token.getToken();
				logger.info("accessToken========================" + accessToken);
				// 获取微信签证
				getUserInfoUrl = GETUSERINFO.replace("OPENID", openId).replace("ACCESS_TOKEN", accessToken);
				logger.info("获取微信头像，昵称等基本信息开始=====" + getUserInfoUrl);
				getUserInfoUrlObject = WeixinUtil.doGetStr(getUserInfoUrl);
				logger.info("获取微信头像，昵称等基本信息编译开始=====" + getUserInfoUrlObject);
				if (StringUtils.isNotEmpty(getUserInfoUrlObject.getString("errcode"))) {
					errcode = getUserInfoUrlObject.getString("errcode");
					if (errcode.equals("4001")) {
						token = reshToken(reshToken);
						accessToken = token.getToken();
						getUserInfoUrl = GETUSERINFO.replace("OPENID", openId).replace("ACCESS_TOKEN", accessToken);
						logger.info("重新获取微信头像，昵称等基本信息开始=====" + getUserInfoUrl);
						getUserInfoUrlObject = WeixinUtil.doGetStr(getUserInfoUrl);
						logger.info("重新获取微信头像，昵称等基本信息返回值开始：" + getUserInfoUrlObject);
						logger.info("重新获取微信昵称=====" + getUserInfoUrlObject.getString("nickname"));
						logger.info("重新获取微信性别=====" + getUserInfoUrlObject.getString("sex"));
						logger.info("重新获取微信头像=====" + getUserInfoUrlObject.getString("headimgurl"));
						nickname = getUserInfoUrlObject.getString("nickname");
						sex = getUserInfoUrlObject.getString("sex");
						headimgurl = getUserInfoUrlObject.getString("headimgurl");
						logger.info("重新获取微信头像，昵称等基本信息END=====" + getUserInfoUrl);
						logger.info("重新获取微信头像，昵称等基本信息END=====" + getUserInfoUrlObject);
					}
				} else {
					logger.info("获取微信头像，昵称等基本信息返回值开始：" + getUserInfoUrlObject);
					logger.info("获取微信昵称=====" + getUserInfoUrlObject.getString("nickname"));
					logger.info("获取微信性别=====" + getUserInfoUrlObject.getString("sex"));
					logger.info("获取微信头像=====" + getUserInfoUrlObject.getString("headimgurl"));
					nickname = getUserInfoUrlObject.getString("nickname");
					sex = getUserInfoUrlObject.getString("sex");
					headimgurl = getUserInfoUrlObject.getString("headimgurl");
					logger.info("获取微信头像，昵称等基本信息END=====" + getUserInfoUrl);
					/*
					 * headimg = downloadFromWeChat(headimgurl); //
					 * 把微信头像上传打包服务器上面 logger.info("获取微信头像保存本地数据库地址=====" +
					 * getUserInfoUrlObject.getString("headimgurl"));
					 */
				}
			}
		} catch (Exception e) {
			logger.error("获取微信头像，昵称等基本信息异常",e);
		}
		userInfo.put("nickname", nickname);
		userInfo.put("sex", sex);
		userInfo.put("headimgurl", headimgurl);
		userInfo.put("openId", openId);
		return userInfo;
	}
	
	/**
	 * @Description: 刷新accesstoken
	 * @DateTime:2017年7月31日 下午2:09:47
	 * @return AccessToken
	 * @throws
	 */
	public AccessToken reshToken(String REFRESHTOKEN) throws ParseException, IOException {
		logger.info("重新获取AccessToken开始：");
		AccessToken token = new AccessToken();
		String url = RESH_ACCESS_TOKEN_URL.replace("APPID", configProperties.getAPPID()).replace("REFRESH_TOKEN", REFRESHTOKEN);
		JSONObject jsonObject = WeixinUtil.doGetStr(url);
		if (jsonObject != null) {
			token.setToken(jsonObject.getString("access_token"));
			token.setExpiresIn(jsonObject.getIntValue("expires_in"));
		}
		logger.info(JSONObject.toJSONString(token));
		return token;
	}
	
	/**
	 * @Description: 通过code在微信服务器上获取openid
	 * @DateTime:2017年7月31日 下午2:31:55
	 * @return String
	 * @throws
	 */
	public String queryWxuseridCallTX(String code) throws Exception{
		String openid = null;
		try {
			if (StringUtils.isNotEmpty(code)) {
				if (logger.isDebugEnabled()) {
					logger.debug(" start to repace appid= {},appkey={}", configProperties.getAPPID(), configProperties.getAPPSECRET());
				}
				String oatu2AccessTokenUrl = OAUTH2_ACCESS_TOKEN_URL.replace("APPID", configProperties.getAPPID()).replace("APPSECRET", configProperties.getAPPSECRET()).replace("CODE", code);
				// 获取openid
				logger.info("获取OAuth授权openid开始=====" + oatu2AccessTokenUrl);
				JSONObject oatu2AccessTokenUrlObject = WeixinUtil.doGetStr(oatu2AccessTokenUrl);
				logger.info("获取openid接口返回值：" + oatu2AccessTokenUrlObject);
				logger.info("获取OAuth授权openid结束=====" + oatu2AccessTokenUrlObject.getString("openid"));
				openid = oatu2AccessTokenUrlObject.getString("openid");
			}
		} catch (Exception e) {
			logger.error("OAuth授权获取openid异常",e);
		}
		return openid;
	}

	/**
	 * @Description: 通过code在腾讯服务器上换取access_token,以及openid
	 * @DateTime:2017年7月31日 下午2:33:42
	 * @return Map
	 * @throws
	 */
	public Map<String, Object> queryWxuseridCall(String code) throws Exception{
		String openid = null;
		String accesstoken = null;
		String refreshToken = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotEmpty(code)) {
				if (logger.isDebugEnabled()) {
					logger.debug(" start to repace appid= {},appkey={}", configProperties.getAPPID(), configProperties.getAPPSECRET());
				}

				String oatu2AccessTokenUrl = OAUTH2_ACCESS_TOKEN_URL.replace("APPID", configProperties.getAPPID()).replace("APPSECRET", configProperties.getAPPSECRET()).replace("CODE", code);
				// 获取openid
				logger.info("获取OAuth授权openid开始=====" + oatu2AccessTokenUrl);
				JSONObject oatu2AccessTokenUrlObject = WeixinUtil.doGetStr(oatu2AccessTokenUrl);
				logger.info("获取openid接口返回值：" + oatu2AccessTokenUrlObject);
				logger.info("获取OAuth授权openid结束=====" + oatu2AccessTokenUrlObject.getString("openid"));
				logger.info("获取OAuth授权accesstoken结束=====" + oatu2AccessTokenUrlObject.getString("access_token"));
				logger.info("获取OAuth授权refreshToken结束=====" + oatu2AccessTokenUrlObject.getString("refresh_token"));
				openid = oatu2AccessTokenUrlObject.getString("openid");
				accesstoken = oatu2AccessTokenUrlObject.getString("access_token");
				refreshToken = oatu2AccessTokenUrlObject.getString("refresh_token");
				map.put("openid", openid);
				map.put("accesstoken", accesstoken);
				map.put("refreshToken", refreshToken);
			}
		} catch (Exception e) {
			logger.error("OAuth授权获取openid异常",e);
		}
		return map;
	}
	
    /**
     * @Description: 从服务器上删除图片
     * @DateTime:2017年7月31日 下午1:39:30
     * @return Boolean
     * @throws
     */
    public Boolean deleteFile(String rootPath) throws Exception{
		rootPath=configProperties.getImageUrl()+rootPath;
		logger.info("从服务器上删除图片开始……rootPath=" + rootPath);
		File file = new File(rootPath);
		boolean flag=false;
		try {
			 if (file.exists()){
				 logger.info("------------------------图片存在，开始删----------");
				 file.delete();
				 flag=true;
			 } else {
				 logger.info("要删除的图片不存在===path:{}",rootPath);
			 }
		} catch (Exception e) {
			logger.error("从服务器上删除图片异常:",e);
		}
		return flag;
    }
    
    /**
     * @Description: 根据访问token获取二维码ticketID
     * @DateTime:2017年7月31日 下午1:53:15
     * @return JSONObject
     * @throws
     */
	public JSONObject getQrcodeTicketByToken(String sceneId) throws ParseException, IOException {
		JSONObject jsonObject = null;
		logger.info("获取AccessToken开始：");
		AccessToken accessToken = getAccessToken();
		if(null != accessToken) {
			//post请求微信创建二维码
			String url = CREATE_QRCODE.replace("TOKENPOST", accessToken.getToken());
			Map<String,Object> mapParames = new HashMap<String, Object>();
			Map<String,Object> actionInfoParams = new HashMap<String, Object>();
			Map<String,Object> sceneParams = new HashMap<String, Object>();
			sceneParams.put("scene_str", sceneId);
			actionInfoParams.put("scene", sceneParams);
			mapParames.put("action_name", "QR_LIMIT_STR_SCENE");
			mapParames.put("action_info", actionInfoParams);
			System.out.println(JSONObject.toJSONString(mapParames));
			jsonObject = WeixinUtil.doPostStr(url, JSONObject.toJSONString(mapParames));
		}
		logger.info("根据访问token获取二维码ticket返回值==={}",JSONObject.toJSONString(jsonObject));
		return jsonObject;
	}
	
	/**
	 * @Description: 群发信息
	 * @DateTime:2017年7月31日 下午2:09:16
	 * @return String
	 * @throws
	 */
    public String sendMessages(String outStr) throws ParseException, IOException {
    	logger.info("群发消息开始模板内容==={}",outStr);
    	AccessToken token=getAccessToken();
    	String url=SEND_MESSAGE.replaceAll("ACCESS_TOKEN", token.getToken());
    	JSONObject jsonObject = WeixinUtil.doPostStr(url, outStr);
    	if(null != jsonObject) {
    		logger.info(jsonObject.toString());
    		return jsonObject.toString();
    	} 
    	return null;
    }
    
    /**
     * @Description: 发送客服消息
     * @DateTime:2017年7月31日 下午2:00:55
     * @return String
     * @throws
     */
    public String sendCustomMessages(String outStr,String openId) throws ParseException, IOException {
    	logger.info("发送客服消息开始模板内容:{}===openId:{}",outStr,openId);
    	HashMap<String, Object> customMessageMap = new HashMap<String, Object>();
    	HashMap<String, String> textMap = new HashMap<String, String>();
    	textMap.put("content", outStr);
    	customMessageMap.put("touser", openId);
    	customMessageMap.put("msgtype", "text");
    	customMessageMap.put("text", textMap);
    	AccessToken token=getAccessToken();
    	String url=MESSAGE_CUSTOM_URL.replaceAll("ACCESS_TOKEN", token.getToken());
    	JSONObject jsonObject = WeixinUtil.doPostStr(url, JSONObject.toJSONString(customMessageMap));
    	logger.info(jsonObject.toString());
		return jsonObject.toString();
    }
    
    /**
     * @Description: 发送微信模板消息
     * @DateTime:2017年7月31日 下午2:02:23
     * @return String
     * @throws
     */
    public String sendTemplateMessages(String templateId,String openId) throws ParseException, IOException {
    	logger.info("发送模板消息开始模板ID:{}===openId:{}",templateId,openId);
    	SendTemplateMessage sendTemplateMessage = new SendTemplateMessage();
    	Data data = new Data();
    	Data_first data_first = new Data_first();
    	Data_name name = new Data_name();
    	Data_remark remark = new Data_remark();
    	name.setColor("#173177");
    	name.setValue("ipone7");
    	remark.setColor("#173177");
    	remark.setValue("请尽快与工作人员联系，领取相应奖品！！");
    	data_first.setColor("#173177");
    	data_first.setValue("恭喜您中奖了");
    	data.setFirst(data_first);
    	data.setName(name);
    	data.setRemark(remark);
    	sendTemplateMessage.setTouser(openId);
    	sendTemplateMessage.setTemplate_id(templateId);
    	sendTemplateMessage.setTopcolor("173177");
    	sendTemplateMessage.setData(data);
    	AccessToken token=getAccessToken();
    	String url=MESSAGE_TEMPLATE_URL.replaceAll("ACCESS_TOKEN", token.getToken());
    	logger.info("发送模板消息内容:{}",JSONObject.toJSONString(sendTemplateMessage));
    	JSONObject jsonObject = WeixinUtil.doPostStr(url, JSONObject.toJSONString(sendTemplateMessage).replace("day", "Day"));
    	logger.info(jsonObject.toString());
		return jsonObject.toString();
    }
    
    /**
     * @Description: 给指定用户推送消息
     * @DateTime:2017年7月31日 下午2:07:39
     * @return String
     * @throws
     */
    public String sendMessageByOpenIds(String message,List<String> openIdList) throws ParseException, IOException{
    	SendTextMessage sendTextMessage = new SendTextMessage();
    	String[] openIdArray = openIdList.toArray(new String[openIdList.size()]);
    	sendTextMessage.setTouser(openIdArray);
    	sendTextMessage.setMsgtype("text");
    	HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("content", message);
		sendTextMessage.setText(hashMap);
		String result = sendMessages(JSONObject.toJSONString(sendTextMessage));
    	return result;
    }
    
	/**
	 * 调用统一下单接口
	 * 
	 * @param requestUrl
	 * @param requestMethod
	 * @param outputStr
	 * @return
	 */
	public Map<String, String> httpsRequestToXML(String outputStr) throws Exception{
		Map<String, String> result = new HashMap<>();
		try {
			StringBuffer buffer = WeixinUtil.httpsRequest(WEIXIN_UNIFIED_ORDER, "POST", outputStr);
			result = MessageUtil.parseXml(buffer.toString());
		} catch (ConnectException ce) {
			logger.error("连接超时：",ce);
		} catch (Exception e) {
			logger.error("https请求异常：",e);
		}
		return result;
	};
	
	/**
	 * @Description: 获取WechatPayConfig
	 * @DateTime:2017年7月31日 下午2:28:27
	 * @return WechatPayConfig
	 * @throws
	 */
	public WechatPayConfig getWechatPayConfig(String orderNo, String openId, String amount, HttpServletRequest request) throws Exception {
		// amount = "0.01";
		WechatPayConfig config = new WechatPayConfig();
		config.setAppId(configProperties.getAPPID());
		Long curTimeMillis = System.currentTimeMillis();

		String timestamp = Long.toString(curTimeMillis / 1000);
		config.setTimestamp(timestamp);
		String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");
		config.setNonceStr(nonceStr);

		PayInfo payInfo = new PayInfo();
		payInfo.setAppid(configProperties.getAPPID());
//		payInfo.setMch_id(WEIXIN_MCH_ID);// 商户号
		payInfo.setDevice_info("WEB");// 设备号
		payInfo.setNonce_str(nonceStr);// 随机字符串
		payInfo.setBody("联保科技商品");// 商品描述
		payInfo.setOut_trade_no(orderNo);// 商户订单号
		String val = "0";
		if (StringUtils.isNotEmpty(amount)) {
			String amount2 = WeixinUtil.changeY2F(Double.parseDouble(amount));
			val = amount2.substring(0, amount2.indexOf("."));
		}
		payInfo.setTotal_fee(val);// 支付金额

		// payInfo.setSpbill_create_ip(InetAddress.getLocalHost().getHostAddress());//
		// 终端IP
		payInfo.setSpbill_create_ip(request.getLocalAddr());

		payInfo.setNotify_url(PAY_CALL_BACK);// 接收微信支付异步通知回调地址
		payInfo.setTrade_type("JSAPI");// 交易类型
		payInfo.setOpenid(openId);// 用户标识

		String start = DateFormatUtils.format(curTimeMillis, "yyyyMMddHHmmss");
		payInfo.setTime_start(start);// 交易开始时间

		String expire = DateFormatUtils.format(curTimeMillis + 30 * 60 * 1000, "yyyyMMddHHmmss");
		payInfo.setTime_expire(expire);// 交易结束时间

		payInfo.setSign(getSign(payInfo));// 签名

		logger.info(JSONObject.toJSONString(payInfo));
		String paramesValue = MessageUtil.payInfoToXML(payInfo).replace("__", "_").replace("<![CDATA[", "").replace("]]>", "");

		logger.info("paramesValue:{}", paramesValue);

		Map<String, String> map = httpsRequestToXML(paramesValue);
		logger.info("调用统一下单接口返回参：" + map);
		if (!map.get("return_code").equals("SUCCESS")) {
			logger.info("调用微信统一下单接口失败：" + map.get("return_msg"));
			return null;
		}
		logger.info("调用微信统一下单接口成功：");
		logger.info(map.toString());
		String pkg = "prepay_id=" + map.get("prepay_id");
		config.setPkg(pkg);

		String str = "appId=" + configProperties.getAPPID() + "&nonceStr=" + nonceStr + "&package=" + config.getPkg() + "&signType=MD5" + "&timeStamp=" + timestamp
		// + "&time_start="+start+"&time_expire="+expire
				+ "&key=" + WEIXIN_KEY;
		logger.info("str:" + str);

		String paySign = MD5Util.MD5Encode(str, "UTF-8").toUpperCase();
		logger.info("paySign:" + paySign);
		config.setPaySign(paySign);

		return config;
	}
	
	/**
	 * 获取统一支付签名
	 * 
	 * @param payInfo
	 * @return
	 * @throws Exception
	 */
	public String getSign(PayInfo payInfo) throws Exception {
		String signTemp = "appid=" + payInfo.getAppid() + "&body=" + payInfo.getBody() + "&device_info=" + payInfo.getDevice_info() + "&mch_id="
				+ payInfo.getMch_id() + "&nonce_str=" + payInfo.getNonce_str() + "&notify_url=" + payInfo.getNotify_url() + "&openid="
				+ payInfo.getOpenid() + "&out_trade_no=" + payInfo.getOut_trade_no() + "&spbill_create_ip=" + payInfo.getSpbill_create_ip()
				+ "&time_expire=" + payInfo.getTime_expire() + "&time_start=" + payInfo.getTime_start() + "&total_fee=" + payInfo.getTotal_fee()
				+ "&trade_type=" + payInfo.getTrade_type() + "&key=" + WEIXIN_KEY; // 商户号API安全的API密钥
		String sign = MD5Util.MD5Encode(signTemp, "UTF-8").toUpperCase();
		return sign;
	};
	
	/**
     * 设置所属行业
     * @param outStr
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public String setIndustry(AccessToken token,String outStr)throws ParseException, IOException{
    	logger.info("设置微信所属行业");
    	String url=INDUSTRY_URI.replaceAll("ACCESS_TOKEN", token.getToken());
    	JSONObject jsonObject = WeixinUtil.doPostStr(url, outStr);
    	logger.info(jsonObject.toString());
		return jsonObject.toString();
    }
    
    /**
     * 获得模板ID
     * @param outStr
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public JSONObject getTemplateId(AccessToken token,String outStr)throws ParseException, IOException{
    	logger.info("获得模板ID");
    	String url=ADD_TEMPLETE_URL.replaceAll("ACCESS_TOKEN", token.getToken());
    	JSONObject jsonObject = WeixinUtil.doPostStr(url, outStr);
    	logger.info(jsonObject.toString());
		return jsonObject;
    }
    
    /**
     * @Title: sendTemplateMang 
     * @Description: 发送模板消息
     * @param @param token
     * @param @param outStr
     * @param @return
     * @param @throws ParseException
     * @param @throws IOException
     * @return String
     * @throws
     */
    public String sendTemplateMang(AccessToken token,String outStr)throws ParseException, IOException{
    	logger.info("发送模板消息");
    	String url=SEND_TEMPLETE_URL.replaceAll("ACCESS_TOKEN", token.getToken());
    	JSONObject jsonObject = WeixinUtil.doPostStr(url, outStr);
    	logger.info(jsonObject.toString());
		return jsonObject.toString();
    }
    
    /**
     * @Description: 通过openID获取微信头像，昵称等基本信息
     * @Datatime 2017年8月4日 下午8:59:28 
     * @return Map<String,Object>    返回类型
     */
	public User getWeChatInfo(String openId) {
		User user = null;
		try {
			if (StringUtils.isNotEmpty(openId)) {
				// 获取微信签证
				AccessToken token = getAccessToken();
				String accessToken = token.getToken();
				logger.info("调用获取微信头像接口开始accessToken={}",accessToken);
				String getUserInfoUrl = GETUSERINFO.replace("OPENID", openId).replace("ACCESS_TOKEN", accessToken);
				logger.info("获取微信头像，昵称等基本信息开始=====" + getUserInfoUrl);
				//从微信获取用户基本信息
				JSONObject getUserInfoUrlObject = WeixinUtil.doGetStr(getUserInfoUrl);
				if(null == getUserInfoUrlObject || !StringUtils.isEmpty(getUserInfoUrlObject.getString("errcode"))) {
					logger.info("从微信获取头像，昵称等基本信息返回失败getUserInfoUrlObject==={}",getUserInfoUrlObject);
				} else {
					logger.info("获取微信头像，昵称等基本信息返回值：" + getUserInfoUrlObject);
					user = new User();
					String nickname = URLEncoder.encode((String)getUserInfoUrlObject.get("nickname"), "utf-8");;
					user.setNickName(nickname);
					user.setUserName(nickname);
					user.setSex(getUserInfoUrlObject.getString("sex"));
					user.setHeadimgurl(getUserInfoUrlObject.getString("headimgurl"));
					user.setCountry(getUserInfoUrlObject.getString("country"));
					user.setProvince(getUserInfoUrlObject.getString("province"));
					user.setCity(getUserInfoUrlObject.getString("city"));
					user.setOpenId(openId);
					user.setAttenation(getUserInfoUrlObject.getString("subscribe"));
					String subscribeTime = getUserInfoUrlObject.getString("subscribe_time");
					if(StringUtils.isNotEmpty(subscribeTime)) {
						user.setAttenationTime(DateUtil.getStrTime(subscribeTime));
					}
					user.setLanguage(getUserInfoUrlObject.getString("language"));
				}
			} else {
				logger.info("获取微信头像，昵称等基本信息失败，openId为空");
			}
		} catch (Exception e) {
			logger.info("获取微信头像，昵称等基本信息异常" + e);
		}
		return user;
	}
}
