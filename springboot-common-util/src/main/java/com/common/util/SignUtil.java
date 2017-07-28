package com.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TimeZone;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import com.common.base.common.BaseLogger;
import com.common.base.constant.AppKeySecretConstant;
import com.common.base.constant.CommonConstant;

/**
 * 签名算法
 * 
 * @author: HuiJunLuo
 * @date:2016年3月31日
 * @Copyright:Copyright (c) xxxx有限公司 2014 ~ 2015 版权所有
 */
public class SignUtil extends BaseLogger {

    private static SignUtil signUtil = null;

    public synchronized static SignUtil getInstance() {
        if (signUtil == null) {
            signUtil = new SignUtil();
        }
        return signUtil;
    }

    private SignUtil() {
    }

    /**
     * 生成签名
     * 
     * @param params
     * @return
     */
    public String getSign(String appKey, String timestamp, Map<String, Object> params) {
        params.put("appKey", appKey);
        params.put("appSecret", AppKeySecretConstant.getAppSecret(appKey));
        params.put("timestamp", timestamp);
        SortedMap<String, Object> sortedParamMap = new TreeMap<String, Object>(params);
        // 签名算法规则：3位随机码+md5(反转字符串(appSecret)+Base64(sortedParamMap+appKey)+"mornsun"+timestamp)+5位随机码
        String sign = CodeUtil.getInstance().getSecurityCode(3)
                + Md5Util.getInstance()
                        .MD5(StringUtils.reverse(AppKeySecretConstant.getAppSecret(appKey)) + Base64Util.getInstance()
                                .encodeStr(canonicalize(sortedParamMap).concat(appKey)).replaceAll("[\\s*\t\n\r]", "")
                                + CommonConstant.SYSTEM_NAME + timestamp)
                + CodeUtil.getInstance().getSecurityCode(5);
        logger.debug("[SignUtil][getSign]sign=" + sign.toLowerCase());
        return sign.toLowerCase();
    }

    /**
     * 获取标准时间
     * 
     * @return
     */
    @SuppressWarnings("unused")
    private String timestamp() {
        Calendar cal = Calendar.getInstance();
        DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        dfm.setTimeZone(TimeZone.getTimeZone("GMT"));
        String timestamp = dfm.format(cal.getTime());
        return timestamp;
    }

    /**
     * 参数排序拼接
     * 
     * @param sortedParamMap
     * @return
     */
    private String canonicalize(SortedMap<String, Object> sortedParamMap) {
        if (sortedParamMap.isEmpty()) {
            return "";
        }
        StringBuffer buffer = new StringBuffer();
        Iterator<Map.Entry<String, Object>> iter = sortedParamMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Object> kvpair = iter.next();
            buffer.append(encodeUTF8(kvpair.getKey()));
            buffer.append("=");
            buffer.append(encodeUTF8(kvpair.getValue()));
            if (iter.hasNext()) {
                buffer.append("&");
            }
        }
        return buffer.toString();
    }

    /**
     * utf-8
     * 
     * @param obj
     * @return
     */
    private String encodeUTF8(Object obj) {
        String str = "";
        try {
            if (obj != null) {
                str = obj.toString();
            }
            str = URLEncoder.encode(str, CommonConstant.CHARSET);
        } catch (UnsupportedEncodingException e) {
        }
        return str;
    }

    /**
     * Rfc3986
     * 
     * @param str
     * @return
     */
    @SuppressWarnings("unused")
    private String percentEncodeRfc3986(String str) {
        String out;
        try {
            out = URLEncoder.encode(str, CommonConstant.CHARSET).replace("+", "%20").replace("*", "%2A").replace("%7E",
                    "~");
        } catch (UnsupportedEncodingException e) {
            out = str;
        }
        return out;
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("mobile", "13246724138");
        // sign :1485d592e8b91cbb95d2052f57d4d0a895d25827
        // timestamp:1482746023406
        // mobile:13246724138
        // appKey:android
        // 765 6d868d350486cb492841b050171e5467 66671
        // pvn 6d868d350486cb492841b050171e5467 bv2v7

        // 13246724138
        // 1482762313563
        // 5vqD2565D27302F4D7824E96ED7C5877412JVH1x
        // 8226e00d32b8b193d5777dc8e2521674ddc14149
        // vax6e00d32b8b193d5777dc8e2521674ddcul43n

        // sign=018c8dba3e14b62cfb0050ee5117869b1af57093&mobile=13237020249&timestamp=1482822169938&appKey=ios

        String sign = getInstance().getSign("android", "1482762313563", params);
        System.out.println(sign);
        String signValid = "7656d868d350486cb492841b050171e546766671";
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong("1482750200727"));
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
        System.out.println(
                (Calendar.getInstance().getTimeInMillis() - Long.parseLong("1482762313563")) / (1000 * 60 * 60));
        System.out.println(sign.substring(3, sign.length() - 5).equals(signValid.substring(3, signValid.length() - 5)));
    }
}
