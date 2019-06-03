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
        //使用用户id查找该用户所有角色
        public List<Role> selectRole(long id) {
            return roleMapper.selectRole(id);
        }
        public List<Role> selectAllRole(String description) {
            return roleMapper.selectAllRoleByDesc(description);
        }
        public List<Role> selectAllRole() {
            return roleMapper.selectAllRole();
        }
        public Role getRoleByid(long id) {
        return roleMapper.getRoleByid(id);
    }
        public int genRole(Role role) { return roleMapper.genRole(role); }
        public int updateRole(Role role) { return roleMapper.updateRole(role); }
        public int deleteRole(long id) { return roleMapper.deleteRole(id); }
}