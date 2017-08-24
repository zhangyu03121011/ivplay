/**
 * 查看分享模糊图片
 */
var data=[];
var canvasWidth = 400;
var canvasHeight = 900;
var blurFileId = my.getUrlParam("id");
var title = null;
var qrcSrc = "/userFiles/getPayShareQrImage?id="+blurFileId;
var blurSrc = null;

/**
 * 查看模糊图片
 */
var blur_image_share = (function() {
	//获取当前用户头像昵称
	my.ajaxGet('/userFiles/query/'+blurFileId,function(ret,err,status){
		if(ret && ret.status){
			var data = ret.data;
			title = data.title;
			blurSrc = "/"+data.blurFilePath;
		}
	},false)
	
	data.push(blurSrc);
	data.push(qrcSrc);
	getImgNaturalDimensions(callback,qrcSrc,1);
	getImgNaturalDimensions(callback,blurSrc,2);
	
})();

//合并图片
function draw(){
	var c = document.getElementById("canvas"),
	ctx = c.getContext('2d'),
	len=data.length;
	c.width=canvasWidth;
	c.height=canvasHeight;
	ctx.rect(0,0,c.width,c.height);
	ctx.fillStyle='rgba(225,225,225,0.5)';
	ctx.fill();
	drawing(0);
	
	function drawing(n){
		if(n<len){
			var img=new Image;
			// img.crossOrigin = 'Anonymous'; //解决跨域
			img.src=data[n];
			img.margin = "0,20";
			img.hspace="30";
			img.vspace="30";
			img.border="10px solid #e5e5e5";
			img.onload=function(){
				if(n==1){
					ctx.drawImage(img,10,50,300,300);
					ctx.fillStyle = '#708090';
					// 设置文字属性
					ctx.font = '48px sans-serif';
					ctx.textBaseline = 'top';
					// 填充字符串
					ctx.fillText(title,380,60);
					ctx.fillText('长按图片识别二维码，查看高清原图',380,150);
				}
				else{
					ctx.drawImage(img,10,400,c.width,c.height-300);
				}
				
				drawing(n+1);// 递归
			}
		}else{
			// 保存生成作品图片
			$("#img1").attr("src",c.toDataURL());
		}
	}
}

function getImgNaturalDimensions(callback,imgSrc,n) {
	var image = new Image()
	image.src = imgSrc;
	image.onload = function() {
		callback(image.width, image.height,n)
	}
}

// 获取图片的原始尺寸
function callback(width,height,n) {
	canvasHeight += height;
	if(n == 2) {
		canvasWidth += width;
		// 生成画布
		draw();
	}
}
