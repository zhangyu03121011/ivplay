package com.mm.dev.service.impl.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mm.dev.dao.jpa.user.UserWithdrawalApplyDao;
import com.mm.dev.entity.user.UserWithdrawalApply;
import com.mm.dev.service.user.IUserWithdrawalApplyService;

/**
 * @ClassName: UserWithdrawalApplyServiceImpl 
 * @Description: 提现申请管理
 * @author zhangxy
 * @date 2017年8月4日 上午9:32:58
 */
@Service
public class UserWithdrawalApplyServiceImpl implements IUserWithdrawalApplyService {
	
	Logger logger = LoggerFactory.getLogger(UserWithdrawalApplyServiceImpl.class);

    @Autowired
    private UserWithdrawalApplyDao userWithdrawalApplyDao;
    
    /**
     * @Description: 保存提现申请记录
     * @DateTime:2017年8月10日 下午2:27:41
     * @return void
     * @throws
     */
    public void save(UserWithdrawalApply userWithdrawalApply) throws Exception {
    	
    	userWithdrawalApplyDao.saveAndFlush(userWithdrawalApply);
    }
}
