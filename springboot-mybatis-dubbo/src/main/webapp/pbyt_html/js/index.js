$(function(){

	/*banner轮播图*/
	var bannerSwiper = new Swiper('.swiper-container',{
		autoplay : 3000,//可选选项，自动滑动
		loop : true,//可选选项，开启循环
		autoplayDisableOnInteraction:false, //触摸后可继续滑动
		pagination:'.banner-pagination'
	});

	/*局部滑动选择，使用swiper插件*/

	var smPicSwiper = new Swiper('.swiper-sm-container',{
		
		loop : false,
		slidesPerView:2.5,
		freeMode:true
	})
})