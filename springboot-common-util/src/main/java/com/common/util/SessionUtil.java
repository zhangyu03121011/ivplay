package com.common.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.common.base.constant.PrivilegeConstant;

/**
 * Session工具类
 * 
 * @author: HuiJunLuo
 * @date:2016年1月20日
 * @Copyright:Copyright (c) xxxx有限公司 2014 ~ 2015 版权所有
 */
public class SessionUtil {

	private static SessionUtil sessionUtil = null;

	public synchronized static SessionUtil getInstance() {
		if (sessionUtil == null) {
			sessionUtil = new SessionUtil();
		}
		return sessionUtil;
	}

	private SessionUtil() {
	}

	/**
	 * 保存session信息
	 * 
	 * @param request
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public void setSession(HttpServletRequest request, Object obj) throws Exception {
		HttpSession session = WebUtil.getInstance().getSession(request);
		session.setAttribute(PrivilegeConstant.SESSION_LOGIN_USER, obj);
		session.setMaxInactiveInterval(PrivilegeConstant.TIMEOUT_SESSION);
	}

	/**
	 * 获取session信息
	 * 
	 * @param request
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public Object getSession(HttpServletRequest request, String sessionId) throws Exception {
		HttpSession session = WebUtil.getInstance().getSession(request);
		if (session != null) {
			return session.getAttribute(PrivilegeConstant.SESSION_LOGIN_USER);
		}
		return null;
	}

	/**
	 * 获取Session信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Object getSession(HttpServletRequest request) throws Exception {
		return getSession(request, getSessionId(request));
	}

	/**
	 * 获取SessionId
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String getSessionId(HttpServletRequest request) {
		Object obj = request.getAttribute(PrivilegeConstant.SESSION_LOGIN_USER_SESSION_ID);
		if (obj != null && StringUtils.isNotEmpty(obj.toString())) {
			return obj.toString();
		} else {
			return request.getSession().getId();
		}
	}

	/**
	 * 获取token
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String getToken(HttpServletRequest request) {
		Object obj = request.getAttribute("token");
		if (obj != null && StringUtils.isNotEmpty(obj.toString())) {
			return obj.toString();
		} else {
			try {
				return CommUtil.getInstance().getToken(request.getSession().getId());
			} catch (Exception e) {
			}
		}
		return null;
	}

}
