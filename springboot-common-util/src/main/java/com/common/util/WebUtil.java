package com.common.util;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * WebUtils
 * 
 * @author: HuiJunLuo
 * @date:2015年12月28日
 * @Copyright:Copyright (c) xxxx有限公司 2014 ~ 2015 版权所有
 */
public class WebUtil {

    private static WebUtil webUtil = null;

    public synchronized static WebUtil getInstance() {
        if (webUtil == null) {
            webUtil = new WebUtil();
        }
        return webUtil;
    }

    private WebUtil() {
    }

    /**
     * 获取session信息
     * 
     * @param request
     * @return
     */
    public HttpSession getSession(HttpServletRequest request) {
        return request.getSession();
    }

    /**
     * 获取Base路径
     * 
     * @param request
     * @return
     */
    public String getBasePath(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath() + "/";
    }

    /**
     * 获取映射后Base路径
     * 
     * @param request
     * @return
     */
    public String getRealBasePath(HttpServletRequest request) {
        String url = request.getScheme() + "://" + request.getServerName();
        if (request.getServerPort() != 80) {
            url += ":" + request.getServerPort();
        }
        url += "/";
        return url;
    }

    /**
     * 查找指定请求中的指定名称的Cookie
     * 
     * @param request
     * @param name
     *            Cookie名称 @return cookie的值
     */
    public Cookie findCookie(HttpServletRequest request, String name) {
        if (request != null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null && cookies.length > 0) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(name)) {
                        return cookie;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 查找指定请求中的指定名称Cookie的值，如果不存在将返回null。
     * 
     * @param request
     *            请求。
     * @param name
     *            Cookie名称。
     * @return cookie的值。
     */
    public String findCookieValue(HttpServletRequest request, String name) {
        Cookie cookie = findCookie(request, name);
        return cookie != null ? cookie.getValue() : null;
    }

    /**
     * 增加一个Cookie,使用默认域名。
     * 
     * @param request
     *            请求。
     * @param response
     *            响应。
     * @param name
     *            Cookie名称 。
     * @param value
     *            Cookie的值。
     * @param maxAge
     *            生命周期。
     */
    public void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value,
            int maxAge) {
        addCookie(request, response, name, value, null, maxAge);
    }

    /**
     * 增加一个Cookie,使用指定域名。
     * 
     * @param request
     *            请求。
     * @param response
     *            响应。
     * @param name
     *            Cookie名称 。
     * @param value
     *            Cookie的值。
     * @param maxAge
     *            生命周期。
     */
    public void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value,
            String domain, int maxAge) {
        String contextPath = request.getContextPath();
        if (contextPath == null || contextPath.isEmpty()) {
            contextPath = "/";
        }
        addCookie(request, response, name, value, domain, contextPath, maxAge);
    }

    /**
     * 增加一个Cookie.ContextPath如果为空或者长度为0，将使用"/".
     * 
     * @param request
     *            当前请求。
     * @param response
     *            当前响应。
     * @param name
     *            cookie名称
     * @param value
     *            cookie值
     * @param domain
     *            cookie域名
     * @param contextPath
     *            cookie路径。
     * @param maxAge
     *            有效时间。
     */
    public void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value,
            String domain, String contextPath, int maxAge) {
        if (request != null && response != null) {
            Cookie cookie = new Cookie(name, value);
            cookie.setMaxAge(maxAge);
            cookie.setSecure(request.isSecure());

            if (contextPath == null || contextPath.isEmpty()) {
                cookie.setPath("/");
            } else {
                cookie.setPath(contextPath);
            }

            if (domain != null && !domain.isEmpty()) {
                cookie.setDomain(domain);
            }

            response.addCookie(cookie);
        }
    }

    /**
     * 失效一个Cookie.
     * 
     * @param request
     *            当前请求。
     * @param response
     *            当前响应。
     * @param name
     *            Cookie名称。
     * @param domain
     *            Cookie域名。
     * @param contextPath
     *            有效路径。
     */
    public void failureCookie(HttpServletRequest request, HttpServletResponse response, String name, String domain,
            String contextPath) {
        if (request != null && response != null) {
            addCookie(request, response, name, null, domain, contextPath, 0);
        }
    }

    /**
     * 将指定的Cookie失效掉。
     * 
     * @param request
     *            请求
     * @param response
     *            响应。
     * @param name
     *            cookie名称。
     * @param domain
     *            cookie的域名。
     */
    public void failureCookie(HttpServletRequest request, HttpServletResponse response, String name, String domain) {
        String contextPath = request.getContextPath();
        if (contextPath == null || contextPath.isEmpty()) {
            contextPath = "/";
        }
        failureCookie(request, response, name, domain, contextPath);
    }

    /**
     * 将指定的Cookie失效掉。
     * 
     * @param request
     *            请求
     * @param response
     *            响应。
     * @param name
     *            cookie名称。
     */
    public void failureCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        failureCookie(request, response, name, null);
    }

    /**
     * 获取请求的完整地址。
     * 
     * @param request
     *            请求。
     * @return 完整地址。
     */
    public String completeTheRequestAddress(HttpServletRequest request) {
        StringBuilder buff = new StringBuilder(request.getRequestURL().toString());
        String queryString = request.getQueryString();
        if (queryString != null) {
            buff.append("?").append(queryString);
        }
        return buff.toString();
    }

    /**
     * 一个客户端转向的方便工具方法.可以选择使用301或者302方式进行跳转.
     * 
     * @param response
     *            当前响应.
     * @param url
     *            需要转向的地址.
     * @param movePermanently
     *            true表示进行301永久跳转,false表示302临时跳转. IOException I/O异常.
     */
    public void redirect(HttpServletResponse response, String url, boolean movePermanently) throws IOException {
        if (!movePermanently) {
            response.sendRedirect(url);
        } else {
            response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
            response.setHeader("Location", url);
        }
    }

}
