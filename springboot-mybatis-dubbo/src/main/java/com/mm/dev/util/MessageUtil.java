package com.mm.dev.util;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.mm.dev.entity.wechat.Image;
import com.mm.dev.entity.wechat.ImageMessage;
import com.mm.dev.entity.wechat.Music;
import com.mm.dev.entity.wechat.MusicMessage;
import com.mm.dev.entity.wechat.News;
import com.mm.dev.entity.wechat.NewsMessage;
import com.mm.dev.entity.wechat.PayInfo;
import com.mm.dev.entity.wechat.TextMessage;
import com.thoughtworks.xstream.XStream;

/**
 * 消息封装类
 * 
 * @author Stephen
 *
 */
public class MessageUtil {

	public static final String MESSAGE_TEXT = "text";
	public static final String MESSAGE_NEWS = "news";
	public static final String MESSAGE_IMAGE = "image";
	public static final String MESSAGE_VOICE = "voice";
	public static final String MESSAGE_MUSIC = "music";
	public static final String MESSAGE_VIDEO = "video";
	public static final String MESSAGE_LINK = "link";
	public static final String MESSAGE_LOCATION = "location";
	public static final String MESSAGE_EVNET = "event";
	public static final String MESSAGE_SUBSCRIBE = "subscribe";
	public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";
	public static final String MESSAGE_CLICK = "CLICK";
	public static final String MESSAGE_VIEW = "VIEW";
	public static final String MESSAGE_SCANCODE = "scancode_push";
	public static final String MESSAGE_SCAN = "SCAN";

	/**
	 * xml转为map集合
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException {
		Map<String, String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();

		InputStream ins = request.getInputStream();
		Document doc = reader.read(ins);

		Element root = doc.getRootElement();

		@SuppressWarnings("unchecked")
		List<Element> list = root.elements();

		for (Element e : list) {
			map.put(e.getName(), e.getText());
		}
		ins.close();
		return map;
	}

	/**
	 * 字符串转map
	 * 
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXml(String xml) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		Document document = DocumentHelper.parseText(xml);
		Element root = document.getRootElement();
		List<Element> elementList = root.elements();
		for (Element e : elementList)
			map.put(e.getName(), e.getText());
		return map;
	}

	/**
	 * 将文本消息对象转为xml
	 * 
	 * @param textMessage
	 * @return
	 */
	public static String textMessageToXml(TextMessage textMessage) {
		XStream xstream = new XStream();
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}

	public static String payInfoToXML(PayInfo pi) {
		XStream xstream = new XStream();
		xstream.alias("xml", pi.getClass());
		return xstream.toXML(pi);
	}

	/**
	 * 组装文本消息
	 * 
	 * @param toUserName
	 * @param fromUserName
	 * @param content
	 * @return
	 */
	public static String initText(String toUserName, String fromUserName, String content) {
		TextMessage text = new TextMessage();
		text.setFromUserName(toUserName);
		text.setToUserName(fromUserName);
		text.setMsgType(MessageUtil.MESSAGE_TEXT);
		text.setCreateTime(new Date().getTime());
		text.setContent(content);
		return textMessageToXml(text);
	}

	/**
	 * 主菜单
	 * 
	 * @return
	 */
	public static String menuText() {
		StringBuffer sb = new StringBuffer();
		sb.append("欢迎您的关注");
		sb.append("上传照片和视频，帮您生成模糊图和短链接，好友打赏才能看照片和视频");
		sb.append("点击左下方菜单“生成打赏”，等待返回短链接和带二维码的模糊图，长按保存模糊图或复制短链接，然后发到朋友圈和群里，或者群发给好友");
		sb.append("收到其他人打赏后，点击右下方菜单“收获赏金”完成提现");			
		sb.append("申明：这只是一个用于好友和粉丝打赏的软件工具，平台只提供技术服务，每个人都可以拿来自己运营，并且要求抵制各种非法内容。平台协助监管，会人工审核所有内容，发布违规内容将一律被删除，相关责任与后果自己承担，且平台保留做出相关处理的权利，敬请大家共同维护绿色和谐的网络环境");	
		sb.append("回复？调出此提示。");
		return sb.toString();
	}

	public static String firstMenu() {
		StringBuffer sb = new StringBuffer();
		sb.append("欢迎您的关注，请绑定手机号码操作：\n");
		sb.append("<a href='/page/weixin/user.html'>点击绑定</a>\n\n");
		return sb.toString();
	}

	public static String secondMenu() {
		StringBuffer sb = new StringBuffer();
		sb.append("欢迎您再次关注!\n");
		return sb.toString();
	}

	public static String welcomeMessage() {
		StringBuffer sb = new StringBuffer();
		sb.append("欢迎您的关注");
		sb.append("上传照片和视频，帮您生成模糊图和短链接，好友打赏才能看照片和视频");
		sb.append("点击左下方菜单“生成打赏”，等待返回短链接和带二维码的模糊图，长按保存模糊图或复制短链接，然后发到朋友圈和群里，或者群发给好友");
		sb.append("收到其他人打赏后，点击右下方菜单“收获赏金”完成提现");			
		sb.append("申明：这只是一个用于好友和粉丝打赏的软件工具，平台只提供技术服务，每个人都可以拿来自己运营，并且要求抵制各种非法内容。平台协助监管，会人工审核所有内容，发布违规内容将一律被删除，相关责任与后果自己承担，且平台保留做出相关处理的权利，敬请大家共同维护绿色和谐的网络环境");	
		sb.append("回复？调出此提示。");
		return sb.toString();
	}

	public static String threeMenu() {
		StringBuffer sb = new StringBuffer();
		sb.append("词组翻译使用指南\n\n");
		sb.append("使用示例：\n");
		sb.append("翻译足球\n");
		sb.append("翻译中国足球\n");
		sb.append("翻译football\n\n");
		sb.append("回复？显示主菜单。");
		return sb.toString();
	}

	/**
	 * 图文消息转为xml
	 * 
	 * @param newsMessage
	 * @return
	 */
	public static String newsMessageToXml(NewsMessage newsMessage) {
		XStream xstream = new XStream();
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new News().getClass());
		return xstream.toXML(newsMessage);
	}

	/**
	 * 图片消息转为xml
	 * 
	 * @param imageMessage
	 * @return
	 */
	public static String imageMessageToXml(ImageMessage imageMessage) {
		XStream xstream = new XStream();
		xstream.alias("xml", imageMessage.getClass());
		return xstream.toXML(imageMessage);
	}

	/**
	 * 音乐消息转为xml
	 * 
	 * @param musicMessage
	 * @return
	 */
	public static String musicMessageToXml(MusicMessage musicMessage) {
		XStream xstream = new XStream();
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}

	/**
	 * 图文消息的组装
	 * 
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initNewsMessage(String toUserName, String fromUserName) {
		String message = null;
		List<News> newsList = new ArrayList<News>();
		NewsMessage newsMessage = new NewsMessage();

		News news = new News();
		news.setTitle("慕课网介绍");
		news.setDescription("慕课网是垂直的互联网IT技能免费学习网站。以独家视频教程、在线编程工具、学习计划、问答社区为核心特色。在这里，你可以找到最好的互联网技术牛人，也可以通过免费的在线公开视频课程学习国内领先的互联网IT技术。慕课网课程涵盖前端开发、PHP、Html5、Android、iOS、Swift等IT前沿技术语言，包括基础课程、实用案例、高级分享三大类型，适合不同阶段的学习人群。");
		news.setPicUrl("http://zapper.tunnel.mobi/Weixin/image/imooc.jpg");
		news.setUrl("www.imooc.com");

		newsList.add(news);

		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MESSAGE_NEWS);
		newsMessage.setArticles(newsList);
		newsMessage.setArticleCount(newsList.size());

		message = newsMessageToXml(newsMessage);
		return message;
	}

	/**
	 * 组装图片消息
	 * 
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initImageMessage(String toUserName, String fromUserName) {
		String message = null;
		Image image = new Image();
		image.setMediaId("JTH8vBl0zDRlrrn2bBnMleySuHjVbMhyAo0U2x7kQyd1ciydhhsVPONbnRrKGp8m");
		ImageMessage imageMessage = new ImageMessage();
		imageMessage.setFromUserName(toUserName);
		imageMessage.setToUserName(fromUserName);
		imageMessage.setMsgType(MESSAGE_IMAGE);
		imageMessage.setCreateTime(new Date().getTime());
		imageMessage.setImage(image);
		message = imageMessageToXml(imageMessage);
		return message;
	}

	/**
	 * 组装音乐消息
	 * 
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initMusicMessage(String toUserName, String fromUserName) {
		String message = null;
		Music music = new Music();
		music.setThumbMediaId("WsHCQr1ftJQwmGUGhCP8gZ13a77XVg5Ah_uHPHVEAQuRE5FEjn-DsZJzFZqZFeFk");
		music.setTitle("see you again");
		music.setDescription("速7片尾曲");
		music.setMusicUrl("http://zapper.tunnel.mobi/Weixin/resource/See You Again.mp3");
		music.setHQMusicUrl("http://zapper.tunnel.mobi/Weixin/resource/See You Again.mp3");

		MusicMessage musicMessage = new MusicMessage();
		musicMessage.setFromUserName(toUserName);
		musicMessage.setToUserName(fromUserName);
		musicMessage.setMsgType(MESSAGE_MUSIC);
		musicMessage.setCreateTime(new Date().getTime());
		musicMessage.setMusic(music);
		message = musicMessageToXml(musicMessage);
		return message;
	}

	/**
	 * 组装回调xml
	 * 
	 * @param return_code
	 * @param return_msg
	 * @return
	 */
	public static String setXML(String return_code, String return_msg) {
		return "<xml><return_code><![CDATA[" + return_code + "]]></return_code><return_msg><![CDATA[" + return_msg + "]]></return_msg></xml>";
	}
}
