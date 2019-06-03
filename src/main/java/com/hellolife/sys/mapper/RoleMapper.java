package com.hellolife.sys.mapper;

import com.hellolife.sys.dao.Role;
import com.hellolife.sys.dao.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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
	@Select("SELECT * FROM sysrole where id = #{id}")
	Role getRoleByid(long id);
	@Insert("insert into sysrole (id, role, description, useflg ) values(#{id}, #{role}, #{description}, #{useflg})")
	int genRole(Role role);
	@Update("update sysrole set role=#{role},description=#{description},useflg=#{useflg} where id=#{id}")
	int updateRole(Role role);


	 @Delete("delete from sysrole where id = #{id}")
	 int deleteRole(long id);
}
