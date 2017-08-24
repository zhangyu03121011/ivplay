package com.mm.dev.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mm.dev.entity.wechat.ReturnMsg;
import com.mm.dev.wechatUtils.ReturnMsgUtil;

/**
 * @ClassName: ExceptionHandle 
 * @Description: 统一异常处理类
 * @author zhangxy
 * @date 2017年8月24日 上午9:41:42
 */
@ControllerAdvice
public class ExceptionHandle {

    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ReturnMsg<Object> handle(HttpServletRequest request,Exception e) {
    	String requestURL = request.getRequestURL().toString();
        if (e instanceof ServiceException) {
            ServiceException serviceException = (ServiceException) e;
            logger.error(serviceException.getMessage(),e);
            return ReturnMsgUtil.error(serviceException.getCode(), serviceException.getMessage(),requestURL);
        }else {
            logger.error("【系统异常】{}", e);
            return ReturnMsgUtil.error(-1, "未知错误",requestURL);
        }
    }
}
