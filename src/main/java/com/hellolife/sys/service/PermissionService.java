package com.hellolife.sys.service;

import com.hellolife.sys.dao.Permission;
import com.hellolife.sys.dao.Role;
import com.hellolife.sys.mapper.PermissionMapper;
import com.hellolife.sys.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {
        @Autowired
        PermissionMapper permissionMapper;
        
        public List<Permission> selectPermission(long id) {
            return permissionMapper.selectPermission(id);
        }
}