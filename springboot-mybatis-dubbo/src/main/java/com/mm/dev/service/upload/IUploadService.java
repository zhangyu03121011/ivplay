package com.mm.dev.service.upload;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.google.zxing.WriterException;
import com.mm.dev.entity.user.UserFiles;

/**
 * @Description: IUploadService
 * @author Jacky
 * @date 2017年8月6日 上午10:52:27
 */
public interface IUploadService {
	
	/**
	 * 处理上传图片
	 * @Description: TODO
	 * @Datatime 2017年8月6日 上午10:51:24 
	 * @return boolean    返回类型
	 */
	public boolean uploadImage(UserFiles userFiles,MultipartFile file) throws Exception;
	
	/**
	 * @Description:将图片放入内存中处理，添加logo
	 * @DateTime:2017年8月1日 下午3:32:00
	 * @return BufferedImage
	 * @throws
	 */
	public BufferedImage toBufferedImage(String openId,String fileName,String fileType) throws WriterException, IOException;

}
