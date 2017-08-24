package com.mm.dev.service;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.alibaba.fastjson.JSONObject;
import com.common.util.UUIDGenerator;
import com.mm.dev.config.ConfigProperties;
import com.mm.dev.constants.WechatConstant;
import com.mm.dev.dao.jpa.user.UserFilesDao;
import com.mm.dev.entity.user.UserFiles;
import com.mm.dev.service.user.IUserFilesService;

public class UserFilesServiceTest extends BaseTest{
	
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
		userFiles.setFileNames("testbbb222.png");
		userFiles.setFileNewNames("aaabbb222.png");
		userFiles.setFileSize("123456");
		userFiles.setFileSuffic("jpeg");
		userFiles.setFilePath("");
		userFiles.setOpenId("1234667788");
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
		Sort sort = new Sort(Direction.DESC, "createTime");
		Pageable pageable = new PageRequest(0,6, sort);
		System.out.println(JSONObject.toJSONString(userFilesService.getAll("o5z7ywOP7qycrtAAxIqDfgMbfcFY","1",pageable)));
	}
	
	@Test
	public void testDeleteById() throws Exception {
		userFilesService.deleteById("402881e45dbc8c52015dbc8c52a50000");
	}
	
	@Test
	public void testQueryBlurInfoById() throws Exception {
		System.out.println(JSONObject.toJSONString(userFilesService.queryBlurInfoById("4028cf815dc580ff015dc580ff2f0000")));
	}
}
