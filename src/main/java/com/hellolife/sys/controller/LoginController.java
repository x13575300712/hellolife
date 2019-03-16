package com.hellolife.sys.controller;

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
public class LoginController {
	
	/**
	 * 
	 *  登陆
	 */
	@RequestMapping(value = {"/","/login"})
	public String login(Model model) {
		return "login";
	}
	/**
	 * 
	 *  登陆
	 */
	@RequestMapping(value = "/home")
	public String login() {
		return "home";
	}
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request,Model model) throws Exception{
	    // 登录失败从request中获取shiro处理的异常信息。
	    // shiroLoginFailure:就是shiro异常类的全类名.
	    String exception = (String) request.getAttribute("shiroLoginFailure");
	    String msg = "";
	    String rescode = "000000";
	    if (exception != null) {
	        if (UnknownAccountException.class.getName().equals(exception)) {
	        	ExceptionMsg e = ExceptionMsg.ACCOUNTNOTHAVE;
	        	rescode = e.getCode();
	        	msg = e.getMsg();
	        } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
	        	ExceptionMsg e = ExceptionMsg.PASSWORDERROR;
	        	rescode = e.getCode();
	        	msg = e.getMsg();
	        } else if ("kaptchaValidateFailed".equals(exception)) {
	        	ExceptionMsg e = ExceptionMsg.VERIFICATIONERROR;
	        	rescode = e.getCode();
	        	msg = e.getMsg();
	        } else {
	        	ExceptionMsg e = ExceptionMsg.FAILED;
	        	rescode = e.getCode();
	        	msg = e.getMsg();
	        }
	    }
	    // 此方法不处理登录成功,由shiro进行处理
	    JSONObject resultJsonObject = new JSONObject();
	    resultJsonObject.put("errmsg", msg);
	    resultJsonObject.put("rescode", rescode);
	    resultJsonObject.put("url", "/home");
	    return resultJsonObject.toJSONString();
	}
}
