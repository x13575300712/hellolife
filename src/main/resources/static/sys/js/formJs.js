/*依赖ajax  bootstrapValidator   jquery.serializeJSON-master*/
function ajaxFormSumbit(json){

}
function ajaxForm(form,callback,confirmMsg){
    validateCallback(form,callback,confirmMsg);
}
function validateCallback(form, callback, confirmMsg) {
    var $form = $(form);
    /*var $callback = eval('('+callback+')');*/
    var bv = $form.data('bootstrapValidator');
    if(bv){
        bv.validate();//手动验证
        if (!bv.isValid()) {
            return false;
        }
    }
    var _submitFn = function(){
        var data = $form.serializeArray();
        var formObject = {};
        formObject.name = "formObject";
        formObject.value = JSON.stringify($form.serializeJSON());
        data.push(formObject);
        $.ajax({
            type: form.method || 'POST',
            url:$form.attr("action"),
            data:data,
            dataType:"json",
            cache: false,
            success: callback || ajaxFormSumbit,
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert(XMLHttpRequest+"__"+textStatus+"__"+errorThrown);
            }
        });
    }
    if (confirmMsg) {
        //alertMsg.confirm(confirmMsg, {okCall: _submitFn});
    } else {
        _submitFn();
    }
    return false;
}

function importExp(inputSelector,url,jsonData,callback) {
    var formData = new FormData();
    var name = $(inputSelector).val();
    formData.append("file",$(inputSelector)[0].files[0]);
    formData.append("name",name);
    formData = fixForm(formData,jsonData);
    $.ajax({
        url : url,
        type : 'POST',
        async : false,
        data : formData,
        // 告诉jQuery不要去处理发送的数据
        processData : false,
        // 告诉jQuery不要去设置Content-Type请求头
        contentType : false,
        beforeSend:function(){
            console.log("正在进行，请稍候");
        },
        success : callback,
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(XMLHttpRequest+"__"+textStatus+"__"+errorThrown);
        }
    });
}

function fixForm(formData,jsonData) {
    var json = JSON.parse(jsonData);
    if(json){
        for(var i in json){
            formData.append(i,json[i]);
        }
    }
    return formData;
}