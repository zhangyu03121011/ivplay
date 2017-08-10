$(function(){
	
	
 		function chooseBoxCallBack(scroller,text,value){
 			var result ="" ;
 			for(var i=0 ;i<text.length ; i++){
 				result += (" "+text[i]);
 			} ;
 		

 			$('.data-input').html(result) ;
 		}
		
		var selectArea = new MobileSelectArea();
		selectArea.init({trigger:'.select-item',value:$('.defaultValue').data('value'),data:'./js/data.json',eventName:'click',callback:chooseBoxCallBack });
})