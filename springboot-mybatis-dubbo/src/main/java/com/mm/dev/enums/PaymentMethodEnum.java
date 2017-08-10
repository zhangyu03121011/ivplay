package com.mm.dev.enums;

public enum PaymentMethodEnum {
	
	/** 微信jsapi支付 */
	WECHATPAY("微信支付","1");

	
	// 成员变量
    private String description;
    private String index;
    
    private PaymentMethodEnum(String description,String index) {
		this.description = description;
		this.index = index;
	}
    
    public static String getDescription(String index){
    	for (PaymentMethodEnum t : PaymentMethodEnum.values()) {
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

