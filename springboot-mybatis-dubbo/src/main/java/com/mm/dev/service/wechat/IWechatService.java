package com.mm.dev.service.wechat;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;

import com.alibaba.fastjson.JSONObject;
import com.mm.dev.entity.wechat.AccessToken;
import com.mm.dev.entity.wechat.JsapiTicket;
import com.mm.dev.entity.wechat.PayInfo;
import com.mm.dev.entity.wechat.WechatConfig;
import com.mm.dev.entity.wechat.WechatPayConfig;

/**
 * @ClassName: IWechatService 
 * @Description: 微信相关业务服务接口
 * @author zhangxy
 * @date 2017年7月31日 上午11:10:36
 */
public interface IWechatService {

	public WechatConfig getWechatConfig(String url) throws Exception;

	/**
	 * @Description: 获取jsapiTicket
	 * @DateTime:2017年7月31日 下午2:13:17
	 * @throws
	 */
	public JsapiTicket getJsapiTicket() throws Exception;

	/**
	 * @Description: 获取accessToken
	 * @DateTime:2017年7月31日 下午2:13:05
	 * @throws
	 */
	public AccessToken getAccessToken() throws Exception;
	
	/**
	 * @Description: 上传文件到微信服务器
	 * @DateTime:2017年7月31日 下午2:28:02
	 * @return String
	 * @throws
	 */
	public String upload(String filePath, String accessToken, String type) throws Exception;
	
	/**
	 * @Description: 从微信下载图片
	 * @DateTime:2017年7月31日 下午2:12:47
	 * @throws
	 */
	public String downloadImage(String mediaId, String rootPath) throws Exception;

	/**
	 * @Description: 上传文件到服务器
	 * @DateTime:2017年7月31日 下午2:12:04
	 * @throws
	 */
	public String uploadImage(HttpResponse httpResponse, String rootPath) throws Exception;
	
	/**
	 * @Description: 通过openID获取微信头像，昵称等基本信息
	 * @DateTime:2017年7月31日 下午2:10:14
	 * @return Map<String,Object>
	 * @throws
	 */
	public Map<String, Object> getWeChatUserInfo(String openId, String accessToken, String reshToken) throws Exception;
	
	/**
	 * @Description: 刷新accesstoken
	 * @DateTime:2017年7月31日 下午2:09:47
	 * @return AccessToken
	 * @throws
	 */
	public AccessToken reshToken(String REFRESHTOKEN) throws Exception;
	
	/**
	 * @Description: 通过code在微信服务器上获取openid
	 * @DateTime:2017年7月31日 下午2:31:55
	 * @return String
	 * @throws
	 */
	public String queryWxuseridCallTX(String code) throws Exception;

	/**
	 * @Description: 通过code在腾讯服务器上换取access_token,以及openid
	 * @DateTime:2017年7月31日 下午2:33:42
	 * @return Map
	 * @throws
	 */
	public Map<String, Object> queryWxuseridCall(String code) throws Exception;
	
    /**
     * @Description: 从服务器上删除图片
     * @DateTime:2017年7月31日 下午1:39:30
     * @return Boolean
     * @throws
     */
    public Boolean deleteFile(String rootPath) throws Exception;
    
    /**
     * @Description: 根据访问token获取二维码ticketID
     * @DateTime:2017年7月31日 下午1:53:15
     * @return JSONObject
     * @throws
     */
	public JSONObject getQrcodeTicketByToken(String sceneId) throws Exception;
	
	/**
	 * @Description: 群发信息
	 * @DateTime:2017年7月31日 下午2:09:16
	 * @return String
	 * @throws
	 */
    public String sendMessages(String outStr) throws Exception;
    
    /**
     * @Description: 发送客服消息
     * @DateTime:2017年7月31日 下午2:00:55
     * @return String
     * @throws
     */
    public String sendCustomMessages(String outStr,String openId) throws Exception;
    
    /**
     * @Description: 发送微信模板消息
     * @DateTime:2017年7月31日 下午2:02:23
     * @return String
     * @throws
     */
    public String sendTemplateMessages(String templateId,String openId) throws Exception;
    
    /**
     * @Description: 给指定用户推送消息
     * @DateTime:2017年7月31日 下午2:07:39
     * @return String
     * @throws
     */
    public String sendMessageByOpenIds(String message,List<String> openIdList) throws Exception;
    
	/**
	 * 调用统一下单接口
	 * 
	 * @param requestUrl
	 * @param requestMethod
	 * @param outputStr
	 * @return
	 */
	public Map<String, String> httpsRequestToXML(String outputStr) throws Exception;
	
	/**
	 * @Description: 获取WechatPayConfig
	 * @DateTime:2017年7月31日 下午2:28:27
	 * @return WechatPayConfig
	 * @throws
	 */
	public WechatPayConfig getWechatPayConfig(String orderNo, String openId, String amount, HttpServletRequest request) throws Exception;
	
	/**
	 * 获取统一支付签名
	 * 
	 * @param payInfo
	 * @return
	 * @throws Exception
	 */
	public String getSign(PayInfo payInfo) throws Exception;
	
	/**
     * 设置所属行业
     * @param outStr
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public String setIndustry(AccessToken token,String outStr) throws Exception;
    
    /**
     * 获得模板ID
     * @param outStr
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public JSONObject getTemplateId(AccessToken token,String outStr) throws Exception;
    
    /**
     * 发送模板消息
     * @Title: sendTemplateMang 
     * @Description: TODO
     * @param @param token
     * @param @param outStr
     * @param @return
     * @param @throws ParseException
     * @param @throws IOException
     * @return String
     * @throws
     */
    public String sendTemplateMang(AccessToken token,String outStr) throws Exception;
}
