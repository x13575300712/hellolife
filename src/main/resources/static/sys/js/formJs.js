
function ajaxFormSumbit(json){
    alert(1);
}
function ajaxForm(form,callback,confirmMsg){
    validateCallback(form,callback,confirmMsg);
    //form.onsubmit();
}

function validateCallback(form, callback, confirmMsg) {
    var $form = $(form);
    /*if (!$form.valid()) {
        return false;
    }*/
    var $callback = eval('('+callback+')');
    var _submitFn = function(){
        $.ajax({
            type: form.method || 'POST',
            url:$form.attr("action"),
            data:$form.serializeArray(),
            dataType:"json",
            cache: false,
            success: $callback || ajaxFormSumbit
        });
    }
    if (confirmMsg) {
        //alertMsg.confirm(confirmMsg, {okCall: _submitFn});
    } else {
        _submitFn();
    }
    return false;
}