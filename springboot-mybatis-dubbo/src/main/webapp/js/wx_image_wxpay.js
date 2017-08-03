/**
 * 支付打赏
 */
var image_pay = (function() {
//     if(IsPC()) window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa4b944d0cb27a7d4&redirect_uri=http%3A%2F%2Fitpqd.cn%2Fexample%2Fjsapi.php&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect";
     setTimeout(function() {
         //当图片高度小于360px时，打赏图片按钮的top为30%
//         var imgW = $('.weui-article img').height();
//         var newTop = (imgW/2 + 66) + 'px';
//         $('a.reward').css({top:newTop}); 
         //弹出窗开启
         $('a.reward').click(function() {
             $('.reward-dialog').show();
         });
         //弹出窗关闭
         $('.dialog-close').click(function() {
             $('.reward-dialog').hide();
         });
//         var dia_top = '-' + (imgW/2 + 166) + 'px';
//         $('.reward-dialog').css({top:dia_top});
     },300);
     wx.config({
         debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
         appId: "wxa4b944d0cb27a7d4",
         timestamp: "1414587457",
         nonceStr: "Wm3WZYTPz0wzccnW",
         signature: "f7cb20e4db00b694fc5037d68e3d1d2c28541528",
         jsApiList: [
         'hideAllNonBaseMenuItem',
         'hideOptionMenu',
         ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
     });   
     wx.ready(function(){
         //隐藏所有非基础按钮接口
         wx.hideOptionMenu();
     });   
})();

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

//写cookies
function setCookie(name,value) {
    var Days = 30;
    var exp = new Date();
    exp.setTime(exp.getTime() + Days*24*60*60*1000);
    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
}   
