package com.common.util;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderNoGenerateUtil {
	
	private static int number;
	
	/**
	 * 12位时间戳+4位自增数字
	 * @return
	 */
	public static String getOrderNo(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		Date date = new Date();
		String newDate =  sdf.format(date);
		
		NumberFormat nf = NumberFormat.getInstance();
		//设置是否使用分组
		nf.setGroupingUsed(false);
		//设置最大整数位数
		nf.setMaximumIntegerDigits(4);
		//设置最小整数位数    
		nf.setMinimumIntegerDigits(4);
		
		if(number >= 999) {
			number = 0;
		}
		String value = nf.format(++number);
		String orderNo = newDate + value;
		return orderNo;
	}
}
