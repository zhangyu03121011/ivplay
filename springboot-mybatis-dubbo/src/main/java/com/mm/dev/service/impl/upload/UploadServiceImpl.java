package com.mm.dev.service.impl.upload;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
        	String fileType = fileNames.substring(split+1,fileNames.length());
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
        		
        		//生成模糊图片
        		FileUtils.copyFile(destTempFile, destBlurFile);
        		String blurFileDir = tempFileDir + File.separator + configProperties.getBlurPrefix() +fileNames;
        		GaussianBlurUtil.setGaussianBlurImg(blurFileDir, 10);
        		logger.info("blurFileDir:======{}",blurFileDir);
        		
        		//生成二维码
        		String qrcodeFileDir = tempFileDir + File.separator + configProperties.getQrcodePrefix() + fileNames;
        		ZxingHandler.encode2("my name is blur", configProperties.getWidth()/4, configProperties.getHeight()/4,qrcodeFileDir);
        		StringBuilder imagePath = new StringBuilder();
        		imagePath.append(configProperties.getHostPath());
        		imagePath.append(File.separator);
        		imagePath.append("upload");
        		imagePath.append(File.separator);
        		imagePath.append("blurImg");
        		imagePath.append(File.separator);
        		imagePath.append(openId);
        		imagePath.append(File.separator);
        		imagePath.append(fileName);
        		imagePath.append(File.separator);
        		imagePath.append(fileType);
        		logger.info("模糊图片访问路径imagePath:======",imagePath.toString());
        		WechatService.sendCustomMessages("<a href='"+imagePath.toString()+"'>已为您生成好模糊图，点击查看</a>",(String)UserSession.getSession("openId"));
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
		gs.drawImage(img, 0, 0, null);  
		gs.dispose();  
		img.flush(); 
		return image;
	}

}
