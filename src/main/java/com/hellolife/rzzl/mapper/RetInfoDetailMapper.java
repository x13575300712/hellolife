package com.hellolife.rzzl.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.hellolife.rzzl.dao.RetInfoDetail;
import java.util.List;

@Repository
public interface RetInfoDetailMapper {
    @Insert({
            "<script>",
            "insert into retinfodetial(id, startdate, enddate, retdate, retbal, retint, retamt, calldays, lvamt) values ",
            "<foreach collection='retInfoList' item='item' index='index' separator=','>",
            "(#{item.id}, #{item.startDate}, #{item.endDate}, #{item.retDate}, #{item.retBal}, #{item.retInt}, #{item.retAmt}, #{item.calldays}, #{item.lvamt})",
            "</foreach>",
            "</script>"
    })
    int insertRetInfoList(@Param(value="retInfoList") List<RetInfoDetail> retInfoList);

}
