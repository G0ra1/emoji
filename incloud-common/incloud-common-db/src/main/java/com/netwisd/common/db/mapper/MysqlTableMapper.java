package com.netwisd.common.db.mapper;
import com.netwisd.common.db.data.TableInfo;
import com.netwisd.common.db.mysql.ColumnMeta;
import com.netwisd.common.db.mysql.QuerySql;
import com.netwisd.common.db.mysql.TableMeta;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static com.netwisd.common.db.mysql.QuerySql.*;

/**
 * @Description: 数据库操作
 * @Author: zouliming@netwisd.com
 * @Date: 2020/4/19 1:15 下午
 */
@Mapper
@Repository
public interface MysqlTableMapper {

    /**
     * 创建表
     *
     * @param map
     */
    @SelectProvider(type = QuerySql.class, method = createTable)
    void createTable(@Param(paramName) Map<TableInfo, List<Object>> map);

    /**
     * 根据表名查询表在库中是否存在
     * @param tableName
     * @return true 表存在  false 表不存在
     */
    @SelectProvider(type = QuerySql.class, method = hasTable)
    @ResultType(Boolean.class)
    boolean hasTable(@Param(paramName) String tableName);

    /**
     * 查询表的字段
     *
     * @param tableName
     * @return
     */
    @SelectProvider(type = QuerySql.class, method = getColumnMetas)
    @ResultType(List.class)
    List<ColumnMeta> getColumnMetas(@Param(paramName) String tableName);

    /**
     * get table meta data
     *
     * support empty parameter and one parameter with "like" searching.
     * @param tableName
     * @return
     */
    @SelectProvider(type = QuerySql.class, method = getTableMetas)
    @ResultType(List.class)
    List<TableMeta> getTableMetas(@Param(paramName) String tableName,
                                  @Param("offset") int offset,
                                  @Param("rows") int rows);

    /**
     *
     * @param tableName
     * @return
     */
    @SelectProvider(type = QuerySql.class, method = getTableMetas)
    @ResultType(List.class)
    List<TableMeta> getTableMeta(@Param(paramName) String tableName);


    /**
     * 为表增加字段
     *
     * @param map
     */
    @SelectProvider(type = QuerySql.class, method = addColumns)
    void addColumns(@Param(paramName) Map<TableInfo, Object> map);

    /**
     * 修改表字段
     *
     * @param map
     */
    @SelectProvider(type = QuerySql.class, method = modifyColumn)
    void modifyColumn(@Param(paramName) Map<TableInfo, Object> map);

    /**
     * 删除表字段
     *
     * @param map
     */
    @SelectProvider(type = QuerySql.class, method = dropColumn)
    void dropColumn(@Param(paramName) Map<TableInfo, Object> map);

    /**
     * 删除主键
     * @param map
     */
    @SelectProvider(type = QuerySql.class, method = dropPrimaryKey)
    void dropPrimaryKey(@Param(paramName) Map<TableInfo, Object> map);

    /**
     * 删除索引
     * @param map
     */
    @SelectProvider(type = QuerySql.class, method = dropIndex)
    void dropIndex(@Param(paramName) Map<TableInfo, Object> map);

    /**
     * 如果表存在，就删除
     * @param tableName
     */
    @SelectProvider(type = QuerySql.class, method = dropTable)
    void dropTable(@Param(paramName) String tableName);

}
