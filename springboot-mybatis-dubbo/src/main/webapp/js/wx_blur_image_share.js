/**
 * 查看分享模糊图片
 */
var data=[];
var canvasWidth = 400;
var canvasHeight = 900;
var blur_image_share = (function() {
	var id = COMMON.getUrlParam("id");
	var fileNames = COMMON.getUrlParam("fileNames");
	var qrcSrc = COMMON.rootPath + "/" + COMMON.imageFolder + "/" + id + "/qrcode_" + fileNames;
	var blurSrc = COMMON.rootPath + "/" + COMMON.imageFolder + "/" + id + "/blur_" + fileNames;
	data.push(blurSrc);
	data.push(qrcSrc);
	getImgNaturalDimensions(callback,qrcSrc,1);
	getImgNaturalDimensions(callback,blurSrc,2);
	
})();
function draw(){
	var c = document.getElementById("canvas"),
	ctx = c.getContext('2d'),
	len=data.length;
	c.width=canvasWidth;
	c.height=canvasHeight;
	ctx.rect(0,0,c.width,c.height);
	ctx.fillStyle='rgba(225,225,225,0.5)';
	ctx.fill();
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
					ctx.fillText('长按图片识别二维码，查看高清原图',400,60);
				}
				else{
					ctx.drawImage(img,10,400,c.width,c.height);
				}
				
				drawing(n+1);// 递归
			}
		}else{
			// 保存生成作品图片
			$("#img1").attr("src",c.toDataURL());
		}
	}
	drawing(0);
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
	canvasWidth += width;
	canvasHeight += height;
	if(n == 2) {
		// 生成画布
		draw();
	}
}
