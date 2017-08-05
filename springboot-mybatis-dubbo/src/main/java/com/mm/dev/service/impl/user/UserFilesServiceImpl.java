package com.mm.dev.service.impl.user;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mm.dev.dao.jpa.user.UserFilesDao;
import com.mm.dev.dao.mapper.user.UserMapper;
import com.mm.dev.entity.user.User;
import com.mm.dev.entity.user.UserFiles;
import com.mm.dev.service.impl.wechat.WechatServiceImpl;
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

    public UserFiles getUserFiles(String id) {
        return userFilesDao.getOne(id);
    }

    public Page<UserFiles> getAll(Pageable pageable){
        return userFilesDao.findAll(pageable);
    }

	/**
	 * @Description: 保存当前用户上传的文件
	 * @Datatime 2017年8月5日 下午6:12:19 
	 * @return void    返回类型
	 */
	public void saveUserFiles(UserFiles userFiles) throws Exception {
		if(null != userFiles && StringUtils.isNotEmpty(userFiles.getUserId())) {
			userFilesDao.saveAndFlush(userFiles);
		}
	}
}
