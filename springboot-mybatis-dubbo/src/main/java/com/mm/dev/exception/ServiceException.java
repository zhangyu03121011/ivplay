package com.mm.dev.exception;

import com.mm.dev.enums.ExceptionEnum;

/**
 * @ClassName: ServiceException 
 * @Description: TODO
 * @author zhangxy
 * @date 2017年8月24日 上午9:37:42
 */
public class ServiceException extends RuntimeException{

	private static final long serialVersionUID = 4129756339384479097L;
	private Integer code;

    public ServiceException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMsg());
        this.code = exceptionEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
