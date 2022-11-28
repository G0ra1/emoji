package com.netwisd.base.model.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.model.dto.ModelEntityDto;
import com.netwisd.base.model.vo.ModelEntityVo;
import com.netwisd.base.model.vo.ModelFieldVo;

import java.util.List;
import java.util.Map;

public interface TableService {

    ModelEntityVo getTableInfo(String dsId, String tableName);

    Page<ModelEntityVo> queryTablePage(String dsId, ModelEntityDto modelEntityDto);

    List<ModelFieldVo> queryColumnInfoList(String dsId, String tableName);

    void dropTable(String dsId, String tableName);

    String getExecSql(String dsId, ModelEntityDto modelEntityDto);

    void execDDLSql(String dsId, String sql);

    List<Map> execSql(String dsId, String sql);
}