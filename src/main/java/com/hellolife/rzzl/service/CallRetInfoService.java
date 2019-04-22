package com.hellolife.rzzl.service;

import com.hellolife.rzzl.dao.CallRetInfo;
import com.hellolife.rzzl.mapper.CallRetInfoMapper;
import com.hellolife.sys.dao.Menu;
import com.hellolife.sys.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CallRetInfoService {
	  @Autowired
      CallRetInfoMapper callRetInfoMapper;
	  
      public List<CallRetInfo> getAllRetInfo() {
    	  return callRetInfoMapper.getAllRetInfo();
      }
      public CallRetInfo getCallById(long id) {
    	  return callRetInfoMapper.getCallById(id);
      }
      public int genRetInfo(CallRetInfo callRetInfo) {
    	  return callRetInfoMapper.genRetInfo(callRetInfo);
      }
      public int delRetInfo(long id) {
          return callRetInfoMapper.delRetInfo(id);
      }
      public int updateRetInfo(CallRetInfo callRetInfo) {
          return callRetInfoMapper.updateRetInfo(callRetInfo);
      }
}
