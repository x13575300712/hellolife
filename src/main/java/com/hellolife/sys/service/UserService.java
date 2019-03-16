package com.hellolife.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hellolife.sys.dao.User;
import com.hellolife.sys.mapper.UserMapper;

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
}