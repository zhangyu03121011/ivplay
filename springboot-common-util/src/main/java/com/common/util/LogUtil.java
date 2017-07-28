package com.common.util;

/**
 * 日志工具
 * 
 * @author: HuiJunLuo
 * @date:2015年12月28日
 * @Copyright:Copyright (c) xxxx有限公司 2014 ~ 2015 版权所有
 */
public class LogUtil {
	
	/**
	 * @author HuiJunLuo
	 * @date 2016年7月1日下午2:36:57
	 * @Desc
	 * @param clazz 类名
	 * @param method 方法名
	 * @param msg 信息
	 * @return
	 */
	public static String msg(String clazz, String method, String msg){
		//"[BaseApiController][update]"
		return new StringBuilder().append("[")
				.append(clazz)
				.append("][")
				.append(method)
				.append("]")
				.append(msg)
				.toString();
	}

	public static String msg(String clazz, String method, String msg, String ip){
		//"[BaseApiController][update]"
		return new StringBuilder().append("[")
				.append(clazz)
				.append("][")
				.append(method)
				.append("] msg=")
				.append(msg)
				.append("ip=")
				.append(ip)
				.toString();
	}

}
