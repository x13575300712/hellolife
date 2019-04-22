package com.hellolife.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.hellolife.sys.dao.Menu;

@Repository
public interface MenuMapper {
	 @Select("SELECT * FROM sysmenu  ")
	 List<Menu> getAllMenu();
	 
	 @Select("SELECT * FROM sysmenu where parentid = #{parentid} order by ordernum")
	 List<Menu> getMenuByParent(long parentid);

	@Select("SELECT * FROM sysmenu where id = #{id}")
	Menu getMenuById(long id);

	@Update("update sysmenu set name=#{name},url=#{url},icon=#{icno},attr=#{attr},ordernum=#{orderNum},parentid=#{parentId} where id=#{id}")
	int updateMenu(Menu m);

	@Insert("insert into sysmenu (id, name, url, icon, attr, ordernum, parentid) values(#{id}, #{name}, #{url}, #{icno}, #{attr}, #{orderNum}, #{parentId})")
	int genMenu(Menu m);

	@Delete("delete from sysmenu where id = #{id}")
	int deleteMenu(long id);

}

