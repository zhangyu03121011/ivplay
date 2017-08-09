/**
 * 朋友圈
 */
// 分页参数
var start = 0;
var count = 1;
var flag = true;
	
(function() {
	
	//初始哈数据
	initData();
	
	window.onscroll = function() {					
		if (getScrollTop() + getClientHeight() == getScrollHeight()) {//判断滚动条到达底部的条件,并自动触发下面AJAX									
			initData();
		}
	} 
})();

//传入数据源，利用模版生成HTML
function createDatasLeft(dataInters) {
	var interText = doT.template(document.getElementById("tmprecodes_left").innerText);
	document.getElementById("recodesLeft").insertAdjacentHTML('beforeEnd', interText(dataInters));
}	

function initData() {
	if(!flag){
		return false;
	}
	var urlPost = '/user/'+ start +'/' + count + '/userFiles/list'
	
	my.ajaxGet(urlPost,function(ret,err,status){
		if(ret && ret.status){
			var data = ret.data[0].content;
			if(data){
				if(data.length){
					var dataInters = '';
					for(var i = 0; i < data.length; i++){
						var blur_url = data[i].filePath;
						if( -1 != blur_url.indexOf(".")) {
							blur_url = blur_url.substring(0,blur_url.lastIndexOf(".")) + "_blur.jpg";
						}
						dataInters = {
								key : data[i].id,
								openId : data[i].openId,
								nickName : decodeURIComponent(data[i].nickName),
								title : data[i].title,
								headimgurl : data[i].headimgurl,
								blurUrl : blur_url,
								index : i
							};
						createDatasLeft(dataInters);
					}
					start ++;
				}
			}else{
				myc.toast({
					msg : '没有更多了'
				});
				flag = false;
			}
		}
	});
}


//获取滚动条当前的位置 
function getScrollTop() {
	var scrollTop = 0;
	if (document.documentElement && document.documentElement.scrollTop) {
		scrollTop = document.documentElement.scrollTop;
	} else if (document.body) {
		scrollTop = document.body.scrollTop;
	}
	return scrollTop;
}

//获取当前可是范围的高度 
function getClientHeight() {
	var clientHeight = 0;
	if (document.body.clientHeight && document.documentElement.clientHeight) {
		clientHeight = Math.min(document.body.clientHeight, document.documentElement.clientHeight);
	} else {
		clientHeight = Math.max(document.body.clientHeight, document.documentElement.clientHeight);
	}
	return clientHeight;
}

//获取文档完整的高度 
function getScrollHeight() {
	return Math.max(document.body.scrollHeight, document.documentElement.scrollHeight);
}
