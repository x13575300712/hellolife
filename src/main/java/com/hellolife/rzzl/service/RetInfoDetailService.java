package com.hellolife.rzzl.service;


import com.hellolife.rzzl.dao.RetInfoDetail;
import com.hellolife.rzzl.mapper.RetInfoDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RetInfoDetailService {
	  @Autowired
      RetInfoDetailMapper retInfoDetailMapper;
      public int insertRetInfoList(List<RetInfoDetail> retInfoList) {
          return retInfoDetailMapper.insertRetInfoList(retInfoList);
      }
}
