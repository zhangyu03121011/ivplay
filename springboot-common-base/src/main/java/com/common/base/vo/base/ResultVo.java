package com.common.base.vo.base;

import java.io.Serializable;
import java.util.List;

import com.common.base.constant.ResultConstant;

/**
 * 处理结果Vo
 * 
 * @author: HuiJunLuo
 * @date:2015年12月28日
 * @Copyright:Copyright (c) xxxx有限公司 2014 ~ 2015 版权所有
 */
public class ResultVo<T> implements Serializable, ResultConstant {

	private static final long serialVersionUID = 2273610255200563857L;

	/**
	 * 结果
	 */
	private int res;

	/**
	 * 结果描述
	 */
	private String resDesc;

	/**
	 * 主键
	 */
	private String priKey;

	/**
	 * code
	 */
	private String code;

	/**
	 * 消息
	 */
	private String msg;

	/**
	 * 对象
	 */
	private T obj;

	/**
	 * 总数
	 */
	private long count;

	/**
	 * 结果集
	 */
	private List<T> list;

	/**
	 * 分页结果
	 */
	private PageListVo<T> pageListVo;

	/**
	 * token安全认证
	 */
	private String token;

	/**
	 * 视图路径
	 */
	private String viewPath;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getRes() {
		return res;
	}

	public void setRes(int res) {
		this.res = res;

		switch (res) {
		case RESULT_FAILURE:
			this.resDesc = "失败";
			break;
		case RESULT_SUCCESS:
			this.resDesc = "成功";
			break;
		case RESULT_NO_LOGIN:
			this.resDesc = "系统未登录";
			break;
		case RESULT_NO_EXISTS:
			this.resDesc = "数据不存在";
			break;
		case RESULT_HAS_EXISTS:
			this.resDesc = "数据已存在";
			break;
		case RESULT_EXCEPTION:
			this.resDesc = "系统异常";
			break;
		case RESULT_TIME_OUT:
			this.resDesc = "系统超时";
			break;
		case RESULT_CODE_ERROR:
			this.resDesc = "code错误";
			break;
		case RESULT_DATA_ERROR:
			this.resDesc = "数据错误";
			break;
		case RESULT_FORMAT_ERROR:
			this.resDesc = "格式错误";
			break;
		case RESULT_DATA_AVAILABLE:
			this.resDesc = "数据无效";
			break;
		case RESULT_DATA_NULL:
			this.resDesc = "数据为空";
			break;
		case RESULT_SIGN_ERROR:
			this.resDesc = "签名认证失败";
			break;
		}

	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getPriKey() {
		return priKey;
	}

	public void setPriKey(String priKey) {
		this.priKey = priKey;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public T getObj() {
		return obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public PageListVo<T> getPageListVo() {
		return pageListVo;
	}

	public void setPageListVo(PageListVo<T> pageListVo) {
		this.pageListVo = pageListVo;
	}

	public String getResDesc() {
		return resDesc;
	}

	public void setResDesc(String resDesc) {
		this.resDesc = resDesc;
	}

	public String getViewPath() {
		return viewPath;
	}

	public void setViewPath(String viewPath) {
		this.viewPath = viewPath;
	}

}
