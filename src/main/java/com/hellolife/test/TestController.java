package com.hellolife.test;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hellolife.sys.enums.ExceptionMsg;

@Controller
public class TestController {

    /**
     *
     *  测试
     */
    @RequestMapping(value = {"/test"})
    public String login(Model model) {
        String htmlContent = "<p style='color:red'> 红色文字</p>";

        boolean testBoolean = true;
        model.addAttribute("htmlContent", htmlContent);
        model.addAttribute("testBoolean", testBoolean);
        return "test";
    }


}
