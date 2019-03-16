package com.hellolife.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hellolife.sys.dao.Menu;
import com.hellolife.sys.mapper.MenuMapper;
@Service
public class MenuService {
	  @Autowired
	  MenuMapper menuMapper;
	  
      public List<Menu> getAllMenu() {
    	  return menuMapper.getAllMenu();
      }
      
      public List<Menu> getMenuByParent(long parentId) {
    	  return menuMapper.getMenuByParent(parentId);
      }
}
