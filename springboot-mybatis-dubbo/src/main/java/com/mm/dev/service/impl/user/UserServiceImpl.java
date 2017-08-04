package com.mm.dev.service.impl.user;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mm.dev.constants.WechatConstant;
import com.mm.dev.dao.jpa.user.UserDao;
import com.mm.dev.dao.mapper.user.UserMapper;
import com.mm.dev.entity.user.User;
import com.mm.dev.service.impl.wechat.WechatServiceImpl;
import com.mm.dev.service.user.IUserService;
import com.mm.dev.util.UserSession;

/**
 * @ClassName: UserServiceImpl 
 * @Description: 用户业务层逻辑
 * @author zhangxy
 * @date 2017年8月4日 上午9:32:58
 */
@Service
public class UserServiceImpl implements IUserService {
	
	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private WechatServiceImpl wechatServiceImpl;

    @Override
    public User getUser(Long id) {
        return userDao.getOne(id);
    }

    @Override
    public Page<User> getAll(Pageable pageable){
        return userDao.findAll(pageable);
    }

    @Override
    public List<User> getUserList() {
        return userMapper.findAll();
    }

    @Override
    public Page<User> getUserAll(Pageable pageable) {
        return userMapper.getUserAll(pageable);
    }

    @Transactional
    @Override
    public void save() throws Exception{
    	for (int i = 0; i < 50; i++) {
    		User user = new User();
    		user.setUserName(String.valueOf(i));
    		user.setPassWord(String.valueOf(i));
    		userDao.saveAndFlush(user);
		}
    }

    @Transactional
    @Override
    public void saveUser() throws Exception{
    	for (int i = 51; i < 100; i++) {
    		User user = new User();
    		user.setUserName(String.valueOf(i));
    		user.setPassWord(String.valueOf(i));
    		userMapper.save(user);
		}
    }
    
    /**
	 * 根据openId查询会员ID
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getuserIdByopenId(String openId) throws Exception{
		Map<String,Object> result = userMapper.getuserIdByopenId(openId);
		if(null != result){
			if(result.containsKey("name")){
				String nickName = (String) result.get("name");
				result.put("name", URLDecoder.decode(nickName, "utf-8"));
			}
		}
		return result;
	}
    
    /**
	 * 保存用户信息
	 * @param toUserName
	 */
	public void weixinRegister(String openId,int attenation) {
		try {
			if (StringUtils.isNotEmpty(openId)) {
				UserSession.setSession(WechatConstant.OPEN_ID, openId);
				logger.info("保存授权回调openId到session：{}",openId);
				// 根据openId查询出当前用户
				Map<String, Object> member = getuserIdByopenId(openId);
				//获取用户头像，性别，头像URL
				Map<String, Object> map = wechatServiceImpl.getWeChatInfo(openId);
				if (member == null) {
					String nickName = URLEncoder.encode(map.get("nickname").toString(), "utf-8");
					User userInfo = new User();
					userInfo.setOpenId(openId);
					userInfo.setAttenation(attenation);//是否已关注公众号（0：未 1：已）
					userInfo.setUserName(nickName);  //昵称
					userInfo.setSex((String)map.get("sex"));//性别
					userInfo.setHeadimgurl((String)map.get("headimgurl"));//头像
					//@todo
//					userDao.createUser(userInfo);
					
				} else { //更新session值:userId，rankId，merchartId
					String userId = member.get("id").toString();
					logger.info("根据openId={}查询出当前userId={}" ,openId,userId);
					UserSession.setSession(WechatConstant.USER_ID, userId);
					String userName = (String) member.get("userName");
					UserSession.setSession(WechatConstant.USER_NAME, userName);
					// 更新用户头像、昵称和性别 TODO
//					userDao.updateUserHead(map);
					if(WechatConstant.attention_status_1.equals(attenation) && WechatConstant.attention_status_0.equals(member.get("attention"))) {
						Map<String, Object> attenationMap = new HashMap<String, Object>();
						attenationMap.put("attention",attenation);
						attenationMap.put("openId", openId);
//						TODO
//						userDao.updateUserAttenation(attenationMap);
						attenationMap = null;
					}
				}
			}
		} catch (Exception e) {
			logger.info("保存用户信息异常======"+e);
			e.printStackTrace();
		}
	}
	
	/*
	 * 更新用户头像，性别，昵称
	 */
	@Transactional(readOnly = false)
	public void updateUserHead(Map<String,Object> map) throws Exception {
		if(null!= map){
			User user = new User();
			user.setSex((String)map.get("sex"));
			if(map.containsKey("nickname")){
				String nickname = URLEncoder.encode((String)map.get("nickname"), "utf-8");;
				user.setUserName(nickname);
			}
			user.setHeadimgurl((String)map.get("headimgurl"));
			user.setOpenId((String)map.get("openId"));
			//TODO
//			userDao.updateUserHead(user);
		}
	}
    
    /**
	 * 取消关注保存用户信息
	 * @param toUserName
	 */
	public void unSubscribe(String openID) {
		try {
			logger.info("取消关注操作openId==={}",openID);
			if(StringUtils.isNotEmpty(openID)) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put(WechatConstant.OPEN_ID,openID);
				params.put("attention", WechatConstant.attention_status_0);
				//TODO
//				userDao.updateUserAttenation(params);
			}
		} catch (Exception e) {
			logger.info("取消关注保存用户信息异常======"+e);
			e.printStackTrace();
		}
	}
}
