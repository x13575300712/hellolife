/*  依赖  messenger.js layer.js*/
function alertWorn(message){
    alertCon(message,"info",2,true,true);
}
function alertCon(message,type,time,closeable,hiddenPreview){
    $.globalMessenger().post({
        message: message,//提示信息
        type: type,//消息类型。error、info、success
        hideAfter: time,//多长时间消失
        showCloseButton:closeable,//是否显示关闭按钮
        hideOnNavigate: hiddenPreview //是否隐藏导航
    });
}
/*关闭当前layer弹出层*/
function layerClose(){
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    parent.layer.close(index);
}
function layerCloseP(){
    var index = layer.getFrameIndex(window.name); //获取窗口索引
    layer.close(index);
}