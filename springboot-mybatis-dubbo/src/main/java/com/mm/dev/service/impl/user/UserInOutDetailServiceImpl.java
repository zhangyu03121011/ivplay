package com.mm.dev.service.impl.user;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mm.dev.dao.jpa.user.UserInOutDetailDao;
import com.mm.dev.dao.mapper.user.UserInOutDetailMapper;
import com.mm.dev.entity.user.UserInOutDetail;
import com.mm.dev.service.user.IUserInOutDetailService;

/**
 * @ClassName: UserInOutServiceImpl 
 * @Description: 收支明细
 * @author zhangxy
 * @date 2017年8月4日 上午9:32:58
 */
@Service
public class UserInOutDetailServiceImpl implements IUserInOutDetailService {
	
	Logger logger = LoggerFactory.getLogger(UserInOutDetailServiceImpl.class);

    @Autowired
    private UserInOutDetailDao userInOutDetailDao;
    
    @Autowired
    private UserInOutDetailMapper userInOutDetailMapper;
    
    
    
    /**
     * @Description: 保存收支明细记录
     * @DateTime:2017年8月10日 下午2:27:41
     * @return void
     * @throws
     */
    public void save(UserInOutDetail userInOutDetail) throws Exception {
    	
    	userInOutDetailDao.saveAndFlush(userInOutDetail);
    }
    
    /**
     * @Description: 分页查询我的收支明细
     * @Datatime 2017年8月5日 下午6:15:42 
     * @return Page<User>    返回类型
     */
	public List<Map<String, String>> queryAllByOpenIdAndDelFlag(String openId,Pageable pageable) throws Exception{
		return userInOutDetailMapper.queryAllByOpenIdAndDelFlag(openId, pageable);
	}
}
