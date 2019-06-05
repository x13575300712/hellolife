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
    public List<Permission> selectAllPermission() {
        return permissionMapper.selectAllPermission();
    }
    public List<Permission> selectAllPermission(String name) {
        return permissionMapper.selectAllPermissionByName(name);
    }
    public List<Permission> selectPermissionByRole(String roleId) {
        return permissionMapper.selectPermissionByRole(roleId);
    }
    public Permission getPermissionByid(long id) {
        return permissionMapper.getPermissionByid(id);
    }
    public int genPermission(Permission p) {
        return permissionMapper.genPermission(p);
    }
    public int updatePermission(Permission p) {
        return permissionMapper.updatePermission(p);
    }
    public int deletePermission(long id) {
        return permissionMapper.deletePermission(id);
    }
    public int genPermissionAndRole(long permissionId,long roleId) {
        return permissionMapper.genPermissionAndRole(permissionId,roleId);
    }
    public int deletePermissionForRole(long permissionId,long roleId) {
        return permissionMapper.deletePermissionForRole(permissionId,roleId);
    }
}