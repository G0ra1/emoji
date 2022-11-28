package com.netwisd.base.log.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.log.entity.ApplicationLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface ApplicationLogMapper extends BaseMapper<ApplicationLog> {

    @Update("create stable if not exists ${applicationName}_log(ts timestamp, thread_name nchar(512), class_name nchar(512), host nchar(128), body nchar(4000)) tags(level nchar(64))")
    int createSuperTable(@Param("applicationName") String applicationName);

    @Insert("insert into ${applicationName}_${level}_log using ${applicationName}_log tags (#{level}) (ts, thread_name, class_name, host, body) values(#{ts}, #{threadName}, #{className}, #{host}, #{body})")
    int insertOne(ApplicationLog applicationLog);
}
