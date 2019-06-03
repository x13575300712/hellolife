package com.hellolife.sys.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hellolife.sys.dao.Permission;
import com.hellolife.sys.dao.Role;
import com.hellolife.sys.pub.SnowflakeIdWorker;
import com.hellolife.sys.pub.pubfunction;
import com.hellolife.sys.service.PermissionService;
import com.hellolife.sys.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class PermissionController {
    @Autowired
    RoleService roleService ;
    @Autowired
    PermissionService permissionService ;
    /**
     *
     *  用户列表
     */
    @RequestMapping(value = "/permission")
    public String userList(Model model) {
        return "sysPermission/permissionList";
    }
    /**
     *
     *  权限表单查询
     */
    @ResponseBody
    @RequestMapping(value = "/permissionTable")
    public String roleTable(HttpServletRequest request,Model model) {
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
        List<Permission> permissionList;
        String selectTyp = request.getParameter("selectTyp");
        switch(selectTyp){
            case "1":
                String roleId = request.getParameter("roleId");
                permissionList =  permissionService.selectPermissionByRole(roleId);
                break;
            default:
                permissionList =  permissionService.selectAllPermission();
        }
        PageInfo<Permission> appsPageInfo = new PageInfo<>(permissionList);
        JSONObject jsonObject = null;
        if(permissionList!=null){
            for(Permission permission : permissionList){
                jsonObject = new JSONObject();
                jsonObject.put("id",permission.getId());
                jsonObject.put("useflg",permission.getUseflg());
                jsonObject.put("name",permission.getName());
                jsonObject.put("parentId",permission.getParentId());
                jsonObject.put("parentIds",permission.getParentIds());
                jsonObject.put("permission",permission.getPermission());
                jsonObject.put("resourceType",permission.getResourceType());
                jsonObject.put("url",permission.getUrl());
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
    @RequestMapping(value = "/createPermission")
    public String login(HttpServletRequest request,Model model) {
        String id = request.getParameter("id");
        if(!pubfunction.isEmptyStr(id)) {
            Permission permission = permissionService.getPermissionByid(Long.parseLong(id));
            model.addAttribute("permission", permission);
            model.addAttribute("saveFlg", "0");
        }else{
            model.addAttribute("permission", new Permission());
            model.addAttribute("saveFlg", "1");
        }
        return "sysPermission/createPermission";
    }

    @ResponseBody
    @RequestMapping(value = "/form/createPermissionForm")
    public String createUser(HttpServletRequest request, Model model ,Permission newPermission) throws Exception{
        JSONObject returnJson = new JSONObject();
        String saveFlg = request.getParameter("saveFlg");
        long id = 0L;
        if("1".equals(saveFlg)) {
            SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
            id = idWorker.nextId();
        }else{
            id = Long.parseLong(request.getParameter("id"));
        }
        newPermission.setId(id);
        if("1".equals(saveFlg)) {
            permissionService.genPermission(newPermission);
        }else{
            permissionService.updatePermission(newPermission);
        }
        return returnJson.toJSONString();
    }
    @ResponseBody
    @RequestMapping(value = "/deletePermission")
    public String deleteUser(HttpServletRequest request, Model model) throws Exception{
        String roleIds = request.getParameter("data");
        JSONArray JsonArray =JSONArray.parseArray(roleIds);
        if(JsonArray!=null){
            for(Object obj : JsonArray){
                JSONObject JsonObject = JSONObject.parseObject(obj.toString());
                Long id = (Long)JsonObject.get("id");
                permissionService.deletePermission(id);
            }
        }
        JSONObject result = new JSONObject();
        result.put("errmsg","");
        return result.toJSONString();
    }
}
