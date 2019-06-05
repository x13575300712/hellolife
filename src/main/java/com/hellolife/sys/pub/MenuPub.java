package com.hellolife.sys.pub;

import com.hellolife.sys.dao.Menu;
import com.hellolife.sys.service.MenuService;

import java.util.*;
/**
 *   菜单操作类
 */

public class MenuPub {
    private MenuService menuService;

    public MenuPub(MenuService menuService){
        this.menuService = menuService;
    }
    public MenuPub(){

    }
    /**
     *
     * 返回主菜单
     *
     * */
    public Map<String,List<Menu>> getMainMenu(long parentId, List<Menu> menulist ) {
        List<Menu> allMenu = menuService.getAllMenu();
        Map<String,List<Menu>> menuMap = menuUtil(parentId,menulist,allMenu);
        return menuMap;
    }
    /**
     *
     * 返回主菜单
     *
     * */
    public Map<String,List<Menu>> getMainMenu(long parentId, List<Menu> menulist,long userId) {
        List<Menu> allMenu = menuService.getMenuByUserRole(userId);
        Map<String,List<Menu>> menuMap = menuUtil(parentId,menulist,allMenu);
        return menuMap;
    }
    public Map<String,List<Menu>> menuUtil (long parentId, List<Menu> menulist,List<Menu> allMenu){
        Map<String,List<Menu>> menuMap = new HashMap<String,List<Menu>>();
        if(allMenu!=null&&!allMenu.isEmpty()){
            for(Menu m : allMenu){
                String paraId = m.getParentId()+"";
                if(menuMap.containsKey(paraId)){
                    List<Menu> list = menuMap.get(paraId);
                    list.add(m);
                }else{
                    List<Menu> list = new ArrayList<>();
                    list.add(m);
                    menuMap.put(paraId,list);
                }
                if(m.getParentId()!=null&&m.getParentId()==parentId){
                    menulist.add(m.getOrderNum(),m);
                }
            }
        }
        return menuMap;
    }
    public String getMenu (List<Menu> menulist,Map<String,List<Menu>> menuMap){
        String result = "<li class=\"header\">MAIN NAVIGATION</li>";
        if(!menulist.isEmpty()){
            int z = 0;
            for(Menu m : menulist){
                List<Menu> childrenList = menuMap.get(m.getId()+"");
                boolean cflg = haveChildren(childrenList);
                if(z==0){
                    result +="<li class=\"active menu-open ";

                }else{
                    result +="<li class=\" ";
                }
                if(cflg){
                    result += "treeview";
                }
                result +="\">";
                result += "<a href=\""+m.getUrl()+"\""+" "+m.getAttr()+">";
                result += "<i class=\"fa fa-dashboard\"></i> <span>"+m.getName()+"</span>";
                if(cflg) {
                    result += "<span class=\"pull-right-container\">";
                    result += "<i class=\"fa fa-angle-left pull-right\"></i>";
                    result += "</span>";
                    result += "</a> ";
                    result += getChirdrenMenu(childrenList, menuMap);
                }else{
                    result += "</a> ";
                }
                result += "</li>";
                z++;
            }
        }
        return result;
    }
    private String getChirdrenMenu (List<Menu> list,Map<String,List<Menu>> menuMap){
        String chirdrenMenu = "";
        chirdrenMenu +="<ul class=\"treeview-menu\">";
        for(Menu m : list) {
            List<Menu> childrenList = menuMap.get(m.getId());
            boolean cflg = haveChildren(childrenList);
            if(cflg) {
                chirdrenMenu += "<li class=\"treeview\">";
            }else{
                chirdrenMenu += "<li>";
            }
            chirdrenMenu += "<a href=\""+m.getUrl()+"\""+" "+m.getAttr()+">";
            chirdrenMenu += "<i class=\"fa fa-circle-o\"></i>";
            chirdrenMenu += m.getName();
            if(cflg){
                chirdrenMenu += "<span class=\"pull-right-container\">";
                chirdrenMenu += "<i class=\"fa fa-angle-left pull-right\"></i>";
                chirdrenMenu += "</span>";
                chirdrenMenu += "</a>";
                chirdrenMenu += getChirdrenMenu(childrenList,menuMap);
            }else{
                chirdrenMenu += "</a>";
            }
            chirdrenMenu += "</li>";
        }
        chirdrenMenu +="</ul>";
        return chirdrenMenu;
    }
    private boolean haveChildren (List<Menu> list){
        boolean re = false;
        if(list!=null&&!list.isEmpty()){
            re = true;
        }
        return re;
    }
    /**
     * 获取菜单的List上下级对象 用于转换Json对象；
     *
     * */
    public List<Map<Object,Object>> getListMenu(List<Menu> allMenu){
        Map<String,List<Map<Object,Object>>> menuMap = new HashMap<>();
        List<Map<Object,Object>> allList = new ArrayList<>();
        List<Map<Object,Object>> resultList = new ArrayList<>();
        if(allMenu!=null&&!allMenu.isEmpty()){
            for(Menu m : allMenu){
                Map<Object,Object> temp = new HashMap<>();
                String paraId = m.getParentId()+"";
                //menu.put("level",0);
                temp.put("id",m.getId()+"");
                temp.put("text",m.getName());
                temp.put("parentid",paraId);
                allList.add(temp);
                if(menuMap.containsKey(paraId)){
                    List<Map<Object,Object>> list = menuMap.get(paraId);
                    list.add(temp);
                }else{
                    List<Map<Object,Object>> list = new ArrayList<>();
                    list.add(temp);
                    menuMap.put(paraId,list);
                }
            }
            for( Map<Object,Object> menu : allList){
                String id = menu.get("id").toString();
                String parentid = menu.get("parentid").toString();
                if(menuMap.containsKey(id+"")){
                    menu.put("nodes",menuMap.get(id+""));
                }
                if("null".equals(parentid)){
                    resultList.add(menu);
                }
            }
        }
        return resultList;
    }

}
