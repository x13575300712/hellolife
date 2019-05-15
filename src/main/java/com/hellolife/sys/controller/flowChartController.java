package com.hellolife.sys.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hellolife.sys.dao.Menu;
import com.hellolife.sys.dao.Node;
import com.hellolife.sys.dao.User;
import com.hellolife.sys.enums.ExceptionMsg;
import com.hellolife.sys.pub.MenuPub;
import com.hellolife.sys.pub.SnowflakeIdWorker;
import com.hellolife.sys.pub.pubfunction;
import com.hellolife.sys.service.FlowChartService;
import com.hellolife.sys.service.MenuService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class flowChartController {
	@Autowired
	FlowChartService flowChartService;
	@ResponseBody
	@RequestMapping("/test/jsPlumbTest")
	public String jsPlumbTest(HttpServletRequest request) {
		String flowid = request.getParameter("flowid");
		boolean fistflg = true;
		int nodesnNow = 1000;
		String startId = "";
		List<Map<String,String>> result = new ArrayList<>();
		List<Node> nodeMap = flowChartService.getAllNode();
		List<Map<String,String>> befnodeMap = flowChartService.getAllBefnode();
		Map<String,List<Map<String,String>>> dataMap = new HashMap<>();
		for(Map<String,String> map: befnodeMap) {
			String nodeId = map.get("befnodeid");
			if(dataMap.containsKey(nodeId)) {
				List<Map<String,String>> dataList = dataMap.get(nodeId);
				dataList.add(map);
			}else {
				List<Map<String,String>> dataList = new ArrayList<Map<String,String>>();
				dataList.add(map);
				dataMap.put(nodeId, dataList);
			}
		}
		int maxNodesn = 0;
		for(Node node: nodeMap) {
			Map<String,String> map = new HashMap<>();
			String nodeId = node.getNodeid();
			int ordernum = node.getNodesn();
			if(ordernum>maxNodesn) {
				maxNodesn = ordernum;
			}
			List<Map<String,String>> list = dataMap.get(nodeId);
			String nextNode = JSONObject.toJSONString(list);
			map.put("nextNode", nextNode);
			map.put("nodeid",nodeId);
			map.put("nodename",node.getNodename());
			map.put("nodesn",node.getNodesn()+"");
			map.put("flowid",node.getFlowid());
			map.put("posinX",node.getPosinX()+"");
			map.put("posinY",node.getPosinY()+"");
			result.add(map);
			if(fistflg) {
				int nodesn = ordernum;//
				if(nodesnNow>nodesn) {
					nodesnNow = nodesn;
					startId = nodeId;
				}
			}
			if(fistflg&&"1".equals(map.get("fstflg"))) {
				fistflg = false;
				startId = nodeId;
			}
		}
		JSONArray jsonArray = new JSONArray();
		for(Map<String,String> map :result) {
			String nodeId = map.get("nodeid");
			String nodename = map.get("nodename");
			String nodesn = map.get("nodesn");
			JSONArray nextNodeArray = new JSONArray();
			List<Map<String,String>> list = dataMap.get(nodeId);
			if(list!=null) {
				for(int j =0;j<list.size();j++) {
					Map<String,String> nextNodeMap = list.get(j);
					JSONObject nextNodeObject = new JSONObject();
					nextNodeObject.put("nodeid",nextNodeMap.get("nodeid"));
					nextNodeObject.put("id",nextNodeMap.get("id"));
					nextNodeArray.add(nextNodeObject);
				}
			}
			String posinY = map.get("posinY");
			if("0".equals(posinY)) {
				posinY = "";
			}
			String posinX = map.get("posinX");
			if("0".equals(posinX)) {
				posinX = "";
			}
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", nodeId);
			jsonObject.put("type", "Mytest");
			jsonObject.put("nodename", nodename);
			jsonObject.put("comment", nodename);
			jsonObject.put("status", "1");
			jsonObject.put("data", new JSONArray());
			jsonObject.put("nextNode", nextNodeArray);
			jsonObject.put("top", posinY);
			jsonObject.put("left", posinX);
			jsonObject.put("nodesn", nodesn);
			jsonArray.add(jsonObject);
		}
		JSONObject resultJsonObject  = new JSONObject();
		resultJsonObject.put("id", "4000");
		resultJsonObject.put("name", "功能测试");
		resultJsonObject.put("status", "enable");
		resultJsonObject.put("startId", startId);
		resultJsonObject.put("maxNodesn", maxNodesn);
		resultJsonObject.put("varList", new JSONArray());
		resultJsonObject.put("nodeList", jsonArray);
		System.out.println(resultJsonObject.toJSONString());
		return resultJsonObject.toJSONString();
	}

	@ResponseBody
	@RequestMapping("/test/jsPlumbTestSave")
	public String jsPlumbTestSave(HttpServletRequest request) {
		String flowid = request.getParameter("flowid");
		Map<String,String> idMap = new HashMap<String,String>();
		String flowChartJson = request.getParameter("flowChartJson");
		JSONObject infoJsonObject = JSONObject.parseObject(flowChartJson);
		JSONArray nodesJsonArray = (JSONArray)infoJsonObject.get("nodes");
		flowChartService.deleteNodeByflowid(flowid);
		for(Object element : nodesJsonArray) {
			JSONObject node = (JSONObject)element;
			Node nodeMap = new Node();
			String nodeid = (String)node.get("nodeId");
			String blockId = (String)node.get("blockId");
			if(pubfunction.isEmptyStr(nodeid)) {
				SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
				long id = idWorker.nextId();
				nodeid = id +"";
			}
			idMap.put(blockId,nodeid);
			nodeMap.setFlowid(flowid);
			nodeMap.setNodeid(nodeid);
			nodeMap.setNodename((String)node.get("name"));
			nodeMap.setNodesn(Integer.parseInt((String)node.get("nodesn")));
			nodeMap.setPosinX(Integer.parseInt(node.get("positionX")+""));
			nodeMap.setPosinY(Integer.parseInt(node.get("positionY")+""));
			flowChartService.insertNode(nodeMap);
		}
		JSONArray connectJsonArray = (JSONArray)infoJsonObject.get("connections");
		flowChartService.deleteBefnodeAll();
		if(connectJsonArray!=null) {
			for(Object element : connectJsonArray) {
				JSONObject connection = (JSONObject)element;
				Map<String,String> connectionMap = new HashMap<String,String>();
				SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
				long id = idWorker.nextId();
				connectionMap.put("id", id+"");
				connectionMap.put("befnodeid", idMap.get((String)connection.get("pageSourceId")));
				connectionMap.put("nodeid", idMap.get((String)connection.get("pageTargetId")));
				flowChartService.insertBefnode(connectionMap);
			}
		}
		return "";
	}
}
