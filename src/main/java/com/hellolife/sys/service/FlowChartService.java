package com.hellolife.sys.service;

import java.util.List;
import java.util.Map;

import com.hellolife.sys.dao.Node;
import com.hellolife.sys.mapper.FlowChartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlowChartService {
	@Autowired
	FlowChartMapper flowChartMapper;
	  
	public List<Node> getAllNode(){
		 return flowChartMapper.getAllNode();
	}
	
	public List<Map<String,String>> getAllBefnode(){
		 return flowChartMapper.getAllBefnode();
	}
	
	public int insertBefnode(Map<String,String> map) {
		return flowChartMapper.insertBefnode(map);
		
	}
	 
	public int insertNode(Node map) {
		return flowChartMapper.insertNode(map);
	}
	
	public int deleteNode(String id) {
		return flowChartMapper.deleteNode(id);
	}
	public int deleteNodeByflowid(String flowid) {
		return flowChartMapper.deleteNodeByflowid(flowid);
	}
	 
	public int deleteBefnode( String befid,String id) {
		return flowChartMapper.deleteBefnode(befid,id);
	}
	public int deleteBefnodeAll() {
		return flowChartMapper.deleteBefnodeAll();
	}
}
