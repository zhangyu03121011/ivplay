package com.mm.dev.controller.upload;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import com.mm.dev.controller.wechat.WechartController;
import com.mm.dev.entity.user.UserFiles;
import com.mm.dev.entity.wechat.ReturnMsg;
import com.mm.dev.enums.ExceptionEnum;
import com.mm.dev.service.upload.IUploadService;
import com.mm.dev.service.wechat.IWechatService;
import com.mm.dev.util.ReturnMsgUtil;
import com.mm.dev.util.UserSession;

/**
 * @Description: UploadController
 * @author Jacky
 * @date 2017年8月6日 上午10:53:01
 */
@Controller
@RequestMapping("/upload")
public class UploadController {
	private Logger logger = LoggerFactory.getLogger(WechartController.class);
	
	@Autowired
	private IWechatService WechatService;
	
	@Autowired
	private IUploadService uploadService;
	
	/**
	 * @Description: 上传图片
	 * @DateTime:2017年8月1日 下午12:31:18
	 * @return ReturnMsg<Object>
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/image",method=RequestMethod.POST)
	@ResponseBody
	public ReturnMsg<Object> fileUpload(UserFiles userFiles,HttpServletRequest request, HttpServletResponse response) throws ServletException {
		String openId = (String)UserSession.getSession("openId");
        try {
        	if(StringUtils.isNotEmpty(openId)) {
        		StandardMultipartHttpServletRequest req = (StandardMultipartHttpServletRequest) request;
        		Iterator<String> iterator = req.getFileNames();
        		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        		if (isMultipart) {
        			WechatService.sendCustomMessages("正在拼命处理中...",openId);
        			while (iterator.hasNext()) {
        				MultipartFile file = req.getFile(iterator.next());
        				userFiles.setOpenId(openId);
        				uploadService.uploadImage(userFiles, file);
        			}
        		}
        	} else {
        		 return ReturnMsgUtil.error(ExceptionEnum.system_error);
        	}
        } catch (ParseException e1) {
        	logger.error("图片上传失败", e1);
        	return ReturnMsgUtil.error(ExceptionEnum.system_error);
		} catch (IOException e2) {
			logger.error("图片上传失败", e2);
			return ReturnMsgUtil.error(ExceptionEnum.system_error);
		} catch (Exception e) {
            logger.error("图片上传失败", e);
            return ReturnMsgUtil.error(ExceptionEnum.system_error);
        }
        return ReturnMsgUtil.success();
    }
	
	
	/**
	 * @Description: 生成带二维码logo模糊图片
	 * @DateTime:2017年8月1日 下午3:28:09
	 * @return void
	 * @throws
	 */
	@RequestMapping("/blurImg/{openId}/{fileNames}/{fileType}")
	public void produceImg(@PathVariable String openId,@PathVariable String fileNames,@PathVariable String fileType,HttpServletResponse response) throws Exception {
		BufferedImage image = null;
		try {
			String format = "jpg";// 图像类型
			image = uploadService.toBufferedImage(openId,fileNames,fileType);
			ImageIO.write(image, format, response.getOutputStream());
			response.flushBuffer();
		} catch (Exception e) {
			logger.error("生成带二维码logo模糊图片异常");
			e.printStackTrace();
		} finally {
			image = null;
		}
	}
}
