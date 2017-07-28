package com.common.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.common.base.common.BaseLogger;

/**
 * 图片压缩工具
 * 
 * @author: HuiJunLuo
 * @date:2016年1月8日
 * @Copyright:Copyright (c) xxxx有限公司 2014 ~ 2015 版权所有
 */
public class ImageCompressUtil extends BaseLogger {

    private static ImageCompressUtil imageCompressUtil = null;

    public synchronized static ImageCompressUtil getInstance() {
        if (imageCompressUtil == null) {
            imageCompressUtil = new ImageCompressUtil();
        }
        return imageCompressUtil;
    }

    private ImageCompressUtil() {
    }

    /**
     * 直接指定压缩后的宽高： (先保存原文件，再压缩、上传) 壹拍项目中用于二维码压缩
     * 
     * @param oldFile
     *            要进行压缩的文件全路径
     * @param width
     *            压缩后的宽度
     * @param height
     *            压缩后的高度
     * @param quality
     *            压缩质量
     * @param smallIcon
     *            文件名的小小后缀(注意，非文件后缀名称),入压缩文件名是yasuo.jpg,则压缩后文件名是yasuo(+smallIcon
     *            ).jpg
     * @return 返回压缩后的文件的全路径
     */
    public String zipImageFile(String oldFile, int width, int height, float quality, String smallIcon) {
        if (oldFile == null) {
            return null;
        }
        String newImage = null;
        try {
            /** 对服务器上的临时文件进行处理 */
            Image srcFile = ImageIO.read(new File(oldFile));
            /** 宽,高设定 */
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            tag.getGraphics().drawImage(srcFile, 0, 0, width, height, null);
            String filePrex = oldFile.substring(0, oldFile.indexOf('.'));
            /** 压缩后的文件名 */
            newImage = filePrex + smallIcon + oldFile.substring(filePrex.length());
            
            /** 压缩之后临时存放位置 */
//            FileOutputStream out = new FileOutputStream(newImage);
//            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//            JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
            
            ImageIO.write(tag,  "jpeg" , new File(newImage)); 
            tag.flush();
            
            /** 压缩质量 */
//            jep.setQuality(quality, true);
//            encoder.encode(tag, jep);
//            out.close();
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return newImage;
    }

    /**
     * 保存文件到服务器临时路径(用于文件上传)
     * 
     * @param fileName
     * @param is
     * @return 文件全路径
     */
    public String writeFile(String fileName, InputStream is) {
        if (fileName == null || fileName.trim().length() == 0) {
            return null;
        }
        try {
            /** 首先保存到临时文件 */
            FileOutputStream fos = new FileOutputStream(fileName);
            byte[] readBytes = new byte[512];// 缓冲大小
            int readed = 0;
            while ((readed = is.read(readBytes)) > 0) {
                fos.write(readBytes, 0, readed);
            }
            fos.close();
            is.close();
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return fileName;
    }

    /**
     * 等比例压缩算法： 算法思想：根据压缩基数和压缩比来压缩原图，生产一张图片效果最接近原图的缩略图
     * 
     * @param srcURL
     *            原图地址
     * @param deskURL
     *            缩略图地址
     * @param comBase
     *            压缩基数
     * @param scale
     *            压缩限制(宽/高)比例 一般用1：
     *            当scale>=1,缩略图height=comBase,width按原图宽高比例;若scale<1,缩略图width=
     *            comBase,height按原图宽高比例
     * @throws Exception
     * @author shenbin
     * @createTime 2014-12-16
     * @lastModifyTime 2014-12-16
     */
    public void saveMinPhoto(String srcURL, String deskURL, double comBase, double scale) throws Exception {
        File srcFile = new java.io.File(srcURL);
        Image src = ImageIO.read(srcFile);
        int srcHeight = src.getHeight(null);
        int srcWidth = src.getWidth(null);

        if (srcHeight <= comBase && srcWidth <= comBase) {
            FileInputStream fis = new FileInputStream(srcURL);
            FileOutputStream fos = new FileOutputStream(deskURL);
            int temp;
            while ((temp = fis.read()) != -1) {
                fos.write(temp);
            }
            fis.close();
            fos.close();

            return;
        }

        int deskHeight = 0;// 缩略图高
        int deskWidth = 0;// 缩略图宽
        double srcScale = (double) srcHeight / srcWidth;
        /** 缩略图宽高算法 */
        if ((double) srcHeight > comBase || (double) srcWidth > comBase) {
            if (srcScale >= scale || 1 / srcScale > scale) {
                if (srcScale >= scale) {
                    deskHeight = (int) comBase;
                    deskWidth = srcWidth * deskHeight / srcHeight;
                } else {
                    deskWidth = (int) comBase;
                    deskHeight = srcHeight * deskWidth / srcWidth;
                }
            } else {
                if ((double) srcHeight > comBase) {
                    deskHeight = (int) comBase;
                    deskWidth = srcWidth * deskHeight / srcHeight;
                } else {
                    deskWidth = (int) comBase;
                    deskHeight = srcHeight * deskWidth / srcWidth;
                }
            }
        } else {
            deskHeight = srcHeight;
            deskWidth = srcWidth;
        }
        BufferedImage tag = new BufferedImage(deskWidth, deskHeight, BufferedImage.TYPE_3BYTE_BGR);
        tag.getGraphics().drawImage(src, 0, 0, deskWidth, deskHeight, null); // 绘制缩小后的图
        
//        FileOutputStream deskImage = new FileOutputStream(deskURL); // 输出到文件流
//        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(deskImage);
//        encoder.encode(tag); // 近JPEG编码
//        deskImage.close();
        
        ImageIO.write(tag,  "jpeg" , new File(deskURL)); 
        tag.flush();
    }

    public void saveMinPhoto(InputStream inputStream, BufferedImage image, String deskURL, double comBase,
            double scale) throws Exception {
        Image src = image;
        int srcHeight = src.getHeight(null);
        int srcWidth = src.getWidth(null);

        if (srcHeight <= comBase && srcWidth <= comBase) {
            FileOutputStream fos = new FileOutputStream(deskURL);
            int temp;
            while ((temp = inputStream.read()) != -1) {
                fos.write(temp);
            }
            inputStream.close();
            fos.close();
            return;
        }

        int deskHeight = 0;// 缩略图高
        int deskWidth = 0;// 缩略图宽
        double srcScale = (double) srcHeight / srcWidth;
        /** 缩略图宽高算法 */
        if ((double) srcHeight > comBase || (double) srcWidth > comBase) {
            if (srcScale >= scale || 1 / srcScale > scale) {
                if (srcScale >= scale) {
                    deskHeight = (int) comBase;
                    deskWidth = srcWidth * deskHeight / srcHeight;
                } else {
                    deskWidth = (int) comBase;
                    deskHeight = srcHeight * deskWidth / srcWidth;
                }
            } else {
                if ((double) srcHeight > comBase) {
                    deskHeight = (int) comBase;
                    deskWidth = srcWidth * deskHeight / srcHeight;
                } else {
                    deskWidth = (int) comBase;
                    deskHeight = srcHeight * deskWidth / srcWidth;
                }
            }
        } else {
            deskHeight = srcHeight;
            deskWidth = srcWidth;
        }
        BufferedImage tag = new BufferedImage(deskWidth, deskHeight, BufferedImage.TYPE_3BYTE_BGR);
        tag.getGraphics().drawImage(src, 0, 0, deskWidth, deskHeight, null); // 绘制缩小后的图
        
//        FileOutputStream deskImage = new FileOutputStream(deskURL); // 输出到文件流
//        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(deskImage);
//        encoder.encode(tag); // 近JPEG编码
//        deskImage.close();
        
        ImageIO.write(image,  "jpeg" , new File(deskURL)); 
        image.flush();
    }

    public void resizePNG(String fromFile, String toFile, int outputWidth, int outputHeight,
            boolean proportion) {

        try {
            File f2 = new File(fromFile);

            BufferedImage bi2 = ImageIO.read(f2);
            int newWidth;
            int newHeight;
            // 判断是否是等比缩放
            if (proportion == true) {
                // 为等比缩放计算输出的图片宽度及高度
                double rate1 = ((double) bi2.getWidth(null)) / (double) outputWidth + 0.1;
                double rate2 = ((double) bi2.getHeight(null)) / (double) outputHeight + 0.1;
                // 根据缩放比率大的进行缩放控制
                double rate = rate1 < rate2 ? rate1 : rate2;
                newWidth = (int) (((double) bi2.getWidth(null)) / rate);
                newHeight = (int) (((double) bi2.getHeight(null)) / rate);
            } else {
                newWidth = outputWidth; // 输出的图片宽度
                newHeight = outputHeight; // 输出的图片高度
            }
            BufferedImage to = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

            Graphics2D g2d = to.createGraphics();

            to = g2d.getDeviceConfiguration().createCompatibleImage(newWidth, newHeight, Transparency.TRANSLUCENT);

            g2d.dispose();

            g2d = to.createGraphics();

            Image from = bi2.getScaledInstance(newWidth, newHeight, bi2.SCALE_AREA_AVERAGING);
            g2d.drawImage(from, 0, 0, null);
            g2d.dispose();

            ImageIO.write(to, "png", new File(toFile));

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

    }

    public void resizePNG(BufferedImage fromFile, String toFile, int outputWidth, int outputHeight,
            boolean proportion) {
        try {

            BufferedImage bi2 = fromFile;
            int newWidth;
            int newHeight;
            // 判断是否是等比缩放
            if (proportion == true) {
                // 为等比缩放计算输出的图片宽度及高度
                double rate1 = ((double) bi2.getWidth(null)) / (double) outputWidth + 0.1;
                double rate2 = ((double) bi2.getHeight(null)) / (double) outputHeight + 0.1;
                // 根据缩放比率大的进行缩放控制
                double rate = rate1 < rate2 ? rate1 : rate2;
                newWidth = (int) (((double) bi2.getWidth(null)) / rate);
                newHeight = (int) (((double) bi2.getHeight(null)) / rate);
            } else {
                newWidth = outputWidth; // 输出的图片宽度
                newHeight = outputHeight; // 输出的图片高度
            }
            BufferedImage to = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

            Graphics2D g2d = to.createGraphics();

            to = g2d.getDeviceConfiguration().createCompatibleImage(newWidth, newHeight, Transparency.TRANSLUCENT);

            g2d.dispose();

            g2d = to.createGraphics();

            Image from = bi2.getScaledInstance(newWidth, newHeight, bi2.SCALE_AREA_AVERAGING);
            g2d.drawImage(from, 0, 0, null);
            g2d.dispose();

            ImageIO.write(to, "png", new File(toFile));

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static void main(String args[]) throws Exception {
        // ImageCompressUtil.zipImageFile("D://E5F595936ED7E4805991D3A42F4F8640.png",
        // 200, 200, 1f, "x2");
        // ImageCompressUtil.saveMinPhoto("D://E5F595936ED7E4805991D3A42F4F8640.png",
        // "d:/11.jpg", 200, 1.5d);
        // ImageCompressUtil.saveMinPhoto("D://bbb.png", "d:/12.jpg", 200,
        // 1.5d);
        // ImageCompressUtil.zipImageFile("D://EC9114A475E28AEF8C37C0756A962F71.jpg",
        // 200, 200, 1f, "x2");
        // ImageCompressUtil.saveMinPhoto("D://EC9114A475E28AEF8C37C0756A962F71.jpg",
        // "d:/11.jpg", 200, 1.5d);

        String str = "04F37F10FBC63CEDF6DEF0DE475B9848";
        File file = new File("D://04F37F10FBC63CEDF6DEF0DE475B9848.jpg");
        System.out.println(file.getPath());

        // ImageCompressUtil.zipImageFile("D://" + str + ".jpg", 200, 200, 1f,
        // "_ssss");
        // ImageCompressUtil.saveMinPhoto("D://" + str + ".jpg", "D://" + str +
        // "_s.jpg", 200, 1.5d);

        // resizePNG("D://"+str+".jpg", "D://"+str+"_ss.jpg",200, 200,true);

    }

}
