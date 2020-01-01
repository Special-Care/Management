package com.nsusoft.management.mapper;

import com.nsusoft.management.domain.SysLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SysLogMapper {
    @Insert("insert into syslog values(replace(uuid(),'-',''),#{visitTime},#{username},#{ip},#{url},#{executionTime},#{method})")
    void insertSysLog(SysLog log);

    @Select("select * from syslog")
    List<SysLog> queryAllLog();
}
