function deleteRow(tableSelect,url,callback){
    var rows = $(tableSelect).bootstrapTable('getSelections');
    var t= JSON.stringify(rows);
    var $callback = eval('('+callback+')');
    $.ajax({
        type: 'POST',
        url:url,
        data:{"data":t},
        dataType:"json",
        cache: false,
        success: $callback,
        error:function(XMLHttpRequest, textStatus, errorThrown){
            alert(XMLHttpRequest);
            alert(textStatus);
            alert(errorThrown);
        }
    });
}
function editRow(tableSelect,url,param){
    var rows = $(tableSelect).bootstrapTable('getSelections');
    if(rows.length==1){
        var t= rows[0];
        var paramUrl = "1";
        if(param){
            var params = param.split(",");
            for(var p in params){
                paramUrl +="&";
                paramUrl += params[p];
                var pn = params[p];
                paramUrl += "=";
                paramUrl += t[pn];
            }
        }
        $("#modal-pub").modal({
            remote: url+"?params="+paramUrl
        });
    }else{
        alertWorn("请选择一条数据！");
    }
}
function felshTable(tableObj,pagenum){
    tableObj.bootstrapTable('refresh',{pageNumber:pagenum});
}

