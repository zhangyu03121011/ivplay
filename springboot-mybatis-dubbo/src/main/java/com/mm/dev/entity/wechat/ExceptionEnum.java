package com.mm.dev.entity.wechat;

/**
 * @Description: 异常枚举类错误代码
 * @author Jacky
 * @date 2017年8月3日 下午7:13:48
 */
public enum ExceptionEnum {
	/** 系统内部异常 */
	system_error("系统内部异常","500"),
	
	/** 参数不能为空 */
	param_not_null("参数不能为空", "1001"), 
	
	/** 未登录 */
	not_login("未登录", "1002"), 
	
	/** 该用户已存在 */
	user_exist("该会员已存在", "1003"),
	
	/** 该用户不存在 */
	user_not_exist("该会员不存在","1004")
	;

	private String value;
	private String index;

	private ExceptionEnum(String value, String index) {
		this.value = value;
		this.index = index;
	}

	public static String getvalue(String index) {
		for (ExceptionEnum t : ExceptionEnum.values()) {
			if (t.getIndex().equals(index)) {
				return t.value;
			}
		}
		return null;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}
}
