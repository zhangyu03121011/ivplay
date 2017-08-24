/**
 * 打赏圈
 */
// 分页参数
var start = 0;
var count = 2;
var flag = true;
var dataPswpUid = 0;

//用户信息
//当前用户id
var openId = my.getUrlParam("openId");
//当前用户头像
var userHeadimgurl = null;
//当前用户昵称
var userNickName = null;

(function() {
	//初始哈数据
	initData(openId);
	//分页判断
	window.onscroll = function() {					
		if (getScrollTop() + getClientHeight() == getScrollHeight()) {//判断滚动条到达底部的条件,并自动触发下面AJAX									
			initData(openId);
		}
	} 
	
	//图片相册
	var initPhotoSwipeFromDOM = function(gallerySelector) {
	    // 解析来自DOM元素幻灯片数据（URL，标题，大小...）
	    // (children of gallerySelector)
	    var parseThumbnailElements = function(el) {
	        var thumbElements = el.childNodes,
	            numNodes = thumbElements.length,
	            items = [],
	            figureEl,
	            linkEl,
	            size,
	            item,
				divEl;

	        for(var i = 0; i < numNodes; i++) {

	            figureEl = thumbElements[i]; // <figure> element

	            // 仅包括元素节点
	            if(figureEl.nodeType !== 1) {
	                continue;
	            }
				divEl = figureEl.children[0];
	            linkEl = divEl.children[0]; // <a> element
				
	            size = linkEl.getAttribute('data-size').split('x');

	            // 创建幻灯片对象
	            item = {
	                src: linkEl.getAttribute('href'),
	                w: parseInt(size[0], 10),
	                h: parseInt(size[1], 10)
	            };



	            if(figureEl.children.length > 1) {
	                // <figcaption> content
	                item.title = figureEl.children[1].innerHTML; 
	            }

	            if(linkEl.children.length > 0) {
	                // <img> 缩略图节点, 检索缩略图网址
	                item.msrc = linkEl.children[0].getAttribute('src');
	            } 

	            item.el = figureEl; // 保存链接元素 for getThumbBoundsFn
	            items.push(item);
	        }

	        return items;
	    };

	    // 查找最近的父节点
	    var closest = function closest(el, fn) {
	        return el && ( fn(el) ? el : closest(el.parentNode, fn) );
	    };

	    // 当用户点击缩略图触发
	    var onThumbnailsClick = function(e) {
	        e = e || window.event;
	        e.preventDefault ? e.preventDefault() : e.returnValue = false;

	        var eTarget = e.target || e.srcElement;

	        // find root element of slide
	        var clickedListItem = closest(eTarget, function(el) {
	            return (el.tagName && el.tagName.toUpperCase() === 'FIGURE');
	        });

	        if(!clickedListItem) {
	            return;
	        }

	        // find index of clicked item by looping through all child nodes
	        // alternatively, you may define index via data- attribute
	        var clickedGallery = clickedListItem.parentNode,
	            childNodes = clickedListItem.parentNode.childNodes,
	            numChildNodes = childNodes.length,
	            nodeIndex = 0,
	            index;

	        for (var i = 0; i < numChildNodes; i++) {
	            if(childNodes[i].nodeType !== 1) { 
	                continue; 
	            }

	            if(childNodes[i] === clickedListItem) {
	                index = nodeIndex;
	                break;
	            }
	            nodeIndex++;
	        }



	        if(index >= 0) {
	            // open PhotoSwipe if valid index found
	            openPhotoSwipe( index, clickedGallery );
	        }
	        return false;
	    };

	    // parse picture index and gallery index from URL (#&pid=1&gid=2)
	    var photoswipeParseHash = function() {
	        var hash = window.location.hash.substring(1),
	        params = {};

	        if(hash.length < 5) {
	            return params;
	        }

	        var vars = hash.split('&');
	        for (var i = 0; i < vars.length; i++) {
	            if(!vars[i]) {
	                continue;
	            }
	            var pair = vars[i].split('=');  
	            if(pair.length < 2) {
	                continue;
	            }           
	            params[pair[0]] = pair[1];
	        }

	        if(params.gid) {
	            params.gid = parseInt(params.gid, 10);
	        }

	        return params;
	    };

	    var openPhotoSwipe = function(index, galleryElement, disableAnimation, fromURL) {
	        var pswpElement = document.querySelectorAll('.pswp')[0],
	            gallery,
	            options,
	            items;

	        items = parseThumbnailElements(galleryElement);

	        // 这里可以定义参数
	        options = {
	          barsSize: { 
	            top: 100,
	            bottom: 100
	          }, 
			   fullscreenEl : false,
				shareButtons: [
				{id:'wechat', label:'分享微信', url:'#'},
				{id:'weibo', label:'新浪微博', url:'#'},
				{id:'download', label:'保存图片', url:'{{raw_image_url}}', download:true}
				],

	            // define gallery index (for URL)
	            galleryUID: galleryElement.getAttribute('data-pswp-uid'),

	            getThumbBoundsFn: function(index) {
	                // See Options -> getThumbBoundsFn section of documentation for more info
	                var thumbnail = items[index].el.getElementsByTagName('img')[0], // find thumbnail
	                    pageYScroll = window.pageYOffset || document.documentElement.scrollTop,
	                    rect = thumbnail.getBoundingClientRect(); 

	                return {x:rect.left, y:rect.top + pageYScroll, w:rect.width};
	            }

	        };

	        // PhotoSwipe opened from URL
	        if(fromURL) {
	            if(options.galleryPIDs) {
	                // parse real index when custom PIDs are used 
	                for(var j = 0; j < items.length; j++) {
	                    if(items[j].pid == index) {
	                        options.index = j;
	                        break;
	                    }
	                }
	            } else {
	                // in URL indexes start from 1
	                options.index = parseInt(index, 10) - 1;
	            }
	        } else {
	            options.index = parseInt(index, 10);
	        }

	        // exit if index not found
	        if( isNaN(options.index) ) {
	            return;
	        }

	        if(disableAnimation) {
	            options.showAnimationDuration = 0;
	        }

	        // Pass data to PhotoSwipe and initialize it
	        gallery = new PhotoSwipe( pswpElement, PhotoSwipeUI_Default, items, options);
	        gallery.init();
	    };

	    // loop through all gallery elements and bind events
	    var galleryElements = document.querySelectorAll( gallerySelector );
	    for(var i = 0, l = galleryElements.length; i < l; i++) {
	        galleryElements[i].setAttribute('data-pswp-uid', i+1);
	        galleryElements[i].onclick = onThumbnailsClick;
	    }

	    // Parse URL and open gallery if it contains #&pid=3&gid=1
	    var hashData = photoswipeParseHash();
	    if(hashData.pid && hashData.gid) {
	        openPhotoSwipe( hashData.pid ,  galleryElements[ hashData.gid - 1 ], true, true );
	    }
		};
		
		// execute above function
		initPhotoSwipeFromDOM('.my-gallery');

		$(".my-gallery>figure>div").each(function(){
			$(this).height($(this).width());
		});
		
		
})();

