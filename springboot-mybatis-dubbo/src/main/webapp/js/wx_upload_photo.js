/**
 * 上传
 */
var upload_photo = (function() {
	var p = o = ap = code = '';
	var mylimit = '';
	if(my.getQuery()){
		p = my.getQuery().p;
		o = my.getQuery().o;	
		ap = my.getQuery().ap;	
		code = my.getQuery().code;	
//		alert('p:' + p + ',o:' + o + ',ap:' + ap + ',code:' + code);
	}else{
//		alert('没有获取到url的参数');
	}
	
	var ajaxurl = 'load?z=1';
	
	if(o){
		ajaxurl = ajaxurl + '&o=' + o;
	}
	
	if(ap){
		ajaxurl = ajaxurl + '&ap=' + ap;
	}
	
	if(code){
		ajaxurl = ajaxurl + '&code=' + code;
	}
	
	/**
	 * ajax请求后台初始化参数
	 */
	my.ajax(ajaxurl,{},function(ret,status,err){
		  // alert(JSON.stringify(ret));
		if(ret.success){
		    var preferences = ret.preferences;
			if(preferences==null) {
				preferences = {};
				preferences.blur = 60;
				preferences.price = 3;
				preferences.priceMin = 2;
				preferences.imageLayout = 4;
				preferences.random = true;
		
			}
			if(preferences.blur == null){
				preferences.blur = 60;
			}
			if(preferences.price == null)	{
				preferences.price = 3;
			} 
			if(preferences.priceMin == null){
				preferences.priceMin = 2;
			}
			if(preferences.imageLayout == null){
				preferences.imageLayout = 4;
			}
			if(preferences.random == null){
				preferences.random = true;
			}
			mylimit = preferences.minAmountLimit;
			console.info(preferences);
			// if(preferences.price <= preferences.minAmountLimit){
			// 	preferences.price = preferences.minAmountLimit
			// }
			//	alert(JSON.stringify(preferences));
			var _bar = document.querySelector('.controlbar-box'); //父元素
			var bar_round = document.querySelector('.c-round'); //小圆
			var bar_num = document.querySelector('.c-bg'); //白色线
			var sigmao = preferences.blur;
			var sigma = sigmao / 100 * $('.controlbar-box').width();
			var offwidth = $('.controlbar-box').width() - $('.c-round').width();
			if(offwidth <= sigma){
				sigma = offwidth;
			}
			bar_round.style.left = sigma + 45 + 'px';
			bar_num.style.width = sigma + 'px';
			bar_num.style.zIndex = '20';
			document.querySelector('.c-progress').innerHTML = sigmao;
													
			$('#inputNumber').val(parseFloat(preferences.price));
			$('#lableNumber').html(parseFloat(preferences.price));
			$('#mininputNumber').val(parseFloat(preferences.priceMin));
			$('#minlableNumber').html(parseFloat(preferences.priceMin));

			// // 不能小于0.2元
			// alert($('#inputNumber').val())
			// $('#inputNumber').bind('input propertychange', function() { 
	 	// 		alert($('#inputNumber').val())
	 	// 		var lessTwo = $('#inputNumber').val();
	 	// 		if(lessTwo < preferences.minAmountLimit){
	 	// 			myc.toast({
			// 			msg : '不能小于' + preferences.minAmountLimit + '元'
			// 		});
			// 		$('#inputNumber').blur(function () {  
			// 		    var lessTwoo = $(this).val();
			// 		    if(lessTwoo < preferences.minAmountLimit){
			// 		    	// alert($(this).val());
			// 		    	$('#inputNumber').val(parseFloat(preferences.minAmountLimit))
			// 		    	$('#lableNumber').html(parseFloat(preferences.minAmountLimit));
			// 		    } 
			// 		});  
	 	// 		}
			// });	
			// $('#mininputNumber').bind('input propertychange', function() { 
	 	// 		// alert($('#inputNumber').val())
	 	// 		var lessTwo = $('#mininputNumber').val();
	 	// 		if(lessTwo < preferences.minAmountLimit){
	 	// 			myc.toast({
			// 			msg : '不能小于' + preferences.minAmountLimit + '元'
			// 		});
			// 		$('#mininputNumber').blur(function () {  
			// 		    var lessTwoo = $(this).val();
			// 		    if(lessTwoo < preferences.minAmountLimit){
			// 		    	// alert($(this).val());
			// 		    	$('#mininputNumber').val(parseFloat(preferences.minAmountLimit))
			// 		    	$('#minlableNumber').html(parseFloat(preferences.minAmountLimit));
			// 		    } 
			// 		});  
	 	// 		}
			// });	

			if(!preferences.random){
				$('#suijiSelect').removeClass('bg_dui');
				$('#randomNum').css('display','none');
				$('#dao').css('display','none');
			}else{
				$('#suijiSelect').addClass('bg_dui');
			}
			
//			$('.ModelSelect').find('span').removeClass('bg_dui');
			
			if(preferences.imageLayout == 4){
				$('#suijiSelect4').addClass('bg_dui');											
			}else if(preferences.imageLayout == 0){
				$('#suijiSelect0').addClass('bg_dui');
			}
			
		}
	});
	
	/**
	 * 初始化设值模糊度插件
	 */
	$(function() {	
		var _bar = document.querySelector('.controlbar-box'); //父元素
		var bar_round = document.querySelector('.c-round'); //小圆
		var bar_num = document.querySelector('.c-bg'); //白色线
		
		var sigmao = 60;
		var sigma = sigmao / 100 * $('.controlbar-box').width();
		var offwidth = $('.controlbar-box').width() - $('.c-round').width();
		if(offwidth <= sigma){
			sigma = offwidth;
		}
		bar_round.style.left = sigma + 45 + 'px';
		bar_num.style.width = sigma + 'px';
		bar_num.style.zIndex = '20';
		document.querySelector('.c-progress').innerHTML = sigmao;
		
		mark();	
		function mark() {
			var mark_body = document.querySelector('.mark_body');
			//圆宽度的一半
			var x = parseInt(bar_round.offsetWidth) / 2;
			var max_w = _bar.offsetWidth - x; //在没有offset().left时的最大x坐标
			var bar_offsetLeft = $('.controlbar-box').offset().left + 45; //元素和左窗体的距离:用不到e.pageY和.offset()top
			function drags(event) {
				var _pagex = event.touches[0].pageX; //得到指针x轴坐标
				
				//判断e.pageX 
				if (_pagex < bar_offsetLeft + x) { //最左边	：_pagex最小时
					_pagex = bar_offsetLeft;
				} else if (_pagex > max_w + bar_offsetLeft) { //最右边：_pagex最大时
					_pagex = max_w + bar_offsetLeft - x;
				} else {
					_pagex = _pagex - x;
				}
				//小圆定位
				//					bar_round.style.position = 'absolute';
//				roundleft=_pagex - bar_offsetLeft;
				bar_round.style.left = (_pagex - bar_offsetLeft+45) + 'px';
				bar_num.style.width = (_pagex - bar_offsetLeft) + 'px';
				bar_num.style.zIndex = '20';
				//得到数值%
				var ratio = Math.abs(_pagex - bar_offsetLeft) / (max_w - x);
				document.querySelector('.c-progress').innerHTML = Math.ceil(ratio * 100);

			}
			//元素按下事件	
			_bar.addEventListener('touchstart', bar_down, false);

			function bar_down() {
				event.preventDefault(); //阻止元素的默认事件
				event.stopPropagation(); // 阻止元素冒泡事件
				drags(event);
				//					_bar.onclick = function(event){//元素点击事件
				//						drags(event);
				//					}
			}
			_bar.addEventListener('touchmove', touchmove, false);

			function touchmove(event) {
				event.preventDefault(); //阻止元素的默认事件
				event.stopPropagation(); // 阻止元素冒泡事件
				drags(event);
			}
			//document按下弹起事件
			_bar.addEventListener('touchend', function(event) {
				event.preventDefault(); //阻止元素的默认事件
				event.stopPropagation(); // 阻止元素冒泡事件
				document.removeEventListener('touchmove', touchmove, false);	
				setBlur();
			}, false);
		}
	});
	
	/**
	 * 开始提交上传
	 */
	$('#ctlBtn').click(function(){	
		if(!$('#erweimaImg').attr('src')){
			myc.toast({
				msg : '请选择您要上传的照片'
			});
			return false;
		}
		//模糊度值
		var blurNumber = $('#blurNumber').html();
		
		//二维码样式值
//		var imageLayout = $('.ModelSelect').find('.bg_dui').attr('data-id');
		
		//判断是否勾选中随机按钮
		var random = 1;
		
		//勾选中随机按钮
		if($('#suijiSelect').hasClass('bg_dui')){
			random = 2;
		}			
		
		//随机最大值
		var amount = parseFloat($('#inputNumber').val()).toFixed(2);
		
		//随机最小值
		var amount_min = parseFloat($('#mininputNumber').val()).toFixed(2);
		
		//没有勾选中随机按钮
		if(random == 1){
			amount_min = amount;
		}
		if(amount_min < mylimit){
			myc.toast({
				msg : '必须要大于' + mylimit +'元' 
			});
			return false;
		}
		
		if(amount < mylimit){
			myc.toast({
				msg : '必须要大于' + mylimit +'元' 
			});
			return false;
		}

		if(amount <= 0){
			myc.toast({
				msg : '请输入金额'
			});
			return false;
		}
		
		if(!amount){
			myc.toast({
				msg : '您输入的不是数字哦'
			});
			return false;
		}
		
		if(!amount_min){
			myc.toast({
				msg : '您输入的不是数字哦'
			});
			return false;
		}
		
		//主要是这里添加参数
		uploader.options.formData._sid_ = localStorage.getItem('_sid_');
		
		//(0-不随,1-随)
		uploader.options.formData.random = random;
		uploader.options.formData.price = amount;
		uploader.options.formData.priceMin = amount_min;
		uploader.options.formData.blur = blurNumber;
//		uploader.options.formData.openid = o;
		//(0-不共享,1-共享)
//		uploader.options.formData.share = 1;
		//0-黑边;4-二维码在左下;2-二维码在中间
//		uploader.options.formData.imageLayout  = imageLayout;
		//标题
		uploader.options.formData.title = $("#title").val();
		uploader.retry();
	});
	
	/**
	 * 显示用户选择的文件大小
	 */
	function sizeBig(num){
		if(num<1024){
			return num+'B';
		}else if(num<1024*1024){
			return (num/1024).toFixed(2)+'KB';
		}else if(num<1024*1024*1024){
			return ((num/1024)/1024).toFixed(2)+'M';
		}
	}
	
	/**
	 * 设值模糊度
	 */
	function setBlur(){
		var blurNumber = $('#blurNumber').html();
		
		if(parseInt(blurNumber) < 20){
			myc.alert({
				msg : '模糊度不能小于20'
			})
			var bar_round = document.querySelector('.c-round'); //小圆
			var bar_num = document.querySelector('.c-bg'); //白色线
			var sigmao = 20;
			
			
			var sigma = sigmao / 100 * $('.controlbar-box').width();
			var offwidth = $('.controlbar-box').width() - $('.c-round').width();
			if(offwidth <= sigma){
				sigma = offwidth;
			}
			bar_round.style.left = sigma + 45 + 'px';
			bar_num.style.width = sigma + 'px';
			bar_num.style.zIndex = '20';
			document.querySelector('.c-progress').innerHTML = sigmao;
			blurNumber = sigmao;
//			return false;
		}
	}
	
	//选择二维码样式
	/*$('.ModelSelect').click(function(){
		$('.ModelSelect').find('span').removeClass('bg_dui');
		$(this).find('span').addClass('bg_dui');
	});*/

    /**
     * 设值随机
     */
	$('#suijiSelect').click(function(){
		if($(this).hasClass('bg_dui')){
			$(this).removeClass('bg_dui');
			$('#randomNum').css('display','none');	
			$('#dao').css('display','none');
		}else{
			$(this).addClass('bg_dui');
			$('#randomNum').css('display','inline-block');
			$('#dao').css('display','inline-block');
		}
		var mininputNumber = $('#mininputNumber').val().replace(/\.$/g, "");
		var inputNumber = $('#inputNumber').val().replace(/\.$/g, "");
		if(parseFloat(mininputNumber) > parseFloat(inputNumber)){
			$('#inputNumber').val(mininputNumber);
			$('#lableNumber').html(mininputNumber);
		}
	});
	
	/**
	 * 设值随机打赏最大值最小值
	 */
	function onblurNum(dom,style){
		var mininputNumber = $('#mininputNumber').val().replace(/\.$/g, "");//最后一个点赋空
		var inputNumber = $('#inputNumber').val().replace(/\.$/g, "");
		$('#lableNumber').html(inputNumber);
		$('#minlableNumber').html(mininputNumber);

		if(!mininputNumber){
			$('#mininputNumber').val(1);
			$('#minlableNumber').html(1);
		}
		
		if(!inputNumber){
			$('#inputNumber').val(1);
			$('#lableNumber').html(1);
		}
		if(!$('#suijiSelect').hasClass('bg_dui')){
			return false;
		}
		if(style == 1){					
			if(parseFloat(mininputNumber) > parseFloat(inputNumber)){
				$('#inputNumber').val(mininputNumber);
				$('#lableNumber').html(mininputNumber);
			}
		}else{					
			if(parseFloat(inputNumber) < parseFloat(mininputNumber)){
				$('#inputNumber').val(mininputNumber);
				$('#lableNumber').html(mininputNumber);
			}
		}
	}
	
	/**
	 * 验证随机打赏最大值最小值
	 */
	function inputNumberF(dom,style) {
		if(style == 1){
			// 只能输入一个小数点
			dom.value =dom.value.replace(/[^\d.]/g, "").replace(/^\./g, "").replace(/\.{2,}/g, ".").replace(".", "$#$").replace(/\./g, "").replace("$#$", ".").replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3');
			if(dom.value){
				if(dom.value > 99){
					dom.value = 99;
					$('#minlableNumber').html(99);						
				}else{
					$('#minlableNumber').html(dom.value);
				}					
			}else{
//					dom.value = 0;
				$('#minlableNumber').html(0);
			}	
		}else{
			dom.value =dom.value.replace(/[^\d.]/g, "").replace(/^\./g, "").replace(/\.{2,}/g, ".").replace(".", "$#$").replace(/\./g, "").replace("$#$", ".").replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3');
			if(dom.value){
				if(dom.value > 99){
					dom.value = 99;
					$('#lableNumber').html(99);						
				}else{
					$('#lableNumber').html(dom.value);
				}					
			}else{
//					dom.value = 0;
				$('#lableNumber').html(0);
			}	
		}
					
	}
	
	/**
	 * 初始化WebUploader上传插件
	 */
	var uploader = WebUploader.create({
//		auto : true, //设置为 true 后，不需要手动调用上传，有文件选择即开始上传。
	    // swf文件路径
	    swf: '../js/Uploader.swf',
	
	    // 文件接收服务端。
	    server: serverUrl + 'upload/image',
	    // 选择文件的按钮。可选。
	    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
	    pick: {
	    	id : '#picker',
	    	multiple : false
	    },
		fileNumLimit : 1,
		compress  : false,
	    // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
	    resize: false,
	    // 只允许选择图片文件
	    accept:{
	    	title: 'Images',
		    mimeTypes: 'image/*'
	    }
	});
	
	/**
	 * 当有文件被添加进队列的时候 由于webuploader不处理UI逻辑，所以需要去监听fileQueued事件来实现。
	 */
	uploader.on( 'fileQueued', function( file ) {
//		myc.toast({
//			msg : '选择成功'
//		})

		$list = $('#thelist');
		
	     var $li = $(
            '<div id="' + file.id + '" class="file-item thumbnail" style="margin:20px 0" > ' +
                '<img>' +
                '<div class="info">' + file.name + '</div>' +
                '<p class="state" style="color:red;">'+sizeBig(file.size)+'</p>' +
                '<p class="state" style="color:red;">等待上传</p>' +
                
            '</div>'
            ),
        $img = $li.find('img');
	    // $list为容器jQuery实例
	    $list.append( $li );
		
	    // 创建缩略图
	    // 如果为非图片文件，可以不用调用此方法。
	    // thumbnailWidth x thumbnailHeight 为 100 x 100
	    uploader.makeThumb( file, function( error, src ) {
//	        if ( error ) {
//	            $img.replaceWith('<span>不能预览</span>');
//	            return;
//	        }
//	
//	        $img.attr( 'src', src );
			document.getElementById("erweimaImg").src = src;
	    }, 100, 100 );
	});
	
	
	/**
	 * 文件上传进度 文件上传中，Web Uploader会对外派送uploadProgress事件，其中包含文件对象和该文件当前上传进度。
	 * 文件上传过程中创建进度条实时显示。
	 */
	uploader.on( 'uploadProgress', function( file, percentage ) {
//	     var $li = $( '#'+file.id ),
//	        $percent = $li.find('.barContainer .bar');
//	
//	    // 避免重复创建
//	    if ( !$percent.length ) {
//	        $percent = $('<div class="barContainer"><div class="bar" style="width: 100%"></div></div>').appendTo($li).find('.bar');
//	    }
//	
//	    $li.find('p.state').text('上传中');
//
//	    $percent.html((percentage * 100).toFixed(2) + '%');
	   	myc.showProgress({
	   		title : '上传中',
	   		text : (percentage * 100).toFixed(2) + '%'
	   	});
	    if(percentage*100 == 100){			    	
	    	myc.showProgress({
	    		title:'等待服务器处理'
	    	});
	    }
	});
	
	/**
	 * 文件上传成功，给item添加成功class, 用样式标记上传成功。
	 */
	uploader.on( 'uploadSuccess', function( file , response ) {
//	    $( '#'+file.id ).find('p.state').text('已上传');
	    myc.hideProgress();
	    myc.toast({
			location:'middle',
			msg:'上传成功'
		});
	    
		if(response.status){
//			$('#imgErWeima').attr('src',response.entryImage);
//			myc.alert({
//				msg : JSON.stringify(response)
//			});
			setTimeout(function(){
				wx.closeWindow();
			},2000);
		}else{
			myc.toast({
				msg : '上传出错,请重新上传'
			});
		}		     			     			      
	});
	
	/**
	 * 只能上传一个视频文件
	 */
	uploader.on('error', function(file) {

		if(file=="Q_EXCEED_NUM_LIMIT"){
			myc.toast({
				msg:'只能上传一个图片文件'
			});
			return false;
		}else if(file=="Q_TYPE_DENIED"){
			myc.toast({
				msg:'不支持这个图片格式'
			});
			return false;
		}
	});
	
	/**
	 * 文件上传失败，显示上传出错。
	 */
	uploader.on( 'uploadError', function( file ) {
		myc.toast({
			msg:'上传出错,请重新上传'
		});
	});
	
	/**
	 * 完成上传完了，成功或者失败，先删除进度条。
	 */
	uploader.on( 'uploadComplete', function( file ) {
		myc.hideProgress();
//		console.log('完成上传完了，成功或者失败');
//	    $( '#'+file.id ).find('.progress').fadeOut();
	});
})();
