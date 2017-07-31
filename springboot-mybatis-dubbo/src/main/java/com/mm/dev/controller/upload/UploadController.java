package com.mm.dev.controller.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import com.mm.dev.controller.wechat.wechartController;
import com.mm.dev.service.impl.wechat.WechatServiceImpl;
import com.mm.dev.util.UserSession;

@Controller
@RequestMapping("/upload")
public class UploadController {
	private Logger logger = LoggerFactory.getLogger(wechartController.class);
	
	@Autowired
	private WechatServiceImpl WechatService;
	
	@RequestMapping(value = "/image",method=RequestMethod.POST)
	public void fileUpload(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
        	StandardMultipartHttpServletRequest req = (StandardMultipartHttpServletRequest) request;
            Iterator<String> iterator = req.getFileNames();
            String path = request.getParameter("path");
            path = path != null ? java.net.URLDecoder.decode(path, "utf-8") : "";
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            if (isMultipart) {
//                FileItemFactory factory = new DiskFileItemFactory();
//                ServletFileUpload upload = new ServletFileUpload(factory);
                // 得到所有的表单域，它们目前都被当作FileItem
//                List<FileItem> fileItems = upload.parseRequest(request);
 
                String id = "";
                String fileName = "";
                // 如果大于1说明是分片处理
                int chunks = 1;
                int chunk = 0;
                FileItem tempFileItem = null;
 
//                for (FileItem fileItem : fileItems) {
//                    if (fileItem.getFieldName().equals("id")) {
//                        id = fileItem.getString();
//                    } else if (fileItem.getFieldName().equals("name")) {
//                        fileName = new String(fileItem.getString().getBytes(
//                                "ISO-8859-1"), "UTF-8");
//                    } else if (fileItem.getFieldName().equals("chunks")) {
//                        chunks = NumberUtils.toInt(fileItem.getString());
//                    } else if (fileItem.getFieldName().equals("chunk")) {
//                        chunk = NumberUtils.toInt(fileItem.getString());
//                    } else if (fileItem.getFieldName().equals("file")) {
//                        tempFileItem = fileItem;
//                    }
//                }
                
                while (iterator.hasNext()) {
                    MultipartFile file = req.getFile(iterator.next());
                    String fileNames = file.getOriginalFilename();
                    if(StringUtils.isNotEmpty(fileNames)) {
                    	WechatService.sendCustomMessages("正在拼命处理中...",(String)UserSession.getSession("openId"));
                    	int split = fileNames.lastIndexOf(".");
        	           // 文件名 
                    	fileName = fileNames.substring(0,split);
        	            //文件格式   fileNames.substring(split+1,fileNames.length())
        	           //文件内容 file.getBytes()
                    	// 临时目录用来存放所有分片文件
                        String tempFileDir = "G:\\temp1" + File.separator + id;
                        File parentFileDir = new File(tempFileDir);
                        if (!parentFileDir.exists()) {
                            parentFileDir.mkdirs();
                        }
                        // 分片处理时，前台会多次调用上传接口，每次都会上传文件的一部分到后台(默认每片为5M)
                        File tempPartFile = new File(parentFileDir, fileName + "_" + chunk + ".part");
                        InputStream fileInputStream = file.getInputStream();
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
                            File destTempFile = new File("G:\\temp1", fileNames);
                            for (int i = 0; i < chunks; i++) {
                                File partFile = new File(parentFileDir, fileName + "_" + i + ".part");
         
                                FileOutputStream destTempfos = new FileOutputStream(destTempFile, true);
         
                                FileUtils.copyFile(partFile, destTempfos);
                                
                                partFile.delete();
                                destTempfos.close();
                            }
                            // 得到 destTempFile 就是最终的文件
                            // 添加到文件系统或者存储中
                             
                            // 删除临时目录中的分片文件
//                            FileUtils.deleteDirectory(parentFileDir);
                            // 删除临时文件
//                            destTempFile.delete();
                            response.getWriter().write("success");
                            
                            WechatService.sendCustomMessages("<a href='javascript:void(0);alert('hello')'>已为您生成好模糊图，点击查看</a>",(String)UserSession.getSession("openId"));
                        } else {
                            // 临时文件创建失败
                            if (chunk == chunks -1) {
                                FileUtils.deleteDirectory(parentFileDir);
                                logger.error("临时分配文件创建失败");
//                                ResponseUtil.responseFail(response, "500", "内部错误");
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("图片上传失败", e);
//            ResponseUtil.responseFail(response, "500", "内部错误");
        }
    }
}
