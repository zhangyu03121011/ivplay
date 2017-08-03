package com.mm.dev.service.impl.upload;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.common.util.GaussianBlur.GaussianBlurUtil;
import com.google.zxing.WriterException;
import com.mm.dev.config.ConfigProperties;
import com.mm.dev.config.ZxingHandler;
import com.mm.dev.service.impl.wechat.WechatServiceImpl;
import com.mm.dev.service.upload.IUploadService;
import com.mm.dev.util.UserSession;

/**
 * Created by Lipengfei on 2015/6/26.
 */
@Service
public class UploadServiceImpl implements IUploadService {
	
	Logger logger = LoggerFactory.getLogger(UploadServiceImpl.class);
	
	@Autowired
	private ConfigProperties configProperties;
	
	@Autowired
	private WechatServiceImpl WechatService;
	
	@Async
	public boolean uploadImage(String openId,MultipartFile file) throws Exception {
		if(null == file) {
			return false;
		}
		String fileNames = file.getOriginalFilename();
        InputStream fileInputStream = file.getInputStream();
        if(StringUtils.isNotEmpty(fileNames) && null != fileInputStream) {
        	String uploadPath = configProperties.getImageUrl();
        	String fileName = null;
        	// 如果大于1说明是分片处理
        	int chunks = 1;
        	int chunk = 0;
        	int split = fileNames.lastIndexOf(".");
        	// 文件名 
        	fileName = fileNames.substring(0,split);
        	//文件格式   
//        	String fileType = fileNames.substring(split+1,fileNames.length());
        	//文件内容 file.getBytes()
        	// 临时目录用来存放所有分片文件
        	String tempFileDir = uploadPath + File.separator + openId;
        	File parentFileDir = new File(tempFileDir);
        	if (!parentFileDir.exists()) {
        		parentFileDir.mkdirs();
        		parentFileDir.canWrite();
        		parentFileDir.canExecute();
        	}
        	// 分片处理时，前台会多次调用上传接口，每次都会上传文件的一部分到后台(默认每片为5M)
        	File tempPartFile = new File(parentFileDir, fileName + "_" + chunk + ".part");
        	FileUtils.copyInputStreamToFile(fileInputStream,tempPartFile);
        	fileInputStream.close();
        	// 是否全部上传完成
        	// 所有分片都存在才说明整个文件上传完成
        	boolean uploadDone = true;
        	for (int i = 0; i < chunks; i++) {
        		File partFile = new File(parentFileDir, fileName + "_" + i
        				+ ".part");
        		if (!partFile.exists()) {
        			uploadDone = false;
        		}
        	}
        	// 所有分片文件都上传完成
        	// 将所有分片文件合并到一个文件中
        	if (uploadDone) {
        		//生成上传图片
        		File destTempFile = new File(tempFileDir, fileNames);
        		//模糊图片
        		File destBlurFile = new File(tempFileDir, "blur_"+fileNames);
        		for (int i = 0; i < chunks; i++) {
        			File partFile = new File(parentFileDir, fileName + "_" + i + ".part");
        			FileOutputStream destTempfos = new FileOutputStream(destTempFile, true);
        			FileUtils.copyFile(partFile, destTempfos);
        			partFile.delete();
        			destTempfos.close();
        		}
        		logger.info("生成的原图片路径:{}",destTempFile.getAbsolutePath());
        		
        		BufferedImage destTempFile2 = ImageIO.read(destTempFile);
        		//生成模糊图片
        		FileUtils.copyFile(destTempFile, destBlurFile);
        		String blurFileDir = tempFileDir + File.separator + configProperties.getBlurPrefix() +fileNames;
        		GaussianBlurUtil.setGaussianBlurImg(blurFileDir, 10);
        		logger.info("生成的模糊图片路径:======{}",destBlurFile.getAbsolutePath());
        		
        		//生成二维码
        		String qrcodeFileDir = tempFileDir + File.separator + configProperties.getQrcodePrefix() + fileNames;
        		ZxingHandler.encode2("张晓风是假哈多", configProperties.getWidth(), configProperties.getHeight(),qrcodeFileDir);
        		logger.info("生成的二维码图片路径:======{}",blurFileDir);
        		
        		StringBuilder imagePath = new StringBuilder();
        		imagePath.append(configProperties.getHostPath());
        		imagePath.append(File.separator);
        		imagePath.append("wx_blur_image_share.html?");
        		imagePath.append("id=");
        		imagePath.append(openId);
        		imagePath.append("&fileNames=");
        		imagePath.append(fileNames);
        		 //输出合并图片  
//        		String blurQrcodeFileDir = tempFileDir + File.separator + configProperties.getBlurPrefix() + "_"+ configProperties.getQrcodePrefix() + fileNames;
//              addImageLogo(blurFileDir, qrcodeFileDir, blurQrcodeFileDir, 0, 0);
                
        		WechatService.sendCustomMessages("<a href='https://open.weixin.qq.com/connect/oauth2/authorize?appid="+configProperties.getAPPID()+"&redirect_uri=http%3a%2f%2fjacky.tunnel.qydev.com%2fwechat%2fcallback?fileNames="+fileNames+"&response_type=code&scope=snsapi_base&state=2&connect_redirect=1#wechat_redirect'>已为您生成好模糊图，点击查看</a>",(String)UserSession.getSession("openId"));
        	} else {
        		// 临时文件创建失败
        		if (chunk == chunks -1) {
//        			FileUtils.deleteDirectory(parentFileDir);
        			logger.error("临时分配文件创建失败");
        		}
        	}
        }
        return true;
	} 
	
