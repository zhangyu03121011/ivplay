$(function(){
	/*banner轮播图*/
	var bannerSwiper = new Swiper('.swiper-container',{
		autoplay : 3000,//可选选项，自动滑动
		loop : true,//可选选项，开启循环
		autoplayDisableOnInteraction:false, //触摸后可继续滑动
		pagination:'.banner-pagination'
	});

	/*编辑按钮*/
	touch.on('.right-text','tap',function(){
		
		$('.mystore-page').toggleClass('edit');
		$(this).html($(this).html()=='编辑'? '完成' :'编辑');
	})
})