function openBlurShare(openId) {
	location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+my.getOpenId()+"&redirect_uri=http%3a%2f%2fjacky.tunnel.qydev.com%2fwechat%2fcallback&response_type=code&scope=snsapi_base&state=3&connect_redirect=1#wechat_redirect";
}


function openMyHome(openId) {
	location.href = "/home/home.html?openId="+openId;
}

function more(obj,id) {
	if ($('#txt'+id).is(":hidden")) {
		$('#p'+id).hide();
		$('#txt'+id).show();
		obj.innerHTML='收起';
	} else {
		$('#p'+id).show();
		$('#txt'+id).hide();
		obj.innerHTML='全文';
	}
}

//传入数据源，利用模版生成HTML
function createDatasLeft(dataInters) {
	var interText = doT.template(document.getElementById("tmprecodes_left").innerText);
	document.getElementById("recodesLeft").insertAdjacentHTML('beforeEnd', interText(dataInters));
}	

/**
 * 初始化数据
 * @returns {Boolean}
 */
function initData(openId) {
	if(openId == "undefined" || openId == undefined){
		openId = "";
	}
	if(!flag){
		return false;
	}
	//查询当前用户列表
	var	urlPost = '/user/'+ start +'/' + count + '/userFiles/list?openId='+openId
	
	//获取当前用户头像昵称
	my.ajaxGet('/user/queryUserInfo?openId='+openId,function(ret,err,status){
		if(ret && ret.status){
			var data = ret.data;
			userHeadimgurl = data.headimgurl;
			userNickName = data.nickName;
			$("#userHeadimgurl").attr("src",userHeadimgurl);
			$("#userNickName").text(decodeURIComponent(userNickName));
		}
	})
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
						dataPswpUid++;
						dataInters = {
								key : data[i].id,
								openId : data[i].openId,
								nickName : decodeURIComponent(data[i].nickName),
								title : data[i].title+"范德萨发生法律房价房间打开了手机法兰克福佛挡杀佛方式方法松岛枫范德萨发生非法顺丰到付佛挡杀佛付付付多所付都是非法的方法",
								headimgurl : data[i].headimgurl,
								blurUrl : blur_url,
								index : dataPswpUid
							};
						createDatasLeft(dataInters);
					}
					start ++;
				} else{
					myc.toast({
						msg : '没有更多了'
					});
					flag = false;
				}
			}
		}
	},false);
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
