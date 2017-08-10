package com.mm.dev.service.user;

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
}
