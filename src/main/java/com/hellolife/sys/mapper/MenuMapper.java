package com.hellolife.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.hellolife.sys.dao.Menu;

@Repository
public interface MenuMapper {
	 @Select("SELECT * FROM sysmenu  ")
	 List<Menu> getAllMenu();
	 
	 @Select("SELECT * FROM sysmenu where parentid = #{parentid} ")
	 List<Menu> getMenuByParent(long parentid);
	 
}

