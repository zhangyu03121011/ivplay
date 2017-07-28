package com.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.alibaba.simpleimage.ImageRender;
import com.alibaba.simpleimage.SimpleImageException;
import com.alibaba.simpleimage.render.ReadRender;
import com.alibaba.simpleimage.render.ScaleParameter;
import com.alibaba.simpleimage.render.ScaleRender;
import com.alibaba.simpleimage.render.WriteRender;
import com.common.base.common.BaseLogger;
import com.common.base.constant.CommonConstant;

/**
 * 图片压缩工具
 * 
 * @author: HuiJunLuo
 * @date:2016年1月8日
 * @Copyright:Copyright (c) xxxx有限公司 2014 ~ 2015 版权所有
 */
public class SimpleImageUtil extends BaseLogger implements CommonConstant {

    private static SimpleImageUtil simpleImageUtil = null;

    public synchronized static SimpleImageUtil getInstance() {
        if (simpleImageUtil == null) {
            simpleImageUtil = new SimpleImageUtil();
        }
        return simpleImageUtil;
    }

    private SimpleImageUtil() {
    }

    static {
        System.setProperty("com.sun.media.jai.disableMediaLib", "true");
    }

    public void zipImg(String source, String dest, int width, int height) throws Exception {
        File in = new File(source); // 原图片
        File out = new File(dest); // 目的图片
        zipImg(in, out, width, height);
    }

    public void zipImg(File source, File dest, int width, int height) throws Exception {
        try {
            zipImg(new FileInputStream(source), dest, width, height);
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void zipImg(InputStream source, File dest, int width, int height) throws Exception {
        ScaleParameter scaleParam = new ScaleParameter(width, height); // 将图像缩略到1024x1024以内，不足1024x1024则不做任何处理
        InputStream inStream = null;
        FileOutputStream outStream = null;
        WriteRender wr = null;
        try {
            inStream = source;
            outStream = new FileOutputStream(dest);
            ImageRender rr = new ReadRender(inStream);
            ImageRender sr = new ScaleRender(rr, scaleParam);
            wr = new WriteRender(sr, outStream);
            wr.render(); // 触发图像处理
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception();
        } finally {
            IOUtils.closeQuietly(inStream); // 图片文件输入输出流必须记得关闭
            IOUtils.closeQuietly(outStream);
            if (wr != null) {
                try {
                    wr.dispose(); // 释放simpleImage的内部资源
                } catch (SimpleImageException ignore) {
                    // skip ...
                    logger.error(ignore.getMessage(), ignore);
                }
            }
        }
    }

}
