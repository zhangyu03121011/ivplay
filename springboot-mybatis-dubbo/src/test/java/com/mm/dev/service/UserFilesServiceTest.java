package com.mm.dev.service;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSONObject;
import com.common.util.UUIDGenerator;
import com.mm.dev.config.ConfigProperties;
import com.mm.dev.constants.WechatConstant;
import com.mm.dev.dao.jpa.user.UserFilesDao;
import com.mm.dev.entity.user.UserFiles;
import com.mm.dev.service.user.IUserFilesService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserFilesServiceTest {
	
	@Autowired
	private IUserFilesService userFilesService;
	
	@Autowired
	private ConfigProperties configProperties;
	
	@Autowired
	private UserFilesDao userFilesDao;
	
	@Test
	public void testSave() throws Exception {
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
		userFiles.setOpenId("123466");
		userFiles.setBlur(configProperties.getBlur());
		userFiles.setPrice(configProperties.getPrice());
		userFiles.setPriceMin(configProperties.getPriceMin());
		userFiles.setRandom(configProperties.getRandom());
		userFilesService.saveUserFiles(userFiles);
	}
	
	@Test
	public void testQuery() throws Exception {
		List<UserFiles> findByOpenIdAndFileCategory = userFilesService.findByOpenIdAndFileCategory("o5z7ywOP7qycrtAAxIqDfgMbfcFY", "1");
		System.out.println(JSONObject.toJSONString(findByOpenIdAndFileCategory));
	}
	
	@Test
	public void testQueryByPage() throws Exception {
		System.out.println(JSONObject.toJSONString(userFilesService.getAll("o5z7ywOP7qycrtAAxIqDfgMbfcFY",null)));
	}
}
