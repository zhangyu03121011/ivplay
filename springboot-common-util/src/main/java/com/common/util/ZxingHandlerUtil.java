package com.common.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 条形码和二维码编码解码
 * 
 * @author DF
 * @version 2014-02-28
 */
public class ZxingHandlerUtil {

	/**
	 * 条形码编码
	 * 
	 * @param contents
	 * @param width
	 * @param height
	 * @param imgPath
	 */
	public static void encode(String contents, int width, int height, String imgPath) {
		int codeWidth = 3 + // start guard
				(7 * 6) + // left bars
				5 + // middle guard
				(7 * 6) + // right bars
				3; // end guard
		codeWidth = Math.max(codeWidth, width);
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,
					BarcodeFormat.EAN_13, codeWidth, height, null);

			MatrixToImageWriter
					.writeToFile(bitMatrix, "png", new File(imgPath));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 条形码解码
	 * 
	 * @param imgPath
	 * @return String
	 */
	public static String decode(String imgPath) {
		BufferedImage image = null;
		Result result = null;
		try {
			image = ImageIO.read(new File(imgPath));
			if (image == null) {
				System.out.println("the decode image may be not exit.");
			}
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

			result = new MultiFormatReader().decode(bitmap, null);
			return result.getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 二维码编码
	 * 
	 * @param contents
	 * @param width
	 * @param height
	 * @param imgPath
	 */
	public static void encode2(String contents, int width, int height, String imgPath) {
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		// 指定纠错等级
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		// 指定编码格式
		hints.put(EncodeHintType.CHARACTER_SET, "GBK");
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,
					BarcodeFormat.QR_CODE, width, height, hints);

			MatrixToImageWriter
					.writeToFile(bitMatrix, "png", new File(imgPath));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 二维码解码
	 * 
	 * @param imgPath
	 * @return String
	 */
	public static String decode2(String imgPath) {
		BufferedImage image = null;
		Result result = null;
		try {
			image = ImageIO.read(new File(imgPath));
			if (image == null) {
				System.out.println("the decode image may be not exit.");
			}
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

			Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
			hints.put(DecodeHintType.CHARACTER_SET, "GBK");

			result = new MultiFormatReader().decode(bitmap, hints);
			return result.getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	 /** 
     * 二维码绘制logo 
     * @param twodimensioncodeImg 二维码图片文件 
     * @param logoImg logo图片文件 
     * */  
    public static BufferedImage encodeImgLogo(File twodimensioncodeImg,File logoImg){  
        BufferedImage twodimensioncode = null;  
        try{  
            if(!twodimensioncodeImg.isFile() || !logoImg.isFile()){  
                System.out.println("输入非图片");  
                return null;  
            }  
            //读取二维码图片  
            twodimensioncode = ImageIO.read(twodimensioncodeImg);  
            //获取画笔  
            Graphics2D g = twodimensioncode.createGraphics();  
            //读取logo图片  
            BufferedImage logo = ImageIO.read(logoImg);  
            //设置二维码大小，太大，会覆盖二维码，此处20%  
            int logoWidth = logo.getWidth(null) > twodimensioncode.getWidth()*2 /10 ? (twodimensioncode.getWidth()*2 /10) : logo.getWidth(null);  
            int logoHeight = logo.getHeight(null) > twodimensioncode.getHeight()*2 /10 ? (twodimensioncode.getHeight()*2 /10) : logo.getHeight(null);  
            //设置logo图片放置位置  
            //中心  
            int x = (twodimensioncode.getWidth() - logoWidth) / 2;  
            int y = (twodimensioncode.getHeight() - logoHeight) / 2;  
            //右下角，15为调整值  
//          int x = twodimensioncode.getWidth()  - logoWidth-15;  
//          int y = twodimensioncode.getHeight() - logoHeight-15;  
            //开始合并绘制图片  
            g.drawImage(logo, x, y, logoWidth, logoHeight, null);  
            g.drawRoundRect(x, y, logoWidth, logoHeight, 15 ,15);  
            //logo边框大小  
            g.setStroke(new BasicStroke(2));  
            //logo边框颜色  
            g.setColor(Color.WHITE);  
            g.drawRect(x, y, logoWidth, logoHeight);  
            g.dispose();  
            logo.flush();  
            twodimensioncode.flush();  
        }catch(Exception e){  
            System.out.println("二维码绘制logo失败");  
        }  
        return twodimensioncode;  
    }  
      
    /** 
     * 二维码输出到文件 
     * @param twodimensioncodeImg 二维码图片文件 
     * @param logoImg logo图片文件 
     * @param format 图片格式 
     * @param file 输出文件 
     * */  
    public static void writeToFile(File twodimensioncodeImg,File logoImg,String format,File file){  
        BufferedImage image = encodeImgLogo(twodimensioncodeImg, logoImg);  
        try {  
            ImageIO.write(image, format, file);  
        } catch (IOException e) {  
            System.out.println("二维码写入文件失败"+e.getMessage());  
        }  
    }  
    /** 
     * 二维码流式输出 
     * @param twodimensioncodeImg 二维码图片文件 
     * @param logoImg logo图片文件 
     * @param format 图片格式 
     * @param stream 输出流 
     * */  
    public static void writeToStream(File twodimensioncodeImg,File logoImg,String format,OutputStream stream){  
        BufferedImage image = encodeImgLogo(twodimensioncodeImg, logoImg);  
        try {  
            ImageIO.write(image, format, stream);  
        } catch (IOException e) {  
            System.out.println("二维码写入流失败"+e.getMessage());  
        }  
    }  
    
    public static byte[] readInputStream(InputStream inStream) throws Exception{  
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        //创建一个Buffer字符串  
        byte[] buffer = new byte[1024];  
        //每次读取的字符串长度，如果为-1，代表全部读取完毕  
        int len = 0;  
        //使用一个输入流从buffer里把数据读取出来  
        while( (len=inStream.read(buffer)) != -1 ){  
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度  
            outStream.write(buffer, 0, len);  
        }  
        //关闭输入流  
        inStream.close();  
        //把outStream里的数据写入内存  
        return outStream.toByteArray();  
    }  
    

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

		// 条形码
//		String imgPath = "target\\zxing_EAN13.png";
//		String contents = "6923450657713";
//		int width = 105, height = 50;
//		
//		ZxingHandler.encode(contents, width, height, imgPath);
//		System.out.println("finished zxing EAN-13 encode.");
//
//		String decodeContent = ZxingHandler.decode(imgPath);
//		System.out.println("解码内容如下：" + decodeContent);
//		System.out.println("finished zxing EAN-13 decode.");
		
		// 二维码
//		 String tempFileDir = "E:\\temp" + File.separator + "o5z7ywOP7qycrtAAxIqDfgMbfcFY";
//         File parentFileDir = new File(tempFileDir);
//         if (!parentFileDir.exists()) {
//             parentFileDir.mkdirs();
//             parentFileDir.canWrite();
//             parentFileDir.canExecute();
//         }
//        String blurFileDir = parentFileDir + File.separator +  "bluraaa2.PNG";
//		String imgPath2 = "target\\o5z7ywOP7qycrtAAxIqDfgMbfcFY\\blurIMG_3794.PNG";
//		String contents2 = "Hello Gem, welcome to Zxing!"
//				+ "\nBlog [ http://thinkgem.iteye.com ]"
//				+ "\nEMail [ thinkgem@163.com ]";
//		int width2 = 300, height2 = 300;
//
//		ZxingHandler.encode2(contents2, width2, height2, blurFileDir);
//		System.out.println("finished zxing encode.");
//
//		String decodeContent2 = ZxingHandler.decode2(imgPath2);
//		System.out.println("解码内容如下：" + decodeContent2);
//		System.out.println("finished zxing decode.");
		//添加logo图片
//		File file = new File("C:\\Users\\Administrator\\Desktop\\qq5.png");
//		File file2 = new File(ZxingHandler.class.getClassLoader().getResource("").getPath()+"images/pbytlogo.jpg");
//		 File img1 = new File("C:\\Users\\Administrator\\Desktop\\qq6.png");  
//        writeToFile(file, file2, "jpeg", img1);
//		File file = new File("http://weixin.qq.com/q/02A1__1u2AcCl100000078");
//		System.out.println(file.length());
		
		
//		System.out.println(decode2("C:\\Users\\Administrator\\Desktop\\湖南商学院渠道二维码2.png"));
        
		//生成模糊图片
//		GaussianBlurUtil.setGaussianBlurImg("target\\o5z7ywOP7qycrtAAxIqDfgMbfcFY\\IMG_4215.PNG", 10);
		
		encodeImgLogo(new File("target\\o5z7ywOP7qycrtAAxIqDfgMbfcFY\\IMG_4215.PNG"), new File("target\\o5z7ywOP7qycrtAAxIqDfgMbfcFY\\o5z7ywOP7qycrtAAxIqDfgMbfcFYbluraaa.PNG"));
	}
    
}