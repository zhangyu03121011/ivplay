package com.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.common.base.common.BaseLogger;

/**
 * 主键工具类
 * 
 * @author: HuiJunLuo
 * @date:2015年12月28日
 * @Copyright:Copyright (c) xxxx有限公司 2014 ~ 2015 版权所有
 */
public class DateUtil extends BaseLogger {

	private static DateUtil dateUtil = null;

	public synchronized static DateUtil getInstance() {
		if (dateUtil == null) {
			dateUtil = new DateUtil();
		}
		return dateUtil;
	}

	private DateUtil() {
	}

	/**
	 * 获取当前时间
	 * 
	 * @return
	 * @throws Exception
	 */
	public Date getCurrDate() {
		try {
			return Calendar.getInstance().getTime();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new Date();
	}

	/**
	 * 两个时间之间相差距离多少天
	 * 
	 * @param one
	 *            时间参数 1：
	 * @param two
	 *            时间参数 2：
	 * @return 相差天数
	 */
	public static long getDistanceDays(String str1, String str2) throws Exception {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date one;
		Date two;
		long days = 0;
		try {
			one = df.parse(str1);
			two = df.parse(str2);
			long time1 = one.getTime();
			long time2 = two.getTime();
			long diff;
			if (time1 < time2) {
				diff = time2 - time1;
			} else {
				diff = time1 - time2;
			}
			days = diff / (1000 * 60 * 60 * 24);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return days;
	}

	/**
	 * 两个时间相差距离多少天多少小时多少分多少秒
	 * 
	 * @param str1
	 *            时间参数 1 格式：1990-01-01 12:00:00
	 * @param str2
	 *            时间参数 2 格式：2009-01-01 12:00:00
	 * @return long[] 返回值为：{天, 时, 分, 秒}
	 */
	public static long[] getDistanceTimes(String str1, String str2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date one;
		Date two;
		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;
		try {
			one = df.parse(str1);
			two = df.parse(str2);
			long time1 = one.getTime();
			long time2 = two.getTime();
			long diff;
			if (time1 < time2) {
				diff = time2 - time1;
			} else {
				diff = time1 - time2;
			}
			day = diff / (24 * 60 * 60 * 1000);
			hour = (diff / (60 * 60 * 1000) - day * 24);
			min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
			sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long[] times = { day, hour, min, sec };
		return times;
	}

	/**
	 * 两个时间相差距离多少天多少小时多少分多少秒
	 * 
	 * @param str1
	 *            时间参数 1 格式：1990-01-01 12:00:00
	 * @param str2
	 *            时间参数 2 格式：2009-01-01 12:00:00
	 * @return String 返回值为：xx天xx小时xx分xx秒
	 */
	public static String getDistanceTime(String str1, String str2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date one;
		Date two;
		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;
		try {
			one = df.parse(str1);
			two = df.parse(str2);
			long time1 = one.getTime();
			long time2 = two.getTime();
			long diff;
			if (time1 < time2) {
				diff = time2 - time1;
			} else {
				diff = time1 - time2;
			}
			day = diff / (24 * 60 * 60 * 1000);
			hour = (diff / (60 * 60 * 1000) - day * 24);
			min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
			sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return day + "天" + hour + "小时" + min + "分" + sec + "秒";
	}

	public static String getDistanceTime(Date date1, Date date2) {
		long day = 0;
		long hour = 0;
		try {
			long time1 = date1.getTime();
			long time2 = date2.getTime();
			long diff;
			if (time1 < time2) {
				diff = time2 - time1;
			} else {
				diff = time1 - time2;
			}
			day = diff / (24 * 60 * 60 * 1000);
			hour = (diff / (60 * 60 * 1000) - day * 24);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (day > 0) {
			return day + "天" + hour + "小时";
		} else {
			return hour + "小时";
		}

	}

}
