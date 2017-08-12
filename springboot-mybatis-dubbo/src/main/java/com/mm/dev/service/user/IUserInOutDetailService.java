package com.mm.dev.service.user;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.mm.dev.entity.user.UserInOutDetail;


/**
 * @Description: IUserInOutService
 * @author Jacky
 * @date 2017年8月4日 下午10:04:02
 */
public interface IUserInOutDetailService {
	
	 /**
     * @Description: 保存收支明细记录
     * @DateTime:2017年8月10日 下午2:27:41
     * @return void
     * @throws
     */
    public void save(UserInOutDetail userInOutDetail) throws Exception;
    
    /**
     * @Description: 分页查询我的收支明细
     * @Datatime 2017年8月5日 下午6:15:42 
     * @return Page<User>    返回类型
     */
	List<Map<String, String>> queryAllByOpenIdAndDelFlag(String openId,Pageable pageable) throws Exception;
}
