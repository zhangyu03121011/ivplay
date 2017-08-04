package com.mm.dev.dao.mapper.user;

import com.mm.dev.entity.user.User;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created by Lipengfei on 2015/6/26.
 */
public interface UserMapper {

    List<User> findAll();

    Page<User> getUserAll(@Param("pageable")Pageable pageable);

    void save(User user);
    
    /**
   	 * 根据openId查询会员id
   	 * @param openId
   	 * @return
   	 * @throws Exception
   	 */
   	public Map<String,Object>  getuserIdByopenId(String openId) throws Exception;

}
