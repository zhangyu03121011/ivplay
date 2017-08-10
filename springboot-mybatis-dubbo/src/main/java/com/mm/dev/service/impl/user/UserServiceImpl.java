package com.mm.dev.service.impl.user;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.common.util.UUIDGenerator;
import com.mm.dev.constants.WechatConstant;
import com.mm.dev.dao.jpa.user.UserDao;
import com.mm.dev.dao.mapper.user.UserMapper;
import com.mm.dev.entity.user.User;
import com.mm.dev.service.impl.wechat.WechatServiceImpl;
import com.mm.dev.service.user.IUserService;
import com.mm.dev.wechatUtils.UserSession;

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
    public User getUser(String id) {
        return userDao.getOne(id);
    }

    @Override
    public Page<User> getAll(Pageable pageable){
        return userDao.findAll(pageable);
    }

    /**
	 * 根据openId查询会员ID
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	public User getuserBaseInfoByopenId(String openId) throws Exception{
		User user = userMapper.getuserBaseInfoByopenId(openId);
		return user;
	}
    
    /**
	 * 保存用户信息
	 * @param toUserName
	 */
	public void weixinRegister(HttpServletRequest request,String openId,String attenation) throws Exception{
		try {
			if (StringUtils.isNotEmpty(openId)) {
				//根据openId查询出当前用户
				User user = getuserBaseInfoByopenId(openId);
				//老用户点击菜单授权
				if(null != user && (StringUtils.isNotEmpty(attenation) && WechatConstant.attention_status_1.equals(attenation))) {
					return;
				} else {
					User userInfo = new User();
					//获取用户头像，性别，头像URL
					userInfo = wechatServiceImpl.getWeChatInfo(openId);
					try {
						//获取用户访问IP地址
						String ipAddr = getIp2(request);
						userInfo.setLastLoginIpAddress(ipAddr);
						logger.info("获取用户访问IP地址ipAddr=",ipAddr);
					} catch (Exception e) {
						logger.error("获取客户端IP地址异常",e);
					}
					//如果是老用户点击关注注册进来更新获取用户最新信息
					if(null != user && (StringUtils.isNotEmpty(attenation) && WechatConstant.attention_status_2.equals(attenation))) {
						userInfo.setId(user.getId());
						save(userInfo);
					} else if(null == user) { //新用户
						save(userInfo);
					}
				}
			}
		} catch (Exception e) {
			logger.info("保存用户信息异常",e);
		}
	}
	
	public static String getIp2(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if(index != -1){
                return ip.substring(0,index);
            }else{
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            return ip;
        }
        return request.getRemoteAddr();
    }
	
	/*
	 * 新增/更新用户
	 */
	@Transactional(readOnly = false)
	public void save(User user) throws Exception {
		if(null != user){
			user.setUpdateTime(new Date());
			if(StringUtils.isEmpty(user.getId())) {
				user.setCreateTime(new Date());
				user.setId(UUIDGenerator.generate());
				userDao.saveAndFlush(user);
			} else {
				userMapper.updateUserInfo(user);
			}
			UserSession.setSession(WechatConstant.USER_ID, user.getId());
		}
	}
    
    /**
	 * 取消关注保存用户信息
	 * @param toUserName
	 */
	public void unSubscribe(String openId) {
		try {
			logger.info("取消关注操作openId==={}",openId);
			if(StringUtils.isNotEmpty(openId)) {
				User user = new User();
				user.setOpenId(openId);
				user.setUnAttenation(WechatConstant.attention_status_2);
				userMapper.updateUserInfo(user);
			}
		} catch (Exception e) {
			logger.info("取消关注保存用户信息异常======"+e);
			e.printStackTrace();
		}
	}
	
	/**
	 * @Description:朋友圈首页分页查询列表
	 * @Datatime 2017年8月6日 下午9:42:44 
	 * @return List<UserFiles>    返回类型
	 */
	public List<Map<String, String>> findUserFilesList(String openId,Pageable pageable) throws Exception{
		return userMapper.findUserFilesList(pageable,openId);
	}
}
