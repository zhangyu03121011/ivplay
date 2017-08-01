package com.mm.dev.service.upload;

import org.springframework.web.multipart.MultipartFile;


/**
 * Created by Lipengfei on 2015/6/26.
 */
public interface IUploadService {

	public boolean uploadImage(String openId,MultipartFile file) throws Exception;

}
