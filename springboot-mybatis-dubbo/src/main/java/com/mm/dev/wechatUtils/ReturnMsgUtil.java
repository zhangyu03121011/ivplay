package com.mm.dev.wechatUtils;


import com.mm.dev.entity.wechat.ReturnMsg;
import com.mm.dev.enums.ExceptionEnum;

/**
 * @ClassName: ReturnMsgUtil 
 * @Description: http请求统一返回结果格式
 * @author zhangxy
 * @date 2017年5月8日 下午1:10:09
 */
@SuppressWarnings("rawtypes")
public class ReturnMsgUtil {
	@SuppressWarnings("unchecked")
	public static ReturnMsg success(Object object) {
		ReturnMsg result = new ReturnMsg();
        result.setCode("0");
        result.setMsg("成功");
        result.setData(object);
        return result;
    }

    public static ReturnMsg success() {
        return success(null);
    }

    public static ReturnMsg error(String code, String msg) {
    	ReturnMsg result = new ReturnMsg();
    	result.setStatus(false);
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
    
    public static ReturnMsg error(ExceptionEnum exceptionEnum) {
    	ReturnMsg result = new ReturnMsg();
    	result.setStatus(false);
        result.setCode(exceptionEnum.getIndex());
        result.setMsg(exceptionEnum.getValue());
        return result;
    }
}

