package com.hellolife.sys.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hellolife.sys.dao.User;
import com.hellolife.sys.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.shiro.crypto.hash.Md5Hash;
import com.hellolife.sys.pub.SnowflakeIdWorker;
import com.hellolife.sys.pub.pubfunction;


import javax.servlet.http.HttpServletRequest;

import java.util.Enumeration;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserService userService ;
    /**
     *
     *  创建用户
     */
    @RequestMapping(value = "/createuser")
    public String login(HttpServletRequest request,Model model) {
        String userCode = request.getParameter("userCode");
        if(!pubfunction.isEmptyStr(userCode)) {
            User selectUser = userService.loginUser(userCode);
            model.addAttribute("user", selectUser);
            model.addAttribute("saveFlg", "0");
        }else{
            model.addAttribute("user", new User());
            model.addAttribute("saveFlg", "1");
        }
        return "sysuser/createuser";
    }
    @ResponseBody
    @RequestMapping(value = "/createuserForm")
    public String createUser(HttpServletRequest request, Model model) throws Exception{
        JSONObject returnJson = new JSONObject();
        User newUser = new User();

        String saveFlg = request.getParameter("saveFlg");
        String userCode = request.getParameter("userCode");
        String password = request.getParameter("password");
        String passwordSure = request.getParameter("passwordSure");
        String userName = request.getParameter("userName");
        String eMail = request.getParameter("eMail");
        if(!pubfunction.isEmptyStr(passwordSure)){
            if(!passwordSure.equals(password)){
                String errmsg = "两次输入的密码不一致请确认！";
                returnJson.put("errmsg",errmsg);
                return returnJson.toJSONString();
            }
        }
        String salt = "";
        String result = "";
        long id = 0L;
        if("1".equals(saveFlg)) {
            salt = new SecureRandomNumberGenerator().nextBytes().toHex();
            //将原始密码加盐（上面生成的盐），并且用md5算法加密两次，将最后结果存入数据库中
            result = new Md5Hash(password, userName + salt, 2).toString();
            SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
            id = idWorker.nextId();
        }else{
            salt = request.getParameter("salt");
            result = new Md5Hash(password, userName + salt, 2).toString();
            id = Long.parseLong(request.getParameter("id"));
        }

        newUser.setId(id);
        newUser.setSalt(salt);
        newUser.setUserName(userName);
        newUser.setUserCode(userCode);
        newUser.setPassWord(result);
        newUser.setState("1");
        newUser.setEmail(eMail);

        if("1".equals(saveFlg)) {
            userService.genUser(newUser);
        }else{
            userService.updateUser(newUser);
        }

        return returnJson.toJSONString();
    }
    @ResponseBody
    @RequestMapping(value = "/deleteUser")
    public String deleteUser(HttpServletRequest request, Model model) throws Exception{
        String userids = request.getParameter("data");
        JSONArray JsonArray =JSONArray.parseArray(userids);
        if(JsonArray!=null){
            for(Object obj : JsonArray){
                JSONObject JsonObject = JSONObject.parseObject(obj.toString());
                String userCode = (String)JsonObject.get("userCode");
                userService.deleteUser(userCode);
            }
        }
        JSONObject result = new JSONObject();
        result.put("errmsg","");
        return result.toJSONString();
    }
    /**
     *
     *  用户列表
     */
    @RequestMapping(value = "/user")
    public String userList(Model model) {
        return "sysuser/userList";
    }
    /**
     *
     *  用户table
     */
    @ResponseBody
    @RequestMapping(value = "/userTable")
    public String userListTable(HttpServletRequest request,Model model) {
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        /* bootstrap table 分页参数*/
        int offset = Integer.parseInt(request.getParameter("offset"));
        int pageSize = Integer.parseInt(request.getParameter("limit"));
        int pageNum = (offset/pageSize)+1;
        String userName = request.getParameter("username");

        /*jqguid 分页参数
        int pageNum = Integer.parseInt(request.getParameter("page"));
        int pageSize = Integer.parseInt(request.getParameter("rows"));*/
        //设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
        PageHelper.startPage(pageNum, pageSize);
        List<User> userList;
        if(pubfunction.isEmptyStr(userName)){
            userList  = userService.getAllUser();
        }else{
            userList  = userService.getAllUserByName("%"+userName+"%");
        }
        PageInfo<User> appsPageInfo = new PageInfo<>(userList);
        JSONObject jsonObject = null;
        if(userList!=null){
            for(User user : userList){
                jsonObject = new JSONObject();
                jsonObject.put("userName",user.getUserName());
                jsonObject.put("id",user.getId()+"");
                jsonObject.put("userCode",user.getUserCode());
                jsonObject.put("email",user.getEmail());
                jsonObject.put("state",user.getState());
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
}
