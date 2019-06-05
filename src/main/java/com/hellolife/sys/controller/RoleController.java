package com.hellolife.sys.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hellolife.sys.dao.Role;
import com.hellolife.sys.dao.User;
import com.hellolife.sys.pub.SnowflakeIdWorker;
import com.hellolife.sys.pub.pubfunction;
import com.hellolife.sys.service.RoleService;
import com.hellolife.sys.service.UserService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class RoleController {
    @Autowired
    RoleService roleService ;
    /**
     *
     *  角色列表
     */
    @RequestMapping(value = "/role")
    public String userList(Model model) {
        return "sysRole/roleList";
    }
    /**
     *
     *  角色表单查询
     */
    @ResponseBody
    @RequestMapping(value = "/roleTable")
    public String roleTable(HttpServletRequest request,Model model,String type) {
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        /* bootstrap table 分页参数*/
        int offset = Integer.parseInt(request.getParameter("offset"));
        int pageSize = Integer.parseInt(request.getParameter("limit"));
        int pageNum = (offset/pageSize)+1;
        /*jqguid 分页参数
        int pageNum = Integer.parseInt(request.getParameter("page"));
        int pageSize = Integer.parseInt(request.getParameter("rows"));*/

        //设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】

        PageHelper.startPage(pageNum, pageSize);
        List<Role> roleList;
            switch(type){
                default:
                    roleList = roleService.selectAllRole();
                    break;
                case "menu":
                    String id = request.getParameter("menuId");
                    roleList = roleService.selectMenuRole(Long.parseLong(id));
                    break;
                case "all":
                    roleList = roleService.selectAllRole();
                    break;
                case "user":
                    id = request.getParameter("userId");
                    roleList = roleService.selectUserRole(Long.parseLong(id));
                    break;
            }
        PageInfo<Role> appsPageInfo = new PageInfo<>(roleList);
        JSONObject jsonObject = null;
        if(roleList!=null){
            for(Role role : roleList){
                jsonObject = new JSONObject();
                jsonObject.put("description",role.getDescription());
                jsonObject.put("role",role.getRole());
                jsonObject.put("id",role.getId()+"");
                jsonObject.put("useFlg",role.getUseflg());
                array.add(jsonObject);
            }
        }
        /* bootstrap table 返回参数*/
        result.put("rows",array);
        result.put("page",appsPageInfo.getPageNum());
        result.put("total",appsPageInfo.getTotal());
        /*jqguid 返回参数
        result.put("rows",array);
        result.put("page",appsPageInfo.getPageNum());//当前页号
        result.put("records",appsPageInfo.getTotal());//总条数
        result.put("total",appsPageInfo.getPages());//页数*/
        return result.toJSONString();
    }
    /**
     *
     *  创建角色
     */
    @RequestMapping(value = "/createRole")
    public String login(HttpServletRequest request,Model model) {
        String id = request.getParameter("id");
        if(!pubfunction.isEmptyStr(id)) {
            Role role = roleService.getRoleByid(Long.parseLong(id));
            model.addAttribute("role", role);
            model.addAttribute("saveFlg", "0");
        }else{
            model.addAttribute("role", new Role());
            model.addAttribute("saveFlg", "1");
        }
        return "sysRole/createRole";
    }

    @ResponseBody
    @RequestMapping(value = "/form/createRoleForm")
    public String createUser(HttpServletRequest request, Model model) throws Exception{
        JSONObject returnJson = new JSONObject();
        Role newRole = new Role();
        String saveFlg = request.getParameter("saveFlg");
        String role = request.getParameter("role");
        String description = request.getParameter("description");
        String useflg = request.getParameter("useflg");
        long id = 0L;
        if("1".equals(saveFlg)) {
            SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
            id = idWorker.nextId();
        }else{
            id = Long.parseLong(request.getParameter("id"));
        }
        newRole.setId(id);
        newRole.setRole(role);
        newRole.setDescription(description);
        newRole.setUseflg(useflg);
        if("1".equals(saveFlg)) {
            roleService.genRole(newRole);
        }else{
            roleService.updateRole(newRole);
        }
        return returnJson.toJSONString();
    }
    @ResponseBody
    @RequestMapping(value = "/deleteRole")
    public String deleteUser(HttpServletRequest request, Model model) throws Exception{
        String roleIds = request.getParameter("data");
        JSONArray JsonArray =JSONArray.parseArray(roleIds);
        if(JsonArray!=null){
            for(Object obj : JsonArray){
                JSONObject JsonObject = JSONObject.parseObject(obj.toString());
                Long id = (Long)JsonObject.get("id");
                roleService.deleteRole(id);
            }
        }
        JSONObject result = new JSONObject();
        result.put("errmsg","");
        return result.toJSONString();
    }


    /**
     *
     *  关联权限列表
     */
    @RequestMapping(value = "/roleAndPermission")
    public String roleAndPermission(HttpServletRequest request,Model model) {
        String roleId = request.getParameter("id");
        model.addAttribute("roleId",roleId);
        return "sysRole/roleAndpermissionList";
    }

    /**
     *
     *  关联菜单
     */
    @RequestMapping(value = "/roleAndMenu")
    public String roleAndMenu(String id ,Model model) {
        model.addAttribute("menuId",id);
        return "sysRole/roleAndMenuList";
    }
    /**
     *
     *  关联用户
     */
    @RequestMapping(value = "/roleAndUser")
    public String roleAndUser(String id ,Model model) {
        model.addAttribute("userId",id);
        return "sysRole/roleAndUserList";
    }
    /**
     *
     *  角色选择   菜单处
     */
    @RequestMapping(value = "/roleForSelect")
    public String roleForSelect(String menuId ,Model model) {
        model.addAttribute("menuId",menuId);
        return "sysRole/roleForSelect";
    }
    /**
     *
     *  角色选择  用户处
     */
    @RequestMapping(value = "/roleForSelectUser")
    public String roleForSelectUser(String userId ,Model model) {
        model.addAttribute("userId",userId);
        return "sysRole/roleForSelectUser";
    }
    /**
     *
     *  角色选择确认
     */
    @ResponseBody
    @RequestMapping(value = "/sureRole")
    public String sureRole(HttpServletRequest request, Model model,String menuId) throws Exception{
        String roleIds = request.getParameter("data");
        JSONArray JsonArray =JSONArray.parseArray(roleIds);
        Long menuIdL = Long.parseLong(menuId);
        if(JsonArray!=null){
            for(Object obj : JsonArray){
                JSONObject JsonObject = JSONObject.parseObject(obj.toString());
                Long roleId =  Long.parseLong((String)JsonObject.get("id"));
                roleService.genMenuAndRole(roleId,menuIdL);
            }
        }
        JSONObject result = new JSONObject();
        result.put("errmsg","");
        return result.toJSONString();
    }
    /**
     *
     *  角色选择确认 用户处
     */
    @ResponseBody
    @RequestMapping(value = "/sureRoleUser")
    public String sureRoleUser(HttpServletRequest request, Model model,String userId) throws Exception{
        String roleIds = request.getParameter("data");
        JSONArray JsonArray =JSONArray.parseArray(roleIds);
        Long userIdL = Long.parseLong(userId);
        if(JsonArray!=null){
            for(Object obj : JsonArray){
                JSONObject JsonObject = JSONObject.parseObject(obj.toString());
                Long roleId =  Long.parseLong((String)JsonObject.get("id"));
                roleService.genUserAndRole(roleId,userIdL);
            }
        }
        JSONObject result = new JSONObject();
        result.put("errmsg","");
        return result.toJSONString();
    }
    /**
     *
     *  角色选择删除 菜单处
     */
    @ResponseBody
    @RequestMapping(value = "/deleteRoleForMenu")
    public String deleteRoleForMenu(HttpServletRequest request, Model model,String menuId) throws Exception{
        String roleIds = request.getParameter("data");
        JSONArray JsonArray =JSONArray.parseArray(roleIds);
        Long menuIdL = Long.parseLong(menuId);
        if(JsonArray!=null){
            for(Object obj : JsonArray){
                JSONObject JsonObject = JSONObject.parseObject(obj.toString());
                Long id = Long.parseLong((String)JsonObject.get("id"));
                roleService.deleteMenuAndRole(id,menuIdL);
            }
        }
        JSONObject result = new JSONObject();
        result.put("errmsg","");
        return result.toJSONString();
    }
    /**
     *
     *  角色选择删除 用户处
     */
    @ResponseBody
    @RequestMapping(value = "/deleteRoleForUser")
    public String deleteRoleForUser(HttpServletRequest request, Model model,String userId) throws Exception{
        String roleIds = request.getParameter("data");
        JSONArray JsonArray =JSONArray.parseArray(roleIds);
        Long userIdL = Long.parseLong(userId);
        if(JsonArray!=null){
            for(Object obj : JsonArray){
                JSONObject JsonObject = JSONObject.parseObject(obj.toString());
                Long id = Long.parseLong((String)JsonObject.get("id"));
                roleService.deleteUserAndRole(id,userIdL);
            }
        }
        JSONObject result = new JSONObject();
        result.put("errmsg","");
        return result.toJSONString();
    }
}
