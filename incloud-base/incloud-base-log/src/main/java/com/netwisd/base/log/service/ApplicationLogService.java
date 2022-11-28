package com.netwisd.base.log.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.log.dto.ApplicationLogDto;
import com.netwisd.base.log.entity.ApplicationLog;
import com.netwisd.common.es.vo.ElasticVo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public interface ApplicationLogService extends IService<ApplicationLog> {

    void insertApplicationLog(ApplicationLog applicationLog);

    default String localDateTimeFormat(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String result = localDateTime.format(formatter);
        return result;
    }

    void insertApplicationLog(String jsonLog);

    List<ElasticVo> searchApplicationLog(ApplicationLogDto applicationLogDto);
}
