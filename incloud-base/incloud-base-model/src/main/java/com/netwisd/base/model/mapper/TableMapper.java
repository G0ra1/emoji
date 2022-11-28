package com.netwisd.base.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.model.entity.ModelEntity;
import com.netwisd.base.model.vo.ModelEntityVo;
import com.netwisd.base.model.vo.ModelFieldVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TableMapper extends BaseMapper<ModelEntity> {

    Page<ModelEntityVo> queryTablePage(Page page, @Param("tableName") String tableName, @Param("tableNameCh") String tableNameCh);

    List<ModelFieldVo> queryColumnInfoList(@Param("tableName") String tableName);

    ModelFieldVo getColumnInfo(@Param("tableName") String tableName, @Param("columnName") String columnName);

    ModelEntityVo getTableInfo(@Param("tableName") String tableName);

    void deleteByTableName(@Param("tableName") String tableName);

    void execDDLSql(@Param("sql") String sql);

    List<Map> execSql(@Param("sql") String sql);
}

