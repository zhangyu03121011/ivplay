$(function(){
	touch.on('.change-pic','tap',function(){
		$('.action-sheet').addClass('open');
	});
	touch.on('.cancel','tap',function(){
		$('.action-sheet').removeClass('open');
	})
})