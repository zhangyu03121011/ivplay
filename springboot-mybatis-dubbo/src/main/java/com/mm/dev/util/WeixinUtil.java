package com.mm.dev.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * 微信工具类
 * 
 * @author Stephen
 * 
 */
@SuppressWarnings("deprecation")
public class WeixinUtil {

	private static final Logger logger = LoggerFactory.getLogger(WeixinUtil.class);
	
	/**
	 * @Description: get请求
	 * @DateTime:2017年7月31日 下午2:26:14
	 * @return JSONObject
	 * @throws
	 */
	public static JSONObject doGetStr(String url) throws ParseException, IOException {
		@SuppressWarnings("resource")
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		JSONObject jsonObject = null;
		HttpResponse httpResponse = httpClient.execute(httpGet);
		HttpEntity entity = httpResponse.getEntity();
		if (entity != null) {
			String result = EntityUtils.toString(entity, "UTF-8");
			EntityUtils.consume(entity);
			jsonObject = JSONObject.parseObject(result);
		}
		return jsonObject;
	}

	/**
	 * @Description: HTTP Get 获取内容
	 * @DateTime:2017年7月31日 下午2:26:32
	 * @return String
	 * @throws
	 */
	public static String doGet(String url, Map<String, String> params, String charset) {
		@SuppressWarnings("resource")
		DefaultHttpClient httpClient = new DefaultHttpClient();
		logger.info("HttpUtil doGet url=====" + url);
		if (params != null) {
			logger.info("HttpUtil doGet params=====" + JSONObject.toJSON(params));
		}
		if (StringUtils.isBlank(url)) {
			return null;
		}
		HttpGet httpGet = new HttpGet(url);
		try {
			if (params != null && !params.isEmpty()) {
				List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
				for (Map.Entry<String, String> entry : params.entrySet()) {
					String value = entry.getValue();
					if (value != null) {
						pairs.add(new BasicNameValuePair(entry.getKey(), value));
					}
				}
				url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
			}
			CloseableHttpResponse response = httpClient.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				httpGet.abort();
				throw new RuntimeException("HttpClient,error status code :" + statusCode);
			}
			HttpEntity entity = response.getEntity();
			String result = null;
			if (entity != null) {
				result = EntityUtils.toString(entity, charset);
				logger.info("HttpUtil result====" + result);
			}
			EntityUtils.consume(entity);
			response.close();
			return result;
		} catch (Exception e) {
			logger.error("doGet error ", e);
		} finally {
			httpGet.abort();
			httpGet.releaseConnection();
			httpClient.getConnectionManager().shutdown();
		}
		return null;
	}

	/**
	 * @Description: POST请求
	 * @DateTime:2017年7月31日 下午2:27:38
	 * @return JSONObject
	 * @throws
	 */
	public static JSONObject doPostStr(String url, String outStr) throws ParseException, IOException {
		@SuppressWarnings("resource")
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(url);
		JSONObject jsonObject = null;
		httpost.setEntity(new StringEntity(outStr, "UTF-8"));
		try {
			HttpResponse response = client.execute(httpost);
			String result = EntityUtils.toString(response.getEntity(), "UTF-8");
			jsonObject = JSONObject.parseObject(result);
		} catch (Exception e) {
			logger.error("doGet error ", e);
		} finally {
			httpost.abort();
			httpost.releaseConnection();
			client.getConnectionManager().shutdown();
		}
		return jsonObject;
	}

	
	
	public static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	/**
	 * @Description: https请求
	 * @DateTime:2017年7月31日 下午2:19:44
	 * @return StringBuffer
	 * @throws
	 */
	public static StringBuffer httpsRequest(String requestUrl, String requestMethod, String output) throws Exception {
		StringBuffer buffer = new StringBuffer();
		try {
			if(StringUtils.isEmpty(requestUrl)) {
				return null;
			}
			URL url = new URL(requestUrl);
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setConnectTimeout(30000); // 设置连接主机超时（单位：毫秒)
			connection.setReadTimeout(30000); // 设置从主机读取数据超时（单位：毫秒)
			connection.setDoOutput(true); // post请求参数要放在http正文内，顾设置成true，默认是false
			connection.setDoInput(true); // 设置是否从httpUrlConnection读入，默认情况下是true
			connection.setUseCaches(false); // Post 请求不能使用缓存
			// 设定传送的内容类型是可序列化的java对象(如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestMethod(requestMethod);// 设定请求的方法为"POST"，默认是GET
			connection.setRequestProperty("Content-Length", requestUrl.length() + "");
			if (null != output) {
				OutputStream outputStream = connection.getOutputStream();
				outputStream.write(output.getBytes("UTF-8"));
				outputStream.flush();
				outputStream.close();
			}
			// 从输入流读取返回内容
			InputStream inputStream = connection.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			connection.disconnect();
		} catch (Exception e) {
			logger.error("https请求接口异常url:{}",requestUrl,e);
		}
		return buffer;
	}

	/**
	 * 将元为单位的转换为分 （乘100）
	 * 
	 * @param amount
	 * @return
	 */
	public static String changeY2F(Double amount) {
		return BigDecimal.valueOf(amount).multiply(new BigDecimal(100)).toString();
	}
	
	/**
	 * @Description: 将分为单位的转换为元 （除以100）
	 * @DateTime:2017年7月31日 下午2:07:56
	 * @return String
	 * @throws
	 */
	public static String changeC2F(Double amount) {
		return BigDecimal.valueOf(amount).divide(new BigDecimal(100)).toString();
	}
}
