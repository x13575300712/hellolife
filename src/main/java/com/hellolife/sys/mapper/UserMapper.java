package com.hellolife.sys.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.hellolife.sys.dao.User;

import java.util.List;

@Repository
public interface UserMapper {
	 @Select("SELECT * FROM sysuser WHERE id = #{id}")
	 User selectUser(int id);
	 @Select("SELECT * FROM sysuser WHERE usercode = #{usercode}")
	 User loginUser(String usercode);
	 @Insert("insert into sysuser (id, username, password, email, usercode, salt, state) values(#{id}, #{userName}, #{passWord}, #{email}, #{userCode}, #{salt}, #{state})")
	 int genUser(User user);
	 @Update("update sysuser set username=#{userName},email=#{email},state=#{state},usercode=#{userCode} where id=#{id}")
	 int updateUser(User user);
	 @Select("Select * from sysuser where username like #{userName} ")
	 List<User> getAllUserByName(String userName);
	 @Select("Select * from sysuser ")
	 List<User> getAllUser();
	 @Delete("delete from sysuser where usercode = #{usercode}")
	 int deleteUser(String usercode);
}
