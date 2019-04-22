package com.hellolife.rzzl.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.util.PoiPublicUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hellolife.rzzl.dao.CallRetInfo;
import com.hellolife.rzzl.dao.RetInfoDetail;
import com.hellolife.rzzl.pub.RzzlPub;
import com.hellolife.rzzl.service.CallRetInfoService;
import com.hellolife.rzzl.service.RetInfoDetailService;
import com.hellolife.sys.dao.Menu;
import com.hellolife.sys.dao.User;
import com.hellolife.sys.enums.ExceptionMsg;
import com.hellolife.sys.pub.MenuPub;
import com.hellolife.sys.pub.SnowflakeIdWorker;
import com.hellolife.sys.pub.pubfunction;
import com.hellolife.sys.service.MenuService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

@Controller
public class RzzlController {
	@Autowired
	private CallRetInfoService callRetInfoService;
	@Autowired
	private RetInfoDetailService retInfoDetailService;
	/**
	 *
	 *  项目列表
	 */
	@RequestMapping(value = "/rzzl")
	public String rzzlList(Model model) {
		return "/rzzl/rzzlList";
	}
	/**
	 *
	 *  打开创建项目页面
	 */
	@RequestMapping(value = "/createrzzl")
	public String menuList(HttpServletRequest request,Model model) {
		String id = request.getParameter("id");
		CallRetInfo c = null;
		String saveFlg = "1";
		if(!pubfunction.isEmptyStr(id)){
			long idL = Long.parseLong(id);
			c =  callRetInfoService.getCallById(idL);
			saveFlg = "0";
		}else{
			c = new CallRetInfo();
		}
		model.addAttribute("callRetInfo",c);
		model.addAttribute("saveFlg",saveFlg);
		return "/rzzl/createrzzl";
	}
	/**
	 * 
	 *  查询融资租赁计算列表
	 */
	@ResponseBody
	@RequestMapping(value = "/table/rzzlTable")
	public String login(HttpServletRequest request,Model model) {
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		int pageNum = Integer.parseInt(request.getParameter("offset"));
		int pageSize = Integer.parseInt(request.getParameter("limit"));
		PageHelper.startPage((pageNum/pageSize)+1, pageSize);
		List<CallRetInfo> retInfoList = callRetInfoService.getAllRetInfo();
		PageInfo<CallRetInfo> appsPageInfo = new PageInfo<>(retInfoList);
		JSONObject jsonObject = null;
		if(retInfoList!=null){
			for(CallRetInfo callRetInfo : retInfoList){
				jsonObject = new JSONObject();
				jsonObject.put("id",callRetInfo.getId());
				jsonObject.put("prjname",callRetInfo.getPrjname());
				jsonObject.put("prjamt",callRetInfo.getPrjamt());
				jsonObject.put("rate",callRetInfo.getRate());
				jsonObject.put("bgdt",callRetInfo.getBgdt());
				jsonObject.put("enddt",callRetInfo.getEnddt());
				jsonObject.put("fistdate",callRetInfo.getFistdate());
				jsonObject.put("retspan",callRetInfo.getRetspan());
				jsonObject.put("yeardays",callRetInfo.getYeardays());
				jsonObject.put("dayratedays",callRetInfo.getDayratedays());
				jsonObject.put("retway",callRetInfo.getRetway());
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
	 *  创建项目
	 */
	@ResponseBody
	@RequestMapping(value = "/form/createrzzl")
	public String saveCreateMenu(HttpServletRequest request,Model model) {
		String formData = request.getParameter("formObject");
		String saveFlg = request.getParameter("saveFlg");
		CallRetInfo callRetInfo = JSONObject.parseObject(formData,CallRetInfo.class);
		if("1".equals(saveFlg)){//1添加
			SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
			long id  = idWorker.nextId();
			callRetInfo.setId(id);
			callRetInfoService.genRetInfo(callRetInfo);
		}else{
			callRetInfoService.updateRetInfo(callRetInfo);
		}
		JSONObject returnJson = new JSONObject();
		return returnJson.toJSONString();
	}
	/**
	 *
	 *  删除项目
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteRzzl")
	public String deleteRzzl(HttpServletRequest request,Model model) {
		String rzzls = request.getParameter("data");
		JSONArray JsonArray =JSONArray.parseArray(rzzls);
		if(JsonArray!=null){
			for(Object obj : JsonArray){
				JSONObject JsonObject = JSONObject.parseObject(obj.toString());
				long id = (Long)JsonObject.get("id");
				callRetInfoService.delRetInfo(id);
			}
		}
		JSONObject returnJson = new JSONObject();
		return returnJson.toJSONString();
	}
	/**
	 *
	 *  计算项目
	 */
	@ResponseBody
	@RequestMapping(value = "/callRzzl")
	public String callRzzl(HttpServletRequest request,Model model) {
		String rzzls = request.getParameter("data");
		CallRetInfo callRetInfo = JSONObject.parseObject(rzzls,CallRetInfo.class);
		RzzlPub rzzlPub = new RzzlPub();
		try{
			List<RetInfoDetail> callList = rzzlPub.callEveryRet(callRetInfo,0,0);
			System.out.println(callList.size());
			retInfoDetailService.insertRetInfoList(callList);
		}catch (Exception e){
			e.printStackTrace();
		}
		JSONObject returnJson = new JSONObject();
		return returnJson.toJSONString();
	}
	/**
	 *
	 *  计算项目
	 */
	@ResponseBody
	@RequestMapping(value = "/importRzzl")
	public String importRzzl(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		String rzzls = request.getParameter("data");
		CallRetInfo callRetInfo = JSONObject.parseObject(rzzls,CallRetInfo.class);
		RzzlPub rzzlPub = new RzzlPub();
		if (!file.isEmpty()) {
			ImportParams params = new ImportParams();
			long start = new Date().getTime();
			try{
				List<Map<String, Object>> list = ExcelImportUtil.importExcel(file.getInputStream(), Map.class, params);
				for (Map<String, Object> m : list) {
					Set<String> set = m.keySet();
					for (String key : set) {
						System.out.println("___________________________" + key + "_)____" + m.get(key));
					}
				}
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		JSONObject returnJson = new JSONObject();
		return returnJson.toJSONString();
	}
}
