package com.hellolife.sys.dao;

import java.util.List;

public class Role {
	private Long id;

	private String role;
	
	private String description;

	private String useflg;
	
	private List<User> user;// 一个角色对应多个用户
	
	private List<Permission> permission;// 一个角色对应多个权限

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUseflg() {
		return useflg;
	}

	public void setUseflg(String useflg) {
		this.useflg = useflg;
	}

	public List<User> getUser() {
		return user;
	}

	public void setUser(List<User> user) {
		this.user = user;
	}

	public List<Permission> getPermission() {
		return permission;
	}

	public void setPermission(List<Permission> permission) {
		this.permission = permission;
	}
}
