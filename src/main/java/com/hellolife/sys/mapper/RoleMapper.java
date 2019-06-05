package com.hellolife.sys.mapper;

import com.hellolife.sys.dao.Role;
import com.hellolife.sys.dao.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMapper {
	@Select("SELECT * FROM sys_user_role a left join sysrole b on a.roleid=b.id WHERE a.userid = #{id}")
	List<Role> selectRole(long id);
	@Select("SELECT * FROM sysrole where description = #{description}")
	List<Role> selectAllRoleByDesc(String description);
	@Select("SELECT * FROM sysrole ")
	List<Role> selectAllRole();

	@Select("SELECT * FROM sysrole a left join sys_menu_role b on a.id=b.roleid where b.menuid=#{menuId}")
	List<Role> selectMenuRole(long menuId);

	@Select("SELECT * FROM sysrole where id = #{id}")
	Role getRoleByid(long id);
	@Insert("insert into sysrole (id, role, description, useflg ) values(#{id}, #{role}, #{description}, #{useflg})")
	int genRole(Role role);
	@Update("update sysrole set role=#{role},description=#{description},useflg=#{useflg} where id=#{id}")
	int updateRole(Role role);
	@Delete("delete from sysrole where id = #{id}")
	int deleteRole(long id);
	@Insert("insert into sys_menu_role (roleId, menuId) " +
			"values(#{roleId}, #{menuId})")
	int genMenuAndRole(@Param("roleId")long roleId, @Param("menuId")long menuId);
	@Delete("delete from sys_menu_role  where roleId=#{roleId} and menuId=#{menuId} ")
	int deleteMenuAndRole(@Param("roleId")long roleId,@Param("menuId")long menuId);

	@Select("SELECT * FROM sysrole a left join sys_user_role b on a.id=b.roleid where b.userId=#{userId}")
	List<Role> selectUserRole(long userId);
	@Insert("insert into sys_user_role (roleId, userId) " +
			"values(#{roleId}, #{userId})")
	int genUserAndRole(@Param("roleId")long roleId, @Param("userId")long userId);
	@Delete("delete from sys_user_role  where roleId=#{roleId} and userId=#{userId} ")
	int deleteUserAndRole(@Param("roleId")long roleId,@Param("userId")long userId);
}
