package com.hellolife.sys.pub;

import com.hellolife.sys.dao.Menu;
import com.hellolife.sys.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MenuPub {
    @Autowired
    private MenuService menuService;
    /**
     *
     * 返回主菜单
     *
     * */
    public String getMainMenu(long parentId) {
        List<Menu> menulist = menuService.getMenuByParent(parentId);
        String returnMenu = "";
        if(menulist!=null) {
            for(int i = 0 ;i<menulist.size();i++) {
                Menu myMenu = menulist.get(i);
                String children = "";
                if(!pubfunction.isEmptyStr(myMenu.getUrl())) {
                    returnMenu += " <li><a href=\""+myMenu.getUrl()+"\" data-toggle=\"tab\"  onclick=\"loadUrl(this,'.right_col')\">";
                }else {
                    returnMenu += " <li><a>";
                }
                if(parentId==0) {
                    returnMenu +="<i class=\"fa "+myMenu.getIcno()+"\" ></i>";
                }
                returnMenu += myMenu.getName();
                children=this.getMainMenu(myMenu.getId());
                if(!pubfunction.isEmptyStr(children)) {
                    returnMenu +=		"<span class=\"fa fa-chevron-down\"></span>";
                    children = "<ul class=\"nav child_menu\">"+children+"</ul>";
                    returnMenu += "</a>";
                    returnMenu += children+"</li>";
                }else {
                    returnMenu +="</a></li>";
                }
            }
        }
        return returnMenu;
    }
}
