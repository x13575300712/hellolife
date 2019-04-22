var mainActiveId='home';
var divId='#content';
var frameFlg = false;
var firstUrl = null;//第一个页面
var secondUrl = null;//第二个页面
var xmlhttp = new getXMLObject();
var flag = "1";
//url : 转向地址，activId:活动菜单id，parent ： 是否属于layer弹出框调用 true代表是
function locationUrl(url,activeId,parent){
	if(parent){
        frameFlg = true;
	}else{
        frameFlg = false;
	}
	if(mainActiveId != null && mainActiveId != "" && activeId != null && activeId != ""){
		$("#"+mainActiveId).removeAttr("class");
		$("#"+activeId).attr("class", "active");
		mainActiveId = activeId;
	}
	goUrl(url,null,"#content");
}


function goUrl(url,params,idSelect) {
    divId = idSelect;
	fixUrl(url,params);
	if(xmlhttp) {
		//var params = "";
		xmlhttp.open("POST",url,true);
		xmlhttp.onreadystatechange = handleServerResponse;
		xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded;charset=UTF-8');
		xmlhttp.send(params);
	}
}
//XML OBJECT
function getXMLObject() {
	var xmlHttp = false;
	try {
		xmlHttp = new ActiveXObject("Msxml2.XMLHTTP") // For Old Microsoft
														// Browsers
	} catch (e) {
		try {
			xmlHttp = new ActiveXObject("Microsoft.XMLHTTP") // For Microsoft
																// IE 6.0+
		} catch (e2) {
			xmlHttp = false // No Browser accepts the XMLHTTP Object then false
		}
	}
	if (!xmlHttp && typeof XMLHttpRequest != 'undefined') {
		xmlHttp = new XMLHttpRequest(); // For Mozilla, Opera Browsers
	}
	return xmlHttp; // Mandatory Statement returning the ajax object created
}
function handleServerResponse() {
	if (xmlhttp.readyState == 4) {
		//document.getElementById("mainSection").innerHTML =xmlhttp.responseText;
		var text = xmlhttp.responseText;
		if(text.indexOf("<title>Favorites error Page</title>") >= 0){
			window.location.href="/error.html";
		}else{
			if(frameFlg){
                parent.$(divId).html(xmlhttp.responseText);
			}else{
                $(divId).html(xmlhttp.responseText);
			}
		}
	}
}
function fixUrl(url, params){
	if(params != null){
		url = url + "?" + params;
	}
	if(firstUrl == null){
		firstUrl = url;
	}else if(secondUrl == null){
		secondUrl = url;
	}else{
		if(flag == 1){
			firstUrl = url;
			flag = 2;
		}else{
			secondUrl = url;
			flag = 1;
		}
	}
}

function getHeight(xiShu) {
    var height = $('.content-wrapper, .right-side').css("minHeight");
    return height.substr(0,height.length-2)*xiShu;
}

