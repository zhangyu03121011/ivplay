package com.mm.dev.enums;

public enum WithdrawalApplyStatusEnum {

	/** 未处理 */
	wait("未处理","1"),

	/** 处理成功 */
	success("处理成功","2"),

	/** 处理失败 */
	failure("处理失败","3");

	
	// 成员变量
    private String description;
    private String index;
    
    private WithdrawalApplyStatusEnum(String description,String index) {
		this.description = description;
		this.index = index;
	}
    
    public static String getDescription(String index){
    	for (WithdrawalApplyStatusEnum t : WithdrawalApplyStatusEnum.values()) {
            if (t.getIndex() == index) {
                return t.description;
            }
        }
        return null;
    }

	public  String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}
    
}

