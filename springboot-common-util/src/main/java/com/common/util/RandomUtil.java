package com.common.util;

import com.common.base.common.BaseLogger;

/**
 * 随机数工具
 * 
 * @author: HuiJunLuo
 * @date:2015年12月28日
 * @Copyright:Copyright (c) xxxx有限公司 2014 ~ 2015 版权所有
 */
public class RandomUtil extends BaseLogger {

    private static RandomUtil randomUtil = null;

    public synchronized static RandomUtil getInstance() {
        if (randomUtil == null) {
            randomUtil = new RandomUtil();
        }
        return randomUtil;
    }

    private RandomUtil() {
    }

    /**
     * 创建指定数量的随机字符串
     * 
     * @param numberFlag
     *            是否是数字
     * @param length
     * @return
     */
    public String createRandom(boolean numberFlag, int length) {
        String retStr = "";
        try {
            String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
            int len = strTable.length();
            boolean bDone = true;
            do {
                retStr = "";
                int count = 0;
                for (int i = 0; i < length; i++) {
                    double dblR = Math.random() * len;
                    int intR = (int) Math.floor(dblR);
                    char c = strTable.charAt(intR);
                    if (('0' <= c) && (c <= '9')) {
                        count++;
                    }
                    retStr += strTable.charAt(intR);
                }
                if (count >= 2) {
                    bDone = false;
                }
            } while (bDone);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return retStr;
    }

}
