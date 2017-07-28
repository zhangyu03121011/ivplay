package com.common.util;

import java.security.MessageDigest;

import org.apache.commons.lang.StringUtils;

import com.common.base.common.BaseLogger;
import com.common.base.constant.CommonConstant;

/**
 * MD5加密
 * 
 * @author: HuiJunLuo
 * @date:2015年12月28日
 * @Copyright:Copyright (c) xxxx有限公司 2014 ~ 2015 版权所有
 */
public class Md5Util extends BaseLogger {

    private static Md5Util md5Util = null;

    public synchronized static Md5Util getInstance() {
        if (md5Util == null) {
            md5Util = new Md5Util();
        }
        return md5Util;
    }

    private Md5Util() {
    }

    /***
     * MD5加密 生成32位md5码
     * 
     * @param 待加密字符串
     * @return 返回32位md5码
     */
    public String md5Encode(String inStr) throws Exception {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return "";
        }

        StringBuffer hexValue = new StringBuffer();
        try {
            // md5加密规则(系统名称+base64[原值(反转)]+系统名称(反转))
            byte[] byteArray = (CommonConstant.SYSTEM_NAME
                    + Base64Util.getInstance().encodeStr(StringUtils.reverse(inStr))
                    + StringUtils.reverse(CommonConstant.SYSTEM_NAME)).getBytes(CommonConstant.CHARSET);

            byte[] md5Bytes = md5.digest(byteArray);
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return hexValue.toString();
    }

    /**
     * MD5加密(标准)
     * 
     * @param s
     * @return
     */
    public String MD5(String s) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 测试主函数
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String args[]) throws Exception {
        String str = new String("amigoxiexiexingxing");
        System.out.println("原始：" + str);
        System.out.println("MD5后：" + Md5Util.getInstance().md5Encode(str));
        System.out.println(Md5Util.getInstance().MD5(str));
    }
}
