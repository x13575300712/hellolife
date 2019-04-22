function findTabTitleParent(pageId) {
    var $ele = null;
    parent.$(".page-tabs-content").find("a.menu_tab").each(function () {
        var $a = $(this);
        if ($a.attr(pageIdField) == pageId) {
            $ele = $a;
            return false;//退出循环
        }
    });
    return $ele;
}
function findTabPanelParent(pageId) {
    var $ele = null;
    parent.$("#tab-content").find("div.tab-pane").each(function () {
        var $div = $(this);
        if ($div.attr(pageIdField) == pageId) {
            $ele = $div;
            return false;//退出循环
        }
    });
    return $ele;
}

function findIframeByIdParent(pageId) {
    return findTabPanelParent(pageId).children("iframe");
}

function getActivePageIdParent() {
    var $a = parent.$('.page-tabs-content').find('.active');
    return getPageId($a);
}

function canRemoveTabParent(pageId) {
    return findTabTitleParent(pageId).find('.fa-remove').size() > 0;
}

function closeTabByPageIdParent(pageId) {
    var $title = findTabTitleParent(pageId);//有tab的标题
    var $tabPanel = findTabPanelParent(pageId);//装有iframe

    if ($title.hasClass("active")) {
        //要关闭的tab处于活动状态
        //要把active class传递给其它tab

        //优先传递给后面的tab,没有的话就传递给前一个
        var $nextTitle = $title.next();
        var activePageId;
        if ($nextTitle.size() > 0) {
            activePageId = getPageId($nextTitle);
        } else {
            activePageId = getPageId($title.prev());
        }

        setTimeout(function () {
            //某种bug，需要延迟执行
            activeTabByPageIdParent(activePageId);
        }, 100);

    } else {
        //要关闭的tab不处于活动状态
        //直接移除就可以了,不用传active class

    }

    $title.remove();
    $tabPanel.remove();
    // scrollToTab($('.menu_tab.active')[0]);

}
//激活Tab,通过id
function activeTabByPageIdParent(pageId) {
    parent.$(".menu_tab").removeClass("active");
    parent.$("#tab-content").find(".active").removeClass("active");
    //激活TAB
    var $title = findTabTitle(pageId).addClass('active');
    findTabPanelParent(pageId).addClass("active");
    // scrollToTab($('.menu_tab.active'));
    scrollToTab($title[0]);
}

function closeTabOnlyParent(pageId) {
    var $title = findTabTitleParent(pageId);//有tab的标题
    var $tabPanel = findTabPanelParent(pageId);//装有iframe
    $title.remove();
    $tabPanel.remove();
}


function refreshTabByIdParent(pageId) {
    var $iframe = findIframeByIdParent(pageId);
    var url = $iframe.attr('src');

    if (url.indexOf(top.document.domain) < 0) {
        $iframe.attr("src", url);// 跨域状况下，重新设置url
    } else {
        $iframe[0].contentWindow.location.reload(true);//带参数刷新
    }

    App.blockUI({
        target: '#tab-content',
        boxed: true,
        message: '加载中......'//,
        // animate: true
    });
}
function getTabUrlByIdParent(pageId) {
    var $iframe = findIframeByIdParent(pageId);
    return $iframe[0].contentWindow.location.href;
}

function getTabUrlParent(element) {
    var pageId = getPageId(element);
    getTabUrlByIdParent(pageId);
}