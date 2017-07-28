package com.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.common.base.common.BaseLogger;

/**
 * 正则工具类
 * 
 * @author: HuiJunLuo
 * @date:2016年1月9日
 * @Copyright:Copyright (c) xxxx有限公司 2014 ~ 2015 版权所有
 */
public class PatternUtil extends BaseLogger {

	private static PatternUtil patternUtil = null;

	public synchronized static PatternUtil getInstance() {
		if (patternUtil == null) {
			patternUtil = new PatternUtil();
		}
		return patternUtil;
	}

	private PatternUtil() {
	}

	/**
	 * 匹配是否数字
	 * 
	 * @param str
	 * @return
	 */
	public boolean matchNumber(String str) {
		try {
			Pattern pattern = Pattern.compile("[0-9]*");
			return pattern.matcher(str).matches();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 匹配用户名规则
	 * 
	 * @param str
	 * @return
	 */
	public boolean matchName(String str) {
		try {
			String regex = "[\\w\u4e00-\u9fa5]{2,10}(?<!_)";
			Pattern pattern = Pattern.compile(regex);
			return pattern.matcher(str).matches();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 匹配密码规则(字母数字组合)
	 * 
	 * @param str
	 * @return
	 */
	public boolean matchPassWord(String str) {
		try {
			String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
			Pattern pattern = Pattern.compile(regex);
			return pattern.matcher(str).matches();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 大陆号码或香港号码均可
	 */
	public boolean isPhoneLegal(String str) throws PatternSyntaxException {
		return isChinaPhoneLegal(str) || isHKPhoneLegal(str);
	}

	/**
	 * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数 此方法中前三位格式有： 13+任意数 15+除4的任意数 18+除1和4的任意数
	 * 17+除9的任意数 147
	 */
	public boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
		String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
		regExp = "^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-9])|(147))\\d{8}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 香港手机号码8位数，5|6|8|9开头+7位任意数
	 */
	public boolean isHKPhoneLegal(String str) throws PatternSyntaxException {
		String regExp = "^(5|6|8|9)\\d{7}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}

}
