package com.hellolife.rzzl.mapper;

import com.hellolife.rzzl.dao.CallRetInfo;
import com.hellolife.sys.dao.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CallRetInfoMapper {
	@Select("select * from callretinfo")
	List<CallRetInfo> getAllRetInfo();
	@Select("select * from callretinfo where id=#{id}")
	CallRetInfo getCallById(long id);

	@Insert("insert into callretinfo (id, prjamt, rate, bgdt, enddt, fistdate, retspan, yeardays, dayratedays, retway, prjname) values(#{id}, #{prjamt}, " +
			"#{rate}, #{bgdt}, #{enddt}, #{fistdate}, #{retspan}, #{yeardays}, #{dayratedays}, #{retway}, #{prjname})")
	int genRetInfo(CallRetInfo callRetInfo);

	@Update("update callretinfo set prjamt=#{prjamt},rate=#{rate},bgdt=#{bgdt},enddt=#{enddt},fistdate=#{fistdate},retspan=#{retspan},yeardays=#{yeardays},dayratedays=#{dayratedays},retway=#{retway}" +
			",prjname=#{prjname} where id=#{id}")
	int updateRetInfo(CallRetInfo callRetInfo);

	@Delete("delete from callretinfo where id = #{id}")
	int delRetInfo(long id);
}
