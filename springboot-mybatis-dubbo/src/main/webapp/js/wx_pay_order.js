/****************
	订单	
****************/
/**
 * order公共js类
 */
$(document).ready(function(){
	//获取订单号
	order_no = URL_PARAM.getParam("orderNo");
	//初始化微信支付
	payInit();
	
	//屏蔽分享功能
	if (typeof WeixinJSBridge == "undefined"){
	    if( document.addEventListener ){
	        document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
	    }else if (document.attachEvent){
	        document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
	        document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
	    }
	}else{
	    onBridgeReady();
	}
});

/**
 * 初始化微信支付
 */
function payInit() {
	var url = location.href.split('#')[0];
	$.get(rootPath + "/wechat/member/user/getWechatConfig?url=" + url,"",function(data){
		wx.config({
			debug: false,
			appId: data.appId,
			nonceStr: data.nonceStr,
			signature: data.signature,
			timestamp: parseInt(data.timestamp),
			jsApiList: [
	            'checkJsApi',
	            'chooseWXPay'
	        ]
		});
	});
};

//屏蔽分享功能
function onBridgeReady(){
	 WeixinJSBridge.call('hideOptionMenu');
}

/**
 * 微信支付回调
 */
function paymentCallback(orderNo) {
	var orderStatus = 0;
	 $.ajax({  
	        url : rootPath + "/wechat/member/order/queryOrder",  
	        data : {orderNo:orderNo},
	        async : false, 
	        type : "POST",  
	        success : function(result) {
	    		if(result.status && result.data){
	    			orderStatus = result.data.orderStatus;
		    	    if (orderStatus != 5) {
		    	    	Alert.disp_prompt('订单支付失败!');
		    	    } else {
		    	    	Alert.disp_prompt('订单支付成功');
		    			setTimeout(function(){
		    				 location.reload();
		    			},1000);
		    	    }
	        	}else{
	        		Alert.disp_prompt(result.msg);
	        	}
	        },
			error : function(result) {
				Alert.disp_prompt(result.msg);
			}
    });
}

/**
 * 订单支付
 * @param orderNo
 */
function payOrder(orderNo){
	
	if(window.location.host == "mall.m.pingbyt.cn") {
		alert("系统维护中");
	} else {
		orderNo = orderNo || order_no;
//		测试
		$.ajax({  
		    url : rootPath + "/wechat/member/order/update/status",  
		    data : {orderNo:orderNo, orderStatus:5},
		    async : false, 
		    type : "POST",  
		    success : function(result) {
		    	if(result.status){
		    		Alert.disp_prompt('订单支付成功');
		    		setTimeout(function(){
		    			window.location.href = "order_me.html";
		    		},2000);
		    	}else{
		    		Alert.disp_prompt(result.msg);
		    	}
		    }  
		});
	}
	
	//生产
//	wx.ready(function () {
//			$.get(rootPath + "/wechat/member/user/getWechatPayConfig","orderNo="+orderNo,function(data){
//				if(data != null){
//				    wx.chooseWXPay({
//				    	 timestamp: data.timestamp,
//				    	 nonceStr: data.nonceStr,
//				    	 package: data.pkg,
//				    	 signType: 'MD5',
//				    	 paySign: data.paySign,
//				    	 success: function (res) {
//				    		 // 支付成功后的回调函数
//				    		 paymentCallback(orderNo);
//				    	 },
//				    	 fail: function (){
//				    		 alert("支付失败");
//				    	 }
//				    });
//				}else{
//					alert("支付异常");
//				}
//			});
//	});
}

/**
 * 取消订单
 */
function cancelledOrder(orderNo){
	orderNo = orderNo || order_no;
	var bool =window.confirm('是否取消订单？');	
	if(bool){
   	 $.ajax({  
	        url : rootPath + "/wechat/member/order/update/status",  
	        data : {orderNo:orderNo, orderStatus:3},
	        async : false, 
	        type : "POST",  
	        success : function(result) {
	        	if(result.status){
	        		Alert.disp_prompt('订单取消成功');
	        		setTimeout(function(){
	        			 location.reload();
	        		},2000);
	        	}else{
	        		Alert.disp_prompt(result.msg);
	        	}
	        }  
	    });
	}
}

/**
 * 确认收货
 */
function confirmReceive(orderNo){
	orderNo = orderNo || order_no;
	var orderReturn=true;
	var bool;
	$.ajax({
		url:rootPath+"/wechat/member/order/queryOrderReturnByOrderNo",
		data:{orderNo:orderNo},
		async:false,
		type:"POST",
		success:function(result){
			if(result.status){
				orderReturn=false;
			}
		}
	});
	
	if(orderReturn){
		bool =window.confirm('是否确认收货？');			
	}else{
		bool =window.confirm('此订单中存在退货中商品,是否确认收货？');
	}
	if(bool){
		 $.ajax({  
  	        url : rootPath + "/wechat/member/order/update/status",  
  	        data : {orderNo:orderNo, orderStatus:1},
  	        async : false, 
  	        type : "POST",  
  	        success : function(result) {
  	        	if(result.status){
  	        		Alert.disp_prompt('订单确认收货成功');
  	        		setTimeout(function(){
  	        			location.reload();
  	        		},2000);
  	        	}else{
  	        		Alert.disp_prompt(result.msg);
  	        	}
  	        }  
  	    });
	}
}

/**
 * 跳转订单详情
 * @param orderNo
 */
function toOrderDetail(orderNo){
	window.location.href = "order_detail.html?orderNo="+orderNo;
}


/**
 * 删除订单
 */
function deleteOrder(orderNo){
	orderNo = orderNo || order_no;
	var bool =window.confirm('是否删除订单？');	
	if(bool){
   	 $.ajax({  
	        url : rootPath + "/wechat/member/order/delete",  
	        data : {orderNo:orderNo},
	        async : false, 
	        type : "POST",  
	        success : function(result) {
	        	if(result.status){
	        		Alert.disp_prompt('订单删除成功');
	        		setTimeout(function(){
	        			 location.href = "order_me.html";
	        		},2000);
	        	}else{
	        		Alert.disp_prompt(result.msg);
	        	}
	        }  
	    });		
	}
}
