var AJAX = {
	call : function (url, type, dataType, data, async, success, error) {
		$.ajax({
			url : url,
			type : type,
			dataType : dataType,
			data : data,
			async : async,
			cache : false,
			success : function(result) {
				success.call(this, result);
			},
			error : function(result) {
				if (error != null) {error.call(this, result);}
			}
		});
	},
	callWithJson : function (url, type, dataType, data, async, success, error) {
		$.ajax({
			url : url,
			type : type,
			dataType : dataType,
			data : data,
			async : async,
			cache : false,
			contentType: "application/json; charset=utf-8",
			success : function(result) {
				success.call(this, result);
			},
			error : function(result) {
				if (error != null) {error.call(this, result);}
			}
		});
	}
};

function getCookie(objName){
     var arrStr = document.cookie.split(";");
     for(var i = 0;i < arrStr.length;i++){
         var temp = arrStr[i].split("=");
         if( $.trim(objName) == $.trim(temp[0])) return temp[1];
    }   
}

function delCookie(name){
    document.cookie = name+"=0;path=/;expires="+(new Date(0)).toGMTString();
}

function addCookieWithNoExpires(name,value){
    var cookieValue = name + "=" + value;
    cookieValue += ";path=/";
//    cookieValue += ";domain=.sf-express.com";
    document.cookie = cookieValue;
}

function addCookie(name,value){
    var cookieValue = name + "=" + value;
    var date = new Date();
    var ms = 7*24*3600*1000;
    date.setTime(date.getTime() + ms);
    cookieValue += ";expires=" + date.toGMTString();
    cookieValue += ";path=/";
//    cookieValue += ";domain=.sf-express.com";
    document.cookie = cookieValue;
}

/*-------------------扩展Date的format方法 ---------------------*/
Date.prototype.format = function (format) {
	var o = {
    	"M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S": this.getMilliseconds()
     };
     if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
     }
     for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
     }
     return format;
};

/**  
 * 转换日期对象为日期字符串(国际化)  
 * @param date 日期对象  
 * @param isFull 是否为完整的日期数据,  
 *               为true时, 格式如"2000-03-05 01:05:04"  
 *               为false时, 格式如 "2000-03-05"  
 * @return 符合要求的日期字符串  
 */  
function getI18nFormatDate(date, isFull) {
        var pattern = "";
        if (isFull == true || isFull == undefined) {
            return date.format('yyyy-MM-dd');
        }(date, pattern);
    }

/**  
 * 转换long值为日期字符串(国际化)  
 * @param l long值  
 * @param isFull 是否为完整的日期数据,  
 *               为true时, 格式如"2000-03-05 01:05:04"  
 *               为false时, 格式如 "2000-03-05" 
 * @return 符合要求的日期字符串  
 */  
function getI18nFormatDateByLong(l, isFull) {
	if(undefined == l || null == l || "null" == l || "" == l){
		return "";
	}
    return getI18nFormatDate(new Date(l), isFull);
}











var VALIDATOR = {
	isValidUsername : function (username){
		return (/^[a-zA-Z][a-zA-Z0-9_]{5,17}$/).test(username);
	},
	isValidMobel : function (mobile) {  
		return (/^[0-9]\d{10}$/g.test(mobile));
	},
	isValidTelephone : function (tel){  
		return (/^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,5})?$/).test(tel);
	},
	isValidEmail : function (email) {
		var reg = /^([a-zA-Z0-9]*[-_.]?[a-zA-Z0-9]+)+@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\.][A-Za-z]{2,3}([\.][A-Za-z]{2})?$/;
		return (reg.test(email) && email.length<=100);
	}
};


var COMMON = {
	/**
	 * 项目路径
	 */
	rootPath : "http://jacky.tunnel.qydev.com",
	imageFolder : "upload",
	/**
	 * 获取路径参
	 */
	getUrlParam : function(key) {
		var svalue = window.location.search.match(new RegExp("[\?\&]" + key
				+ "=([^\&]*)(\&?)", "i"));
		return svalue ? svalue[1] : svalue;
	},
	/**
	 * 格式化手机号码显示
	 * @param value
	 * @returns {String}
	 */
	formatPhone : function formatPhone(value){
		if(!isNull(value)){
			var reg = /(\d{3})\d{4}(\d{4})/;
			return " " + value.replace(reg,"$1****$2");
		}else{
			return formatNull(value);
		}
	},
	/**
	 * 格式化价格显示
	 * @param value
	 * @returns {String}
	 */
	formatPrice : function formatPrice(value){
		if(!isNull(value)){
			return "￥" + value.toFixed(2);
		}else{
			return formatNull(value);
		}
	},
	/**
	 * 去除字符串中所有空格
	 * @param str
	 * @param is_global
	 * @returns
	 */
	trimString : function trim(str,is_global) {
		var result;
		result = str.replace(/(^\s+)|(\s+$)/g,"");
		if(is_global.toLowerCase()=="g")
		result = result.replace(/\s/g,"");
		return result;
	},
	/**
	 * 判断对象是否为空
	 */
	isNotEmpty : function isNotEmpty(object) {
		if(null != object && '' != object && undefined != object){
			return true;
		} else {
			return false;
		}
	},
	/**
	 * 将一个javaScript字面量对象,转化成一个标准的json字符串.
	 * by : 孔卫佳
	 * time : 2013-05-24
	 * @param javascript字面量对象
	 * @return 标准json字符串
	 */
	json2String : function json2String(obj){
		if(typeof(obj) =='undefined' || typeof(obj) =='function'){
			return '';
		}
		if(typeof(obj) =='number' || typeof(obj) =='string' || typeof(obj) =='boolean'){
			return '"'+obj+'"';
		}
		if(typeof(obj) =='object'){
			if(obj instanceof Array){
				var reStr = '[';
				for(i in obj){
					reStr = reStr + json2String(obj[i])+',';
				}
				reStr = reStr + ']';
	            reStr = reStr .replace(/,]/,']');
				return reStr;
			} 
			if(obj instanceof Date){
				return '"'+obj+'",';
			}
			if(obj instanceof Object){
				var reStr = '{';
				for(i in obj){
					reStr = reStr + '"' + i + '":' + json2String(obj[i]) + ',';
				}
	            reStr = reStr + '}';
	            reStr = reStr .replace(/,}/,'}');
				return reStr;
			}
		}
		return '';
	}
}
