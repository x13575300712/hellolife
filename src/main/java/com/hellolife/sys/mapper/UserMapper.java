package com.hellolife.sys.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.hellolife.sys.dao.User;

@Repository
public interface UserMapper {
	 @Select("SELECT * FROM sysuser WHERE id = #{id}")
	 User selectUser(int id);
	 @Select("SELECT * FROM sysuser WHERE usercode = #{usercode}")
	 User loginUser(String usercode);
	 @Insert("insert into sysuser (id, username, password, email, usercode, salt, state) values(#{id}, #{userName}, #{passWord}, #{email}, #{userCode}, #{salt}, #{state})")
	 int genUser(User user);
}
