/**
 * 用户上传的文件列表
 */
var upload_list = (function() {
	
	function toggle_header(style){
		if(style == 1){
			$('#originalImg').attr('class','headerActive');
			$('#blurImg').attr('class','headerstatic');
			toggle_img(1);
		}else{
			$('#originalImg').attr('class','headerstatic');
			$('#blurImg').attr('class','headerActive');
			toggle_img(2);
		}
	}
	
	function toggle_img(style){
		var originalImg = $('.recode').find('.originalImg');
		var blurImg = $('.recode').find('.blurImg');
		if(style == 1){
			for(var i = 0; i < originalImg.length; i++){
				$(originalImg[i]).css('display','inline-block');
				$(blurImg[i]).css('display','none');
			}
		}else{
			for(var i = 0; i < originalImg.length; i++){
				$(originalImg[i]).css('display','none');
				$(blurImg[i]).css('display','inline-block');
			}
		}
	}
	
	// 分页参数
	var start = 0;
	var count = 20;
	var flag = true;
	var code = t = qmoreUrl = '';
	
	getImgInfo(1);
	
	//获取url的参数
	/*if(my.getQuery()){
		code = my.getQuery().code;
		t = my.getQuery().t; 
		
		alert('code:' + code)
		
		getImgInfo(1);					
	}else{
		alert('没有获取到url的参数');
	}*/
	
	function getImgInfo(type){
		if(!flag){
			return false;
		}
		myc.showProgress();
		
		/*if(type == 1){//第一次
			var urlPost = 'load/'+ start +'/' + count + '?code=' + code + '&t=' + t;
		}else{
			var urlPost = 'load/'+ start +'/' + count;
		}*/
		
		var urlPost = 'load/'+ start +'/' + count;
		
		my.ajax(urlPost,{},function(ret){
			myc.hideProgress();
			if(ret && ret.status){
				alert(JSON.stringify(ret));
				var data = ret.details;
				if(data){
					if(data.length){
						var dataInters = '';
						for(var i = 0; i < data.length; i++){
							var mymoreUrl = data[i].short_url;
							if(data[i].short_url == null){
								var short_urlMore =mymoreUrl;
							}else{
								var short_urlMore =mymoreUrl.split(" ");
							}
							dataInters = {
								key : data[i].key,
								mpid : data[i].mpid,
								qrc_type : data[i].type,
								date : myc.getTime((new Date(data[i].create_time))),
								blur_url : data[i].blurUrl,
								shortUrl : short_urlMore,
								url : data[i].shotUrl,									
								state : data[i].state,										
								scans : data[i].scans,										
								random : data[i].random,										
								amount : data[i].price,										
								amount_min : data[i].price_min,																			
								income : data[i].income,																			
								sigma : data[i].blur,										
								pays : data[i].pays,									
							};
							if(!data[i].blur){
								dataInters.sigma = 60;
							}
							createDatasLeft(dataInters);
						}
						start += data.length;
					}
				}else{
					myc.toast({
						msg : '没有更多了'
					});
					flag = false;
				}
			}else{		
				if(ret.block){
					window.location.href = 'wx_block.html';
				}
//				alert('ret:' + JSON.stringify(ret) + 'err:' + JSON.stringify(err) + 'status:' + JSON.stringify(status));
			}
		});
	}
	
	//传入数据源，利用模版生成HTML
	function createDatasLeft(dataInters) {
		var interText = doT.template(document.getElementById("tmprecodes_left").innerText);
		document.getElementById("recodesLeft").insertAdjacentHTML('beforeEnd', interText(dataInters));
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
	
	window.onscroll = function() {					
		if (getScrollTop() + getClientHeight() == getScrollHeight()) {//判断滚动条到达底部的条件,并自动触发下面AJAX									
			getImgInfo(2);
		}
	} 
	
	function openWin(dom){
		window.location.href = serverUrl + 'link/'+ $(dom).attr('data-mpid') +'/wx_qrc_setting?k=' + $(dom).attr('data-key');
	}
	
	function openReward(dom){
		window.location.href = 'wx_reward_detail.html?k=' + $(dom).attr('data-key') + '&p=' + $(dom).attr('data-mpid');
	}
	
	function stop(){
		event.stopPropagation();
		event.preventDefault();
	}
	function delImg(dom){
		myc.confirm({
			title : '删除确认',
			msg : '您是否确定删除该内容？'
		},function(ret){
			if(ret.buttonIndex == 1){
				myc.showProgress({
					title:'删除中'
				});
//				alert($(dom).attr('data-id'));
				my.ajax('delete/' + $(dom).attr('data-key'),{},function(ret,err,status){
					myc.hideProgress();
//					alert(JSON.stringify(ret));
					if(ret && ret.success){		
						myc.toast({
							msg : '删除成功'
						});
						$(dom).parent().parent().remove();
					}else{	
						myc.toast({
							msg : '删除失败，请重新删除'
						});
					}
				});
			}
		})
		
	}
})();
