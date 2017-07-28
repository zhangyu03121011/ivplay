package com.common.util;

import java.util.regex.Pattern;

import com.common.base.common.BaseLogger;

/**
 * 中文工具类
 * 
 * @author: HuiJunLuo
 * @date:2015年12月28日
 * @Copyright:Copyright (c) xxxx有限公司 2014 ~ 2015 版权所有
 */
public class ChineseUtil extends BaseLogger {

	private static ChineseUtil chineseUtil = null;

	public synchronized static ChineseUtil getInstance() {
		if (chineseUtil == null) {
			chineseUtil = new ChineseUtil();
		}
		return chineseUtil;
	}

	private ChineseUtil() {
	}

	public static void main(String[] args) {
		System.out.println(getInstance().isChinese('我'));
		String[] strArr = new String[] { "www.micmiu.com", "!@#$%^&*()_+{}[]|\"'?/:;<>,.", "！￥……（）——：；“”‘’《》，。？、",
				"不要啊", "やめて", "韩佳人", "???" };
		for (String str : strArr) {
			System.out.println("===========> 测试字符串：" + str);
			System.out.println(
					"正则判断结果：" + getInstance().isChineseByREG(str) + " -- " + getInstance().isChineseByName(str));
			System.out.println("Unicode判断结果 ：" + getInstance().isChinese(str));
			System.out.println("详细判断列表：");
			char[] ch = str.toCharArray();
			for (int i = 0; i < ch.length; i++) {
				char c = ch[i];
				System.out.println(c + " --> " + (getInstance().isChinese(c) ? "是" : "否"));
			}
		}
	}

	// 根据Unicode编码完美的判断中文汉字和符号
	public boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
			return true;
		}
		return false;
	}

	// 完整的判断中文汉字和符号
	public boolean isChinese(String strName) {
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c)) {
				return true;
			}
		}
		return false;
	}

	// 只能判断部分CJK字符（CJK统一汉字）
	public boolean isChineseByREG(String str) {
		if (str == null) {
			return false;
		}
		Pattern pattern = Pattern.compile("[\\u4E00-\\u9FBF]+");
		return pattern.matcher(str.trim()).find();
	}

	// 只能判断部分CJK字符（CJK统一汉字）
	public boolean isChineseByName(String str) {
		if (str == null) {
			return false;
		}
		// 大小写不同：\\p 表示包含，\\P 表示不包含
		// \\p{Cn} 的意思为 Unicode 中未被定义字符的编码，\\P{Cn} 就表示 Unicode中已经被定义字符的编码
		String reg = "\\p{InCJK Unified Ideographs}&&\\P{Cn}";
		Pattern pattern = Pattern.compile(reg);
		return pattern.matcher(str.trim()).find();
	}
}
