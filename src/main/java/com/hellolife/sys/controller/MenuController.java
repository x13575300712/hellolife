package com.hellolife.sys.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hellolife.sys.dao.Menu;
import com.hellolife.sys.pub.MenuPub;
import com.hellolife.sys.pub.SnowflakeIdWorker;
import com.hellolife.sys.pub.pubfunction;
import com.hellolife.sys.service.MenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class MenuController {
    @Autowired
    private MenuService menuService;
    /**
     *
     *  菜单列表
     */
    @RequestMapping(value = "/menu")
    public String menuList(Model model) {
        List<Menu> allMenu = menuService.getAllMenu();
        MenuPub menuPub = new MenuPub();
        List<Map<Object,Object>> resultList = menuPub.getListMenu(allMenu);
        model.addAttribute("treeData",JSONArray.toJSONString(resultList));
        return "sysmenu/menuList";
    }
    /**
     *
     *  菜单列表
     */
    @RequestMapping(value = "/menuDetailTop")
    public String menuDetailTop(String id,Model model) {
        model.addAttribute("id",id);
        return "sysmenu/menuDetailTop";
    }
    /**
     *
     *  菜单列表
     */
    @RequestMapping(value = "/menuTable")
    public String userList(HttpServletRequest request,Model model) {
        long id = Long.parseLong(request.getParameter("id"));
        Menu  pm =  menuService.getMenuById(id);
        model.addAttribute("id",request.getParameter("id"));
        model.addAttribute("name",pm.getName());
        return "sysmenu/menuTable";
    }
    /**
     *
     *  菜单table
     */
    @ResponseBody
    @RequestMapping(value = "/table/menuTable")
    public String userListTable(HttpServletRequest request,Model model) {
        long id = Long.parseLong(request.getParameter("parantId"));
        String pName = request.getParameter("parantName");
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        int pageNum = Integer.parseInt(request.getParameter("offset"));
        int pageSize = Integer.parseInt(request.getParameter("limit"));
        PageHelper.startPage((pageNum/pageSize)+1, pageSize);
        List<Menu> menuList = menuService.getMenuByParent(id);
        PageInfo<Menu> appsPageInfo = new PageInfo<>(menuList);
        JSONObject jsonObject = null;
        if(menuList!=null){
            for(Menu menu : menuList){
                jsonObject = new JSONObject();
                jsonObject.put("id",menu.getId()+"");
                jsonObject.put("name",menu.getName());
                jsonObject.put("url",menu.getUrl());
                jsonObject.put("icon",menu.getIcno());
                jsonObject.put("parentId",menu.getParentId());
                jsonObject.put("parentName",pName);
                jsonObject.put("orderNum",menu.getOrderNum());
                jsonObject.put("attr",menu.getAttr());
                array.add(jsonObject);
            }
        }
        result.put("rows",array);
        result.put("page",appsPageInfo.getPageNum());
        result.put("total",appsPageInfo.getTotal());
        return result.toJSONString();
    }
    /**
     *
     *  创建菜单
     */
    @RequestMapping(value = "/createmenu")
    public String createMenu(HttpServletRequest request,Model model) {
        String parentId = request.getParameter("parentId");
        String parentName = request.getParameter("parentName");
        String id = request.getParameter("id");
        Menu m = null;
        String saveFlg = "1";
        if(!pubfunction.isEmptyStr(id)){
            long idL = Long.parseLong(id);
            m =  menuService.getMenuById(idL);
            saveFlg = "0";
        }else{
            m = new Menu();
        }
        model.addAttribute("parentId",parentId);
        model.addAttribute("parentName",parentName);
        model.addAttribute("menu",m);
        model.addAttribute("saveFlg",saveFlg);
        return "sysmenu/createmenu";
    }
    /**
     *
     *  创建菜单
     */
    @RequestMapping(value = "/editMenuRow")
    public String editMenuRow(Long id,Model model) {
        String saveFlg = "1";
        Menu m =  menuService.getMenuById(id);
        Long parentId = m.getParentId();
        if(parentId!=null) {
            Menu pm = menuService.getMenuById(parentId);
            model.addAttribute("parentId", parentId + "");
            model.addAttribute("parentName", pm.getName());
        }
        model.addAttribute("menu",m);
        model.addAttribute("saveFlg",saveFlg);
        return "sysmenu/createmenu";
    }
    /**
     *
     *  创建菜单
     */
    @ResponseBody
    @RequestMapping(value = "/form/createmenu")
    public String saveCreateMenu(HttpServletRequest request,Model model) {
        String saveFlg = request.getParameter("saveFlg");
        String name = request.getParameter("name");
        String url = request.getParameter("url");
        String icno = request.getParameter("icno");
        String attr = request.getParameter("attr");
        int orderNum = Integer.parseInt(request.getParameter("orderNum"));
        String id = request.getParameter("id");
        long parentId = Long.parseLong(request.getParameter("parentId"));
        long idL = 0;
        Menu m = new Menu();
        m.setAttr(attr);
        m.setIcno(icno);
        m.setName(name);
        m.setOrderNum(orderNum);
        m.setParentId(parentId);
        m.setUrl(url);
        if("1".equals(saveFlg)){//1添加
            SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
            idL = idWorker.nextId();
            m.setId(idL);
            menuService.genMenu(m);
        }else{
            idL = Long.parseLong(id);
            m.setId(idL);
            menuService.updateMenu(m);
        }
        JSONObject returnJson = new JSONObject();
        return returnJson.toJSONString();
    }
    @ResponseBody
    @RequestMapping(value = "/deleteMenu")
    public String deleteMenu(HttpServletRequest request, Model model) throws Exception{
        String menus = request.getParameter("data");
        JSONArray JsonArray =JSONArray.parseArray(menus);
        if(JsonArray!=null){
            for(Object obj : JsonArray){
                JSONObject JsonObject = JSONObject.parseObject(obj.toString());
                long menuId = Long.parseLong((String)JsonObject.get("id"));
                menuService.deleteMenu(menuId);
            }
        }
        JSONObject result = new JSONObject();
        result.put("errmsg","");
        return result.toJSONString();
    }

}