	/** 
	    *@param photopath : 原图存放的路径 
	     *@param logopath : logo图像存放的路径 
	     *@param savepath : 目标输出保存的路径 
	     *@param x : logo图像在合并到原图中的显示位置x座标 
	     *@param y : logo图像在合并到原图中的显示位置y座标 
	     */  
	public void addImageLogo(String photopath,String logopath,String savepath,int x,int y) throws IOException,FileNotFoundException{  
		Image image = ImageIO.read(new File(photopath));
		int pwidth = image.getWidth(null);
		int pheight = image.getHeight(null);

		BufferedImage buffimage = new BufferedImage(pwidth, pheight,BufferedImage.TYPE_INT_BGR);
		Graphics g = buffimage.createGraphics();
		g.drawImage(image, 0, 0, pwidth, pheight, null);

		Image logo = ImageIO.read(new File(logopath));
		int lwidth = logo.getWidth(null);
		int lheight = logo.getHeight(null);
		g.drawImage(logo, x, y, lwidth, lheight, null);
		g.dispose();
		FileOutputStream os = new FileOutputStream(savepath);
		ImageIO.write(buffimage, "jpg", os);
		os.close();   
    }  
	
	/**  
     * 创建图片  
     */  
    public static void createImage(){  
        //设置字体样式  
        Font font = new Font("宋体", Font.PLAIN, 25);  
        //图片大小  
        int width = 1000;  
        int height = 1000;  
          
        //创建一个图片缓冲区  
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);  
        //获取图片处理对象  
        Graphics graphics = image.getGraphics();  
        //填充背景色  
        graphics.setColor(getRandomColor());  
        graphics.fillRect(1, 1, width - 1, height - 1);  
        //设定边框颜色  
        graphics.setColor(getRandomColor());  
        graphics.drawRect(0, 0, width - 1, height - 1);  
        //设置干扰线  
        graphics.setColor(getRandomColor());  
        Random random = new Random();  
        for(int i=0;i<20;i++){  
            int x = random.nextInt(width - 1);  
            int y = random.nextInt(height - 1);  
            int x1 = random.nextInt(width - 1);  
            int y1 = random.nextInt(height - 1);  
            graphics.drawLine(x, y, x1, y1);  
        }  
        //写入文字  
        graphics.setColor(getRandomColor());  
        graphics.setFont(font);  
        String content = new String("哈哈");  
        graphics.drawString(content, 100, 100);  
          
        //输出文件  
        File file = new File("E:\\temp\\123.gif");  
        try {  
            ImageIO.write(image, "GIF", file);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
          
        //释放资源  
        graphics.dispose();  
    }  
      
    /**  
     * 获取随机颜色  
     * @return  
     */  
    public static Color getRandomColor(){  
        Random random = new Random();  
        int r = random.nextInt(255);   
        int g = random.nextInt(255);  
        int b = random.nextInt(255);  
        return new Color(r, g, b);  
    }  
	
	/**
	 * @Description:将图片放入内存中处理，添加logo
	 * @DateTime:2017年8月1日 下午3:32:00
	 * @return BufferedImage
	 * @throws
	 */
	public BufferedImage toBufferedImage(String openId,String fileName,String fileType) throws WriterException, IOException {
		logger.info("请求查看模糊图片的用户openId:======{}",openId);
		logger.info("请求查看模糊图片的名称fileName:======{}",fileName);
		logger.info("请求查看模糊图片的格式fileType:======{}",fileType);
		String blurFileDir = configProperties.getImageUrl() + File.separator + openId + File.separator + configProperties.getBlurPrefix() + fileName + "."+fileType;
		String qrcodeFileDir = configProperties.getImageUrl() + File.separator + openId + File.separator + configProperties.getQrcodePrefix() + fileName + "."+fileType;
		BufferedImage image = ImageIO.read(new FileInputStream(blurFileDir));
		//载入logo  
		Graphics2D gs = image.createGraphics();  
		Image img = ImageIO.read(new File(qrcodeFileDir));  
		gs.drawImage(img, 0, image.getHeight(), null);  
		gs.dispose();  
		img.flush(); 
		return image;
	}
	
	public static void main(String[] args) {
		String ss = "http%3A%2F%2Fmnsppds.huishengdianz.com%2Fs%2F53%2Fwx_board.html%3Fp%3Df928b9bed111%26t%3D3a4489473f7afd4b4e0eb6ad433189bf%26p%3Df928b9bed111&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
		System.out.println(URLDecoder.decode(ss));
	}

}
