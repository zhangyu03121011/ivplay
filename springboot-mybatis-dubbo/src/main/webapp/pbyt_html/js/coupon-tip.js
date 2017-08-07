$(function(){
	touch.on('.receive-btn','tap',function(){
		$('.coupon-area').addClass('open');
	});
	touch.on('.cancel','tap',function(){
		$('.coupon-area').removeClass('open');
	})
})