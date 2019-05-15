package com.hellolife.sys.dao;

public class Node {

	private String nodeid;

	private String nodename;
	
	private int nodesn;
	
	private String flowid;
	
	private int orderselect;
	
	private int posinX;
	
	private int posinY;

	public String getNodeid() {
		return nodeid;
	}

	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}

	public String getNodename() {
		return nodename;
	}

	public void setNodename(String nodename) {
		this.nodename = nodename;
	}

	public int getNodesn() {
		return nodesn;
	}

	public void setNodesn(int nodesn) {
		this.nodesn = nodesn;
	}

	public String getFlowid() {
		return flowid;
	}

	public void setFlowid(String flowid) {
		this.flowid = flowid;
	}

	public int getOrderselect() {
		return orderselect;
	}

	public void setOrderselect(int orderselect) {
		this.orderselect = orderselect;
	}

	public int getPosinX() {
		return posinX;
	}

	public void setPosinX(int posinX) {
		this.posinX = posinX;
	}

	public int getPosinY() {
		return posinY;
	}

	public void setPosinY(int posinY) {
		this.posinY = posinY;
	}
}
