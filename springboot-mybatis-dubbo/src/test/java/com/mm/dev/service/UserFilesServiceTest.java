package com.mm.dev.service;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.common.util.UUIDGenerator;
import com.mm.dev.constants.WechatConstant;
import com.mm.dev.entity.user.UserFiles;
import com.mm.dev.service.user.IUserFilesService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserFilesServiceTest {
	
	@Autowired
	private IUserFilesService userFilesService;
	
	@Test
	public void test() throws Exception {
		UserFiles userFiles = new UserFiles();
		userFiles.setId(UUIDGenerator.generate());
		userFiles.setCreateTime(new Date());
		userFiles.setUpdateTime(new Date());
		userFiles.setFileCategory(WechatConstant.file_category_1);
		userFiles.setFileNames("test.png");
		userFiles.setFileNewNames("aaa.png");
		userFiles.setFileSize("454121241");
		userFiles.setFileSuffic("png");
		userFiles.setFilePath("");
		userFiles.setUserId("11111");
		userFilesService.saveUserFiles(userFiles);
	}
}
