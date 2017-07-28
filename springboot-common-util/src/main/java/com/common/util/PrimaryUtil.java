package com.common.util;

import java.io.File;
import java.util.UUID;

import com.common.base.common.BaseLogger;

/**
 * 主键工具类
 * 
 * @author: HuiJunLuo
 * @date:2015年12月28日
 * @Copyright:Copyright (c) xxxx有限公司 2014 ~ 2015 版权所有
 */
public class PrimaryUtil extends BaseLogger {

    private static PrimaryUtil primaryUtil = null;

    public synchronized static PrimaryUtil getInstance() {
        if (primaryUtil == null) {
            primaryUtil = new PrimaryUtil();
        }
        return primaryUtil;
    }

    private PrimaryUtil() {
    }

    /**
     * 主键生成规则(uuid+日期+随机码),最后使用md5加密
     * 
     * @return
     * @throws Exception
     */
    public synchronized String getPrimaryValue() {
        try {
            String key = UUID.randomUUID().toString() + File.separator + System.currentTimeMillis() + File.separator
                    + RandomUtil.getInstance().createRandom(false, 8);
            return Md5Util.getInstance().md5Encode(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 10; i++) {
            System.out.println(PrimaryUtil.getInstance().getPrimaryValue());
        }
    }

}
