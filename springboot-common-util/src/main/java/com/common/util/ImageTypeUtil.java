package com.common.util;

import org.apache.commons.lang.StringUtils;

import com.common.base.common.BaseLogger;

/**
 * 图片类别工具
 * 
 * @author: HuiJunLuo
 * @date:2016年1月8日
 * @Copyright:Copyright (c) xxxx有限公司 2014 ~ 2015 版权所有
 */
public class ImageTypeUtil extends BaseLogger {

    private static ImageTypeUtil imageTypeUtil = null;

    public synchronized static ImageTypeUtil getInstance() {
        if (imageTypeUtil == null) {
            imageTypeUtil = new ImageTypeUtil();
        }
        return imageTypeUtil;
    }

    private ImageTypeUtil() {
    }

    /**
     * 获取图片扩展名
     * 
     * @param mime
     * @return
     */
    public String getImageSuffix(String mime) {
        String suffix = "jpg";
        try {

            if (StringUtils.isNotEmpty(mime)) {
                if (mime.equalsIgnoreCase("image/jpeg")) {
                    suffix = "jpg";
                } else if (mime.equalsIgnoreCase("image/gif")) {
                    suffix = "gif";
                } else if (mime.equalsIgnoreCase("image/png")) {
                    suffix = "png";
                } else if (mime.equalsIgnoreCase("image/bmp")) {
                    suffix = "bmp";
                }
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return suffix;
    }
}
