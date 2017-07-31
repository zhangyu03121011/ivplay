package com.mm.dev.entity.wechat;

/**
 * 消息内容
 * @ClassName: Data 
 * @Description: TODO
 * @author zhangxy
 * @date 2017年5月21日 下午5:41:49
 */
public class Data {
	private Data_first first;
	
	private Data_remark remark;
	
	private Data_name name;

	public Data_first getFirst() {
		return first;
	}

	public void setFirst(Data_first first) {
		this.first = first;
	}

	public Data_remark getRemark() {
		return remark;
	}

	public void setRemark(Data_remark remark) {
		this.remark = remark;
	}

	public Data_name getName() {
		return name;
	}

	public void setName(Data_name name) {
		this.name = name;
	}
}
