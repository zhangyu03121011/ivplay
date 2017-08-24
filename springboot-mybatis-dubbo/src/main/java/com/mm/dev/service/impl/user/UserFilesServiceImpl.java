package com.mm.dev.service.impl.user;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mm.dev.dao.jpa.user.UserFilesDao;
import com.mm.dev.dao.mapper.user.UserFilesMapper;
import com.mm.dev.entity.user.UserFiles;
import com.mm.dev.service.user.IUserFilesService;

/**
 * @ClassName: UserFilesServiceImpl 
 * @Description: 用户业务层逻辑
 * @author zhangxy
 * @date 2017年8月4日 上午9:32:58
 */
@Service
public class UserFilesServiceImpl implements IUserFilesService {
	
	Logger logger = LoggerFactory.getLogger(UserFilesServiceImpl.class);

    @Autowired
    private UserFilesDao userFilesDao;
    
    @Autowired
    private UserFilesMapper userFilesMapper;

    public UserFiles getUserFiles(String id) {
        return userFilesDao.getOne(id);
    }

    public Page<UserFiles> getAll(String openId,String delFlag,Pageable pageable) throws Exception{
        return userFilesDao.findAllByOpenIdAndDelFlag(openId,delFlag,pageable);
    }

	/**
	 * @Description: 保存当前用户上传的文件
	 * @Datatime 2017年8月5日 下午6:12:19 
	 * @return void    返回类型
	 */
	public void saveUserFiles(UserFiles userFiles) throws Exception {
		if(null != userFiles && StringUtils.isNotEmpty(userFiles.getOpenId())) {
			userFilesDao.saveAndFlush(userFiles);
		}
	}
	
	/**
	 * @Description: 根据opoenId,文件分类查询列表
	 * @Datatime 2017年8月6日 下午9:42:44 
	 * @return List<UserFiles>    返回类型
	 */
	public List<UserFiles> findByOpenIdAndFileCategory(String openId,String fileCategory) throws Exception{
		return userFilesDao.findByOpenIdAndFileCategory(openId,fileCategory);
	}
	
	/**
	 * @Description: 根据ID删除
	 * @Datatime 2017年8月7日 下午9:55:50 
	 * @return void    返回类型
	 */
	public void deleteById(String id) throws Exception {
		userFilesMapper.deleteById(id);
	}
	
	/**
	 * @Description: 根据ID查询模糊图片信息
	 * @Datatime 2017年8月7日 下午9:55:50 
	 * @return void    返回类型
	 */
	public Map<String, String> queryBlurInfoById(String id) throws Exception {
		return userFilesMapper.queryBlurInfoById(id);
	}
}
