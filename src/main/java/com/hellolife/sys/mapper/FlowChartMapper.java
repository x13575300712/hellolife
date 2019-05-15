package com.hellolife.sys.mapper;

import java.util.List;
import java.util.Map;

import com.hellolife.sys.dao.Node;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface FlowChartMapper {
	
	 @Select("SELECT * FROM node  ")
	 List<Node> getAllNode();
	 
	 @Select("SELECT * FROM befnode  ")
	 List<Map<String,String>> getAllBefnode();
	 
	 @Insert("insert into befnode(id,befnodeid,nodeid)values(#{id},#{befnodeid},#{nodeid}) ")
	 int insertBefnode(Map<String, String> map);
	 
	 @Insert("insert into node(nodeid,nodename,nodesn,flowid,posinX,posinY)values(#{nodeid},#{nodename},#{nodesn},#{flowid},#{posinX},#{posinY})")
	 int insertNode(Node map);
	 
	 @Delete("delete from node where nodeid=#{id}")
	 int deleteNode(String id);
	 
	 @Delete("delete from node where flowid=#{flowid}")
	 int deleteNodeByflowid(String flowid);
	 
	 @Delete("delete from befnode where befnodeid=#{befid} and nodeid=#{id}")
	 int deleteBefnode(@Param("befid") String befid, @Param("id") String id);
	 
	 @Delete("delete from befnode")
	 int deleteBefnodeAll();
	 
}
