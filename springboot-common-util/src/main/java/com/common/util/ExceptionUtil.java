package com.common.util;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;

/**
 * 异常工具类
 * 
 * @author: HuiJunLuo
 * @date:2016年1月20日
 * @Copyright:Copyright (c) xxxx有限公司 2014 ~ 2015 版权所有
 */
public class ExceptionUtil {

	private static ExceptionUtil exceptionUtil = null;

	public synchronized static ExceptionUtil getInstance() {
		if (exceptionUtil == null) {
			exceptionUtil = new ExceptionUtil();
		}
		return exceptionUtil;
	}

	private ExceptionUtil() {
	}

	/**
	 * 获取异常信息
	 * 
	 * @param e
	 * @return
	 */
	public String getExceptionMsg(Exception e) {
		String msg = null;
		if (e != null) {
			msg = e.getMessage();
			if (ArrayUtils.isNotEmpty(e.getStackTrace())) {
				StackTraceElement element = e.getStackTrace()[0];
				if (element != null) {
					msg = "[" + StringUtils.substringAfterLast(element.getClassName(), ".") + "]["
							+ element.getMethodName() + "][lineNumber=" + element.getLineNumber() + "]" + msg;
				}
			}
		}
		return msg;
	}

	/**
	 * 获取异常信息
	 * 
	 * @param e
	 * @return
	 */
	public String getExceptionMsg(Exception e, Object obj) {
		String msg = null;
		if (e != null) {
			msg = e.getMessage();
			if (ArrayUtils.isNotEmpty(e.getStackTrace())) {
				StackTraceElement element = e.getStackTrace()[0];
				if (element != null) {
					msg = "[" + StringUtils.substringAfterLast(element.getClassName(), ".") + "]["
							+ element.getMethodName() + "][entity=" + obj == null ? null
									: JSON.toJSONString(obj) + "][lineNumber=" + element.getLineNumber() + "]" + msg;
				}
			}
		}
		return msg;
	}
}
