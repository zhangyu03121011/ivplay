package com.mm.dev.service.user;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.mm.dev.entity.user.UserWithdrawalApply;


/**
 * @Description: IUserWithdrawalApplyService
 * @author Jacky
 * @date 2017年8月4日 下午10:04:02
 */
public interface IUserWithdrawalApplyService {
	
	 /**
     * @Description: 保存提现申请记录
     * @DateTime:2017年8月10日 下午2:27:41
     * @return void
     * @throws
     */
    public void save(UserWithdrawalApply userWithdrawalApply) throws Exception;
    
    /**
     * @Description: 分页查询我的提现列表
     * @Datatime 2017年8月5日 下午6:15:42 
     * @return Page<User>    返回类型
     */
	List<Map<String, String>> queryAllByApplyOpenIdAndDelFlag(String openId,String delFlag,Pageable pageable) throws Exception;
}
