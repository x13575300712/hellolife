package com.hellolife.sys.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hellolife.sys.dao.User;
import com.hellolife.sys.pub.SnowflakeIdWorker;
import com.hellolife.sys.pub.pubfunction;
import com.hellolife.sys.service.UserService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;

@Controller
public class PubController {
    /**
     *
     *  文件导入公共页面
     */
    @RequestMapping(value = "/importFile")
    public String userList(HttpServletRequest request,Model model) {
        Enumeration em = request.getParameterNames();
        JSONObject jsonObject = new JSONObject();
        while (em.hasMoreElements()) {
            String name = (String) em.nextElement();
            String value = request.getParameter(name);
            if("callback".equals(name)){
                model.addAttribute(name,value);
                continue;
            }
            jsonObject.put(name,value);
        }
        model.addAttribute("jsonData",jsonObject.toJSONString());
        return "sysHtml/importFile";
    }
}
