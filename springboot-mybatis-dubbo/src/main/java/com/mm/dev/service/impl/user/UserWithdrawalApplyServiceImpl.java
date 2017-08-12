package com.mm.dev.service.impl.user;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mm.dev.dao.jpa.user.UserWithdrawalApplyDao;
import com.mm.dev.dao.mapper.user.UserWithdrawalApplyMapper;
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
    
    @Autowired
    private UserWithdrawalApplyMapper userWithdrawalApplyMapper;
    
    /**
     * @Description: 保存提现申请记录
     * @DateTime:2017年8月10日 下午2:27:41
     * @return void
     * @throws
     */
    public void save(UserWithdrawalApply userWithdrawalApply) throws Exception {
    	
    	userWithdrawalApplyDao.saveAndFlush(userWithdrawalApply);
    }
    
    /**
     * @Description: 分页查询我的提现列表
     * @Datatime 2017年8月5日 下午6:15:42 
     * @return Page<User>    返回类型
     */
	public List<Map<String, String>> queryAllByApplyOpenIdAndDelFlag(String openId,String delFlag,Pageable pageable) throws Exception{
		return userWithdrawalApplyMapper.queryAllByApplyOpenIdAndDelFlag(openId, delFlag, pageable);
	}
	
	public static void main(String[] args) {
		System.out.println(URLDecoder.decode("http%3A%2F%2Fmnsppds.huishengdianz.com%2Fs%2F54%2Fwx_withdraw.html%3Fp%3Df928b9bed111%26t%3Dc546213ad16ff1d4cf0df7925cdb4b1c&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect"));
	}
}
