$(function(){
	/*轮播图js*/

	var bannerSwiper = new Swiper('.swiper-container',{
		autoplay : 3000,//可选选项，自动滑动
		loop : true,//可选选项，开启循环
		autoplayDisableOnInteraction:false, //触摸后可继续滑动
		pagination:'.banner-pagination'
	});

	/*处理tab*/
	touch.on('.head-text','tap',function(){
		var content = $(this).data('name');
		$(this).addClass('active').siblings().removeClass('active');
		$('.content-'+content).removeClass('hide-content').siblings().addClass('hide-content');

	});

	touch.on('.product-detail-page-select','tap',function(){
		$('.spec-choose-content').toggleClass('open');

	});

/*	处理数量按钮*/
	touch.on('.buy-quantity .cal','tap',function(){
		var type = $(this).data('cal');
		var num = +$('.num').html();
		if(type == 'add'){
			$('.buy-quantity .num').html(++num);
			$('.buy-quantity .sub').removeClass('disabled');

		}
		else if(type == 'sub' && num > 1){
			$('.buy-quantity .num').html(--num);
			if(num <= 1){
				$('.buy-quantity .sub').addClass('disabled');
			}
		}
		else{
			return;
		}

	});

	/*关闭数量选择页面*/
	touch.on('.product-detail-page .closeIcon','tap',function(){
		$('.spec-choose-content').toggleClass('open');
	});

/*	处理规格单选*/
	touch.on('.spec span','tap',function(){
		$(this).addClass('selected').siblings().removeClass('selected');
	})

 /*导航栏开关*/
  $(function() {
        touch.on('.circle', 'touchstart', function() {
            $(".circle").toggleClass('close');
        });
    });
})