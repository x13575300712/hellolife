package com.hellolife.sys.mapper;

import com.hellolife.sys.dao.Permission;
import com.hellolife.sys.dao.Role;
import com.hellolife.sys.dao.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionMapper {
	 @Select("SELECT c.* FROM sys_user_role a join sys_role_permission b on a.roleid=b.roleid join syspermission c  WHERE a.userid = #{id}")
	 List<Permission> selectPermission(long id);

}
