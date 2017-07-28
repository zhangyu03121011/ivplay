package com.common.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.common.base.common.BaseLogger;
import com.common.util.gif.AnimatedGifEncoder;
import com.common.util.gif.GifDecoder;
import com.common.util.gif.Scalr;
import com.common.util.gif.Scalr.Mode;

/**
 * 图片压缩工具
 * 
 * @author: HuiJunLuo
 * @date:2016年1月8日
 * @Copyright:Copyright (c) xxxx有限公司 2014 ~ 2015 版权所有
 */
public class ImageGifScalingUtil extends BaseLogger {

    private static ImageGifScalingUtil imageGifScalingUtil = null;

    public synchronized static ImageGifScalingUtil getInstance() {
        if (imageGifScalingUtil == null) {
            imageGifScalingUtil = new ImageGifScalingUtil();
        }
        return imageGifScalingUtil;
    }

    private ImageGifScalingUtil() {
    }

    /**
     * @Title: main
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @author Administrator
     * @param @param
     *            args 设定文件
     * @return void 返回类型
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        getInstance().scale(new File("D:/52adbea43dfae9b54d000003.gif"), new File("D:/aa_1.gif"), 360, 360);

        // SimpleImageUtil.getInstance().zipImg("d:/52adbea43dfae9b54d000003.GIF",
        // "d:/52adbea43dfae9b54d000003_s.GIF", 360, 360);
    }

    public BufferedImage getImage(File src) {
        try {
            GifDecoder gd = new GifDecoder();
            int status = gd.read(new FileInputStream(src));
            if (status != GifDecoder.STATUS_OK) {
                return null;
            }

            for (int i = 0; i < gd.getFrameCount();) {
                BufferedImage frame = gd.getFrame(i);
                return frame;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public boolean scale(File src, File dest, int widths, int heights) {
        try {
            GifDecoder gd = new GifDecoder();
            int status = gd.read(new FileInputStream(src));
            if (status != GifDecoder.STATUS_OK) {
                return false;
            }
            //

            AnimatedGifEncoder ge = new AnimatedGifEncoder();
            ge.start(new FileOutputStream(dest));
            ge.setRepeat(0);

            for (int i = 0; i < gd.getFrameCount(); i++) {
                BufferedImage frame = gd.getFrame(i);
                int widthTmp = frame.getWidth();
                int heightTmp = frame.getHeight();
                // System.out.println(widthTmp + "--" + heightTmp);
                // // 80%
                // width = (int) (width * 1);
                // height = (int) (height * 1);

                double sy, sx;
                int height, width;
                if (widthTmp <= heightTmp) {
                    width = widths;
                    sx = (double) width / widthTmp;
                    sy = sx;
                    height = (int) (heightTmp * sx);
                } else if (heightTmp > heights) {
                    height = heights;
                    sy = (double) height / heightTmp;
                    sx = sy;
                    width = (int) (widthTmp * sy);
                } else {
                    width = widths;
                    height = heights;
                }

                //
                BufferedImage rescaled = Scalr.resize(frame, Mode.FIT_EXACT, width, height);
                //
                int delay = gd.getDelay(i);
                //

                ge.setDelay(delay);
                ge.addFrame(rescaled);
            }

            ge.finish();
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }
}
