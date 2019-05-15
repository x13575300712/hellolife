package com.hellolife.sys.service;

import com.hellolife.sys.dao.Role;
import com.hellolife.sys.dao.User;
import com.hellolife.sys.mapper.RoleMapper;
import com.hellolife.sys.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
        @Autowired
        RoleMapper roleMapper;
        
        public List<Role> selectRole(long id) {
            return roleMapper.selectRole(id);
        }
}