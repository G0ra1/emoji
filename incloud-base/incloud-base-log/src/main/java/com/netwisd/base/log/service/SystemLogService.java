package com.netwisd.base.log.service;

import com.netwisd.base.log.dto.SystemLogDto;
import com.netwisd.common.es.config.Page;
import com.netwisd.common.es.vo.ElasticVo;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public interface SystemLogService {

    default int getFrom(Page page) {
        int current = page.getCurrent();
        int size = page.getSize();
        int from = (current - 1) * size;
        return from;
    }

    default String localDateTimeFormat(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String result = localDateTime.format(formatter);
        return result;
    }

    void insertSystemLog(String jsonLog);

    Page<ElasticVo> searchSystemLogPage(SystemLogDto systemLogDto);

    List<ElasticVo> searchSystemLog(SystemLogDto systemLogDto);
}
