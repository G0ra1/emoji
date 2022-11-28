package com.netwisd.base.model.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.model.dto.DbDsDto;
import com.netwisd.base.model.entity.DbDs;
import com.netwisd.base.model.vo.DbDsVo;

import java.util.List;
import java.util.Set;

public interface DbDsService extends IService<DbDs> {

    Page page(DbDsDto dbDsDto);

    DbDsVo getDbDs(Long id);

    Boolean saveDbDs(DbDsDto dbDsDto);

    Boolean updateDbDs(DbDsDto dbDsDto);

    Boolean deleteDbDs(Long id);

    Set<String> currentDs();

    Boolean check(DbDsDto dbDsDto);

    List<DbDsVo> lists(DbDsDto dbDsDto);
}
