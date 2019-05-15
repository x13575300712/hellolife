package com.hellolife.sys.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hellolife.sys.pub.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hellolife.sys.dao.User;
import com.hellolife.sys.mapper.UserMapper;

import java.util.List;

@Service
public class UserService {
        @Autowired
        UserMapper userMapper;
        
        public User selectUser(int id) {
            return userMapper.selectUser(id);
        }
        public User loginUser(String usercode) {
            return userMapper.loginUser(usercode);
        }
        public  int genUser(User user) {
        	 return userMapper.genUser(user);
        }
        public  int updateUser(User user) {
        	 return userMapper.updateUser(user);
        }
        public  int deleteUser(String userCode) {
        	 return userMapper.deleteUser(userCode);
        }
        public  List<User> getAllUserByName(String userName) {
                return userMapper.getAllUserByName(userName);
        }
        public  List<User> getAllUser() {
                return userMapper.getAllUser();
        }
}