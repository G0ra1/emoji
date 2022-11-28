package com.netwisd.base.model.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.constants.YesNo;
import com.netwisd.base.common.util.RsaUtil;
import com.netwisd.base.model.dto.DbDsDto;
import com.netwisd.base.model.entity.DbDs;
import com.netwisd.base.model.mapper.DbDsMapper;
import com.netwisd.base.model.service.DbDsService;
import com.netwisd.base.model.vo.DbDsVo;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.db.util.DbDsUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.DriverManager;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class DbDsServiceImpl extends ServiceImpl<DbDsMapper, DbDs> implements DbDsService {

    private final DataSourceCreator dataSourceCreator;

    private final DynamicRoutingDataSource dynamicRoutingDataSource;

    private final DataSource dataSource;

    @Autowired
    private Mapper dozerMapper;

    /**
     * 单表简单查询操作
     *
     * @param dbDsDto
     * @return
     */
    @Override
    public Page page(DbDsDto dbDsDto) {
        return page(dbDsDto.getPage(), Wrappers.<DbDs>lambdaQuery()
                .like(StrUtil.isNotEmpty(dbDsDto.getPoolName()), DbDs::getPoolName, dbDsDto.getPoolName())
                .eq(ObjectUtil.isNotEmpty(dbDsDto.getIsEnable()), DbDs::getIsEnable, dbDsDto.getIsEnable())
                .orderByAsc(DbDs::getCreateTime));
    }

    @Override
    public List<DbDsVo> lists(DbDsDto dbDsDto) {
        return list(Wrappers.<DbDs>lambdaQuery().like(StrUtil.isNotEmpty(dbDsDto.getPoolName()), DbDs::getPoolName, dbDsDto.getPoolName())
                .eq(ObjectUtil.isNotEmpty(dbDsDto.getIsEnable()), DbDs::getIsEnable, dbDsDto.getIsEnable())
                .orderByAsc(DbDs::getCreateTime))
                .stream()
                .map(x -> dozerMapper.map(x, DbDsVo.class))
                .collect(Collectors.toList());
    }

    @Override
    public Set<String> currentDs() {
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        return ds.getCurrentDataSources().keySet();
    }

    @Override
    public Boolean check(DbDsDto dbDsDto) {
        try {
            DriverManager.getConnection(dbDsDto.getUrl(), dbDsDto.getUsername(), dbDsDto.getPassword());
        } catch (Exception e) {
            throw new IncloudException("数据库连接失败");
        }
        return true;
    }

    @Override
    public DbDsVo getDbDs(Long id) {
        DbDs dbDs = getById(id);
        return ObjectUtil.isNull(dbDs) ? null : dozerMapper.map(dbDs, DbDsVo.class);
    }

    @Override
    public Boolean saveDbDs(DbDsDto dbDsDto) {
        Optional.ofNullable(count(Wrappers.<DbDs>lambdaQuery().eq(DbDs::getPoolName, dbDsDto.getPoolName()))).filter(x -> {
            if (x > 0) {
                throw new IncloudException("{}数据源已经存在", dbDsDto.getPoolName());
            }
            return true;
        });
        if (dbDsDto.getIsEnable() == YesNo.YES.getCode()) {
            DbDsDto dto = handleSecret(dbDsDto);
            //启用验证数据源是否可用
            check(dto);
            //启用时，加到动态数据源路由里去
            addDataSource(dto);
        }
        return save(dozerMapper.map(dbDsDto, DbDs.class));
    }

    /**
     * 处理下密码,把密文处理成正常的明文，给动态数据源
     *
     * @param dbDsDto
     */
    DbDsDto handleSecret(DbDsDto dbDsDto) {
        DbDsDto dto = dozerMapper.map(dbDsDto, DbDsDto.class);
        dto.setPassword(RsaUtil.handleSecret(dbDsDto.getPassword()));
        return dto;
    }

    @Override
    public Boolean updateDbDs(DbDsDto dbDsDto) {
        Optional.ofNullable(getById(dbDsDto.getId())).orElseThrow(() -> new IncloudException("未找到要修改的数据源"));
        Optional.ofNullable(count(Wrappers.<DbDs>lambdaQuery().ne(DbDs::getId, dbDsDto.getId()).eq(DbDs::getPoolName, dbDsDto.getPoolName())))
                .filter(x -> {
                    if (x > 0) {
                        throw new IncloudException("{}数据源已经存在", dbDsDto.getPoolName());
                    }
                    return true;
                });
        //启用验证数据源是否可用
        DbDsDto dto = null;
        if (dbDsDto.getIsEnable() == YesNo.YES.getCode()) {
            dto = handleSecret(dbDsDto);
            check(dto);
        }
        //删除数据源
        delDataSource(dbDsDto.getPoolName());
        //启用时，加到动态数据源路由里去
        if (dbDsDto.getIsEnable() == YesNo.YES.getCode()) {
            addDataSource(dto);
        }
        return updateById(dozerMapper.map(dbDsDto, DbDs.class));
    }

    @Override
    public Boolean deleteDbDs(Long id) {
        DbDs oldDbDs = Optional.ofNullable(getById(id)).orElseThrow(() -> new IncloudException("未找到要删除的数据源"));
        //删除数据源
        delDataSource(oldDbDs.getPoolName());
        return removeById(id);
    }

    private void addDataSource(DbDsDto dbDsDto) {
        //非MySQL的数据源暂时不放到数据源里面
        if (!"mysql".equals(dbDsDto.getType())) return;
        DataSourceProperty dataSourceProperty = DbDsUtil.initDbDsProperty(dbDsDto.getPoolName(), dbDsDto.getUrl(), dbDsDto.getUsername(), dbDsDto.getPassword());
        DataSource dataSource = dataSourceCreator.createDataSource(dataSourceProperty);
        dynamicRoutingDataSource.addDataSource(dataSourceProperty.getPoolName(), dataSource);
    }

    private void delDataSource(String ds) {
        dynamicRoutingDataSource.removeDataSource(ds);
    }
}
