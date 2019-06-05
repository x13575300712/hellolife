package com.hellolife.sys.dao;

public class Permission {
	private Long id;// 主键.
	private String name;// 名称.
	private String resourceType;// 资源类型，[menu|button]
	private String url;// 资源路径.
	private String permission; // 权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
	private Long parentId; // 父编号
	private String parentIds; // 父编号列表
	private int useflg;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	public int getUseflg() {
		return useflg;
	}

	public void setUseflg(int useflg) {
		this.useflg = useflg;
	}

	@Override
	public String toString() {
		return "Permission{" +
				"id=" + id +
				", name='" + name + '\'' +
				", resourceType='" + resourceType + '\'' +
				", url='" + url + '\'' +
				", permission='" + permission + '\'' +
				", parentId=" + parentId +
				", parentIds='" + parentIds + '\'' +
				", useflg=" + useflg +
				'}';
	}
}
