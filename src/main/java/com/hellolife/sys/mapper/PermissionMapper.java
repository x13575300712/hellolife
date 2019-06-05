package com.hellolife.sys.mapper;

import com.hellolife.sys.dao.Permission;
import com.hellolife.sys.dao.Role;
import com.hellolife.sys.dao.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionMapper {
	 @Select("SELECT c.* FROM sys_user_role a join sys_role_permission b on a.roleid=b.roleid join syspermission c  WHERE a.userid = #{id}")
	 List<Permission> selectPermission(long id);

	@Select("SELECT * FROM  syspermission ")
	List<Permission> selectAllPermission();

	@Select("SELECT * FROM  syspermission where name like #{name}")
	List<Permission> selectAllPermissionByName(String name);

	@Select("SELECT a.* FROM  syspermission a left join sys_role_permission b on a.id=b.permissionId where b.roleId=#{roleId}")
	List<Permission> selectPermissionByRole(String roleId);

	@Select("SELECT * FROM  syspermission where id=#{id}")
	Permission getPermissionByid(long id);

	@Insert("insert into syspermission (id, useflg, name, parentId,parentIds,permission,resourceType,url ) " +
			"values(#{id}, #{useflg}, #{name}, #{parentId}, #{parentIds}, #{permission}, #{resourceType}, #{url})")
	int genPermission(Permission p);

	@Update("update syspermission set useflg=#{useflg},name=#{name},parentId=#{parentId},parentIds=#{parentIds},permission=#{permission},resourceType=#{resourceType} ,url=#{url} where id=#{id}")
	int updatePermission(Permission p);

	@Delete("delete FROM  syspermission where id = #{id}")
	int deletePermission(long id);

	@Insert("insert into sys_role_permission (permissionId, roleId) " +
			"values(#{permissionId}, #{roleId})")
	int genPermissionAndRole(@Param("permissionId")long permissionId,@Param("roleId")long roleId);

	@Delete("delete from sys_role_permission  where permissionId=#{permissionId} and roleId=#{roleId} ")
	int deletePermissionForRole(@Param("permissionId")long permissionId,@Param("roleId")long roleId);
}
