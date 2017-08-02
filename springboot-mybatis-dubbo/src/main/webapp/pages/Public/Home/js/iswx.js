//判断是否电脑端
function IsPC() {
    var userAgentInfo = navigator.userAgent;
    var Agents = ["Android", "iPhone",
                "SymbianOS", "Windows Phone",
                "iPad", "iPod"];
    var flag = true;
    for (var v = 0; v < Agents.length; v++) {
        if (userAgentInfo.indexOf(Agents[v]) > 0) {
            flag = false;
            break;
        }
    }
    return flag;
}    
//if(IsPC()) window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa4b944d0cb27a7d4&redirect_uri=http%3A%2F%2Fitpqd.cn%2Fexample%2Fjsapi.php&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect";

//判断是否微信客户端
function isWeiXin() {
    var ua = window.navigator.userAgent.toLowerCase();
    if (ua.match(/MicroMessenger/i) == 'micromessenger') {
        return true;
    } else {
        return false;
    }
}
if(!isWeiXin()){
    //window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa4b944d0cb27a7d4&redirect_uri=http%3A%2F%2Fitpqd.cn%2Fexample%2Fjsapi.php&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect";
} 