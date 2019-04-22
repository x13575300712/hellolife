function deleteRow(tableSelect,url,callback){
    var rows = $(tableSelect).bootstrapTable('getSelections');
    if(rows.length==0){
        alertWorn("请选择一条数据！");
    }
    var t= JSON.stringify(rows);
    $.ajax({
        type: 'POST',
        url:url,
        data:{"data":t},
        dataType:"json",
        cache: false,
        success: callback,
        error:function(XMLHttpRequest, textStatus, errorThrown){
            alert(XMLHttpRequest);
            alert(textStatus);
            alert(errorThrown);
        }
    });
}
function editRow(tableSelect,url,param,extparam,tilte,width,height){
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
        layer.open({
            type: 2,
            title: tilte,
            maxmin: true,
            shadeClose: true, //点击遮罩关闭层
            area : [width+'px' , height+'px'],
            content: url+"?params="+paramUrl+extparam
        });
        // $("#modal-pub").modal({
        //     remote: url+"?params="+paramUrl+extparam,
        //     show:true
        // });
    }else{
        alertWorn("请选择一条数据！");
    }
}
function addRow(url,extparam,tilte,width,height){
    layer.open({
        type: 2,
        title: tilte,
        maxmin: true,
        shadeClose: true, //点击遮罩关闭层
        area : [width+'px' , height+'px'],
        content: url+"?test20190408=1"+extparam
    });
}
function felshTable(tableObj,pagenum){
    tableObj.bootstrapTable('refresh',{pageNumber:pagenum});
}
function excuteRowButton(tableSelect,rowId,url,callback){
    var json =  $(tableSelect).bootstrapTable('getRowByUniqueId',rowId);
    $.ajax({
        type: 'POST',
        url:url,
        data:{"data":JSON.stringify(json)},
        dataType:"json",
        cache: false,
        success: callback,
        error:function(XMLHttpRequest, textStatus, errorThrown){
            alert(XMLHttpRequest);
            alert(textStatus);
            alert(errorThrown);
        }
    });
}
function importFile(url,extparam,tilte,width,height){
    layer.open({
        type: 2,
        title: tilte,
        maxmin: true,
        shadeClose: true, //点击遮罩关闭层
        area : [width+'px' , height+'px'],
        content: "/importFile"+"?url="+url+extparam
    });
}
