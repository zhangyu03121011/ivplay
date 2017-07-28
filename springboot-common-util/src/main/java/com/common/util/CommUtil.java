package com.common.util;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import com.common.base.common.BaseLogger;
import com.common.base.constant.PrivilegeConstant;

/**
 * 公用工具类
 * 
 * @author: HuiJunLuo
 * @date:2015年12月28日
 * @Copyright:Copyright (c) xxxx有限公司 2014 ~ 2015 版权所有
 */
public class CommUtil extends BaseLogger {

	private static CommUtil commUtil = null;

	public synchronized static CommUtil getInstance() {
		if (commUtil == null) {
			commUtil = new CommUtil();
		}
		return commUtil;
	}

	private CommUtil() {
	}

	/**
	 * 获取第一个数字
	 * 
	 * @param str
	 * @return
	 */
	public int getFirstNum(String str) {
		try {
			char[] charArray = str.toCharArray();
			for (char c : charArray) {
				if (Character.isDigit(c) && Integer.valueOf(String.valueOf(c)) > 0) {
					return Integer.valueOf(String.valueOf(c));
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return 0;
	}

	/**
	 * 获取最后一个数字
	 * 
	 * @param str
	 * @return
	 */
	public int getLastNum(String str) {
		try {
			char[] charArray = str.toCharArray();
			for (int i = charArray.length - 1; i >= 0; i--) {
				if (Character.isDigit(charArray[i]) && Integer.valueOf(String.valueOf(charArray[i])) > 0) {
					return Integer.valueOf(String.valueOf(charArray[i]));
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return 0;
	}

	public String replaceAllBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	public String replaceExcelBlank(String str) {
		if (str == null) {
			return "";
		}
		return str.replace(String.valueOf((char) 160), " ").trim();
	}

	/**
	 * 生成token
	 * 
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public String getToken(String sessionId) throws Exception {
		try {

			if (StringUtils.isEmpty(sessionId)) {
				throw new Exception("[getToken][sessionId=" + sessionId + "]");
			}

			// 生成8位随机key
			String randomKey = CodeUtil.getInstance().getSecurityCode(8);

			logger.debug("randomKey=" + randomKey);

			// token生成规则（xxxxx+base64(xxx+base64(PBE(反转(sessionId))))）sessionId反转，再PBE加密，xxx为随机码

			// 根据星期截取
			// Calendar calendar = Calendar.getInstance();
			// int len = calendar.get(Calendar.DAY_OF_WEEK);
			// if (len > 7) {
			// len = 7;
			// }

			String token = randomKey.substring(0, 3) + Base64Util.getInstance()
					.encodeByte(PBEUtil.getInstance().encrypt(StringUtils.reverse(sessionId).getBytes(),
							PrivilegeConstant.PBE_PASSWORD_KEY, randomKey.getBytes()));

			return randomKey.substring(3) + Base64.encodeBase64String(token.getBytes());

			// String token = randomKey.substring(0, len) +
			// Base64Util.getInstance()
			// .encodeByte(PBEUtil.getInstance().encrypt(StringUtils.reverse(sessionId).getBytes(),
			// PrivilegeConstant.PBE_PASSWORD_KEY, randomKey.getBytes()));
			//
			// return randomKey.substring(len) +
			// Base64.encodeBase64String(token.getBytes());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 解密token
	 * 
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public String getTokenVal(String token) throws Exception {
		try {

			if (StringUtils.isEmpty(token)) {
				throw new Exception("[getTokenVal][token=" + token + "]");
			}

			// token生成规则（xxxxx+base64(xxx+base64(PBE(反转(sessionId))))）sessionId反转，再PBE加密，xxx为随机码

			// 根据星期截取
			// Calendar calendar = Calendar.getInstance();
			// int len = calendar.get(Calendar.DAY_OF_WEEK);
			// if (len > 7) {
			// len = 7;
			// }

			// 获取8位随机码
			// String salt = token.substring(0, 8 - len);
			// token = Base64Util.getInstance().decodeStr(token.substring(8 -
			// len));
			// salt = token.substring(0, len) + salt;
			// token = token.substring(len);
			// String sessionId = StringUtils
			// .reverse(new
			// String(PBEUtil.getInstance().decrypt(Base64Util.getInstance().decodeByte(token),
			// PrivilegeConstant.PBE_PASSWORD_KEY, salt.getBytes())));

			String salt = token.substring(0, 5);
			token = Base64Util.getInstance().decodeStr(token.substring(5));
			salt = token.substring(0, 3) + salt;
			token = token.substring(3);
			String sessionId = StringUtils
					.reverse(new String(PBEUtil.getInstance().decrypt(Base64Util.getInstance().decodeByte(token),
							PrivilegeConstant.PBE_PASSWORD_KEY, salt.getBytes())));

			return sessionId;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 密码加密生成
	 * 
	 * @param idCode
	 * @param password
	 * @param randomCode
	 * @return
	 * @throws Exception
	 */
	public String getPassword(String idCode, String password, String randomCode) throws Exception {
		try {

			if (StringUtils.isEmpty(randomCode) || StringUtils.isEmpty(password)) {
				throw new Exception("[getPassword][randomCode=" + randomCode + "][idCode=" + idCode + "][password="
						+ password + "]");
			}

			// 密码加密规则( 反转(xxx+md5(反转(随机码)+反转(pwd)+随机码)+base64(xxx)))
			password = randomCode.substring(getLastNum(idCode))
					+ Md5Util.getInstance()
							.md5Encode(StringUtils.reverse(randomCode) + StringUtils.reverse(password) + randomCode)
					+ Base64Util.getInstance().encodeStr(randomCode.substring(getFirstNum(idCode)));
			// 去除base64标识
			password = StringUtils.reverse(password.replaceAll("=", ""));

			// 对数字减10
			String str = "";
			char[] charArray = password.toCharArray();
			for (char charStr : charArray) {
				if (Character.isDigit(charStr)) {
					str += (10 - Integer.valueOf(String.valueOf(charStr)));
				} else {
					str += charStr;
				}
			}

			password = str;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return password;
	}

	/**
	 * 密码加密生成
	 * 
	 * @param id
	 * @param password
	 * @param randomCode
	 * @return
	 * @throws Exception
	 */
	public String getCommPassword(String id, String password, String randomCode) throws Exception {
		try {

			if (StringUtils.isEmpty(randomCode) || StringUtils.isEmpty(password)) {
				throw new Exception(
						"[getPassword][randomCode=" + randomCode + "][id=" + id + "][password=" + password + "]");
			}

			// 取Id的第一个数字
			int beginLen = getFirstNum(id);
			if (beginLen == 0) {
				// 取密码的长度
				beginLen = password.length();
			}
			if (beginLen > 10) {
				beginLen = 5;
			} else {
				beginLen = 10 - beginLen;
			}

			// 取Id的最后一个数字
			int endLen = getLastNum(id);
			if (endLen == 0) {
				// 取密码的长度
				endLen = password.length();
			}
			if (endLen > 10) {
				endLen = 5;
			} else {
				endLen = endLen / 2;
			}

			// 密码加密规则( 反转(xxx+md5(反转(随机码)+反转(pwd)+随机码)+base64(xxx)))
			password = Md5Util.getInstance()
					.md5Encode(StringUtils.reverse(randomCode) + StringUtils.reverse(password) + randomCode)
					+ Base64Util.getInstance().encodeStr(randomCode.substring(endLen));
			// 去除base64标识
			password = StringUtils.reverse(password.replaceAll("=", ""));

			// 对加密字符串所有数字减10
			String str = "";
			char[] charArray = password.toCharArray();
			for (char charStr : charArray) {
				if (Character.isDigit(charStr)) {
					str += (10 - Integer.valueOf(String.valueOf(charStr)));
				} else {
					str += charStr;
				}
			}
			password = str;

			// 将随机数插入到加密字符中
			String tmpStr = randomCode.substring(beginLen);
			StringBuffer buffer = new StringBuffer(password);
			// 是否奇数
			boolean isOddFlag = true;
			if (password.length() % 2 == 0) {
				isOddFlag = false;
			}
			char[] charArrayStr = tmpStr.toCharArray();
			int index = 0;
			for (int i = 0; i < charArrayStr.length; i++) {
				if (isOddFlag) {
					index += 3;
				} else {
					index += 2;
				}
				buffer.insert(index, charArrayStr[i]);
			}
			password = buffer.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return password;
	}

	public static void main(String[] args) throws Exception {

		System.out.println(getInstance().getToken("E10ADC3949BA59ABBE56E057F20F883E"));

		// 取idcode第一位非0数字
		int before = 0;
		// 取idcode最后一位数字
		int after = 0;
		String str = "1693238185";
		char[] charArray = str.toCharArray();
		for (char c : charArray) {
			if (Character.isDigit(c) && Integer.valueOf(c) > 0) {
				before = Integer.valueOf(String.valueOf(c));
				break;
			}
		}
		for (int i = charArray.length - 1; i >= 0; i--) {
			if (Character.isDigit(charArray[i]) && Integer.valueOf(charArray[i]) > 0) {
				after = Integer.valueOf(String.valueOf(charArray[i]));
				break;
			}
		}

		// 1693238185
		// E10ADC3949BA59ABBE56E057F20F883E
		// Ih3fUPreYb
		//
		// aPdGs6b11db3bde1429481a9fb8597e69749ad

		System.out.println(before);
		System.out.println(after);
		System.out.println(CommUtil.getInstance().getFirstNum("1693238185"));
		System.out.println(Base64Util.getInstance().decodeStr("2N2M4MjQ1NGI5ZjZhOWIzYzUzNWRjZjRlMDFhYzY1Y2M=v5hpk"));
		System.out.println(
				"-----" + getInstance().getPassword("1693238185", "E10ADC3949BA59ABBE56E057F20F883E", "Ih3fUPreYb"));
		System.out.println(
				"-----" + getInstance().getPassword("359123279", "E10ADC3949BA59ABBE56E057F20F883E", "Ih3fUPreYb"));
		System.out.println(Base64Util.getInstance().encodeStr("111"));

		System.out.println("abcdef".substring(3));

		// 根据星期截取
		Calendar calendar = Calendar.getInstance();
		int len = calendar.get(Calendar.DAY_OF_WEEK);
		if (len > 7) {
			len = 7;
		}

		System.out.println();
		String key = "0a016406e0a32c9ae191b74aeffb0af4";
		System.out.println("key=" + key);
		String token = getInstance().getToken(key);
		System.out.println("token=" + token);
		String salt = token.substring(0, 8 - len);
		token = Base64Util.getInstance().decodeStr(token.substring(8 - len));
		System.out.println("token=" + token);
		salt = token.substring(0, len) + salt;
		System.out.println("salt=" + salt);
		token = token.substring(len);
		System.out.println("token=" + token);
		System.out.println(new String(PBEUtil.getInstance().decrypt(Base64Util.getInstance().decodeByte(token),
				PrivilegeConstant.PBE_PASSWORD_KEY, salt.getBytes())));

		System.out.println();
		System.out.println(Base64Util.getInstance().encodeStr("0a016406e0a32c9ae191b74aeffb0af4"));
		System.out.println(Base64Util.getInstance().encodeStr("1693238185"));
		System.out.println(Base64Util.getInstance().decodeStr(token));
		System.out.println(Base64Util.getInstance().decodeStr("NGZhMGJmZmVhNDdiMTkxZWE5YzIzYTBlNjA0NjEwYTA="));
	}

}
