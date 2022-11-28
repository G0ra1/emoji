package com.netwisd.base.model.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.constants.YesNo;
import com.netwisd.base.model.dto.ModelEntityDto;
import com.netwisd.base.model.dto.ModelFieldDto;
import com.netwisd.base.model.entity.ModelEntity;
import com.netwisd.base.model.entity.ModelField;
import com.netwisd.base.model.mapper.ModelEntityMapper;
import com.netwisd.base.model.service.ModelEntityService;
import com.netwisd.base.model.service.ModelFieldService;
import com.netwisd.base.model.vo.ModelEntityVo;
import com.netwisd.base.model.vo.ModelFieldVo;
import com.netwisd.common.core.exception.IncloudException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class ModelEntityServiceImpl extends ServiceImpl<ModelEntityMapper, ModelEntity> implements ModelEntityService {

    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private ModelFieldService modelFieldService;

    @Override
    public Page queryModelEntityPage(ModelEntityDto modelEntityDto) {
        return page(modelEntityDto.getPage(), Wrappers.<ModelEntity>lambdaQuery()
                .eq(ObjectUtil.isNotNull(modelEntityDto.getModelTypeId()), ModelEntity::getModelTypeId, modelEntityDto.getModelTypeId())
                .like(ObjectUtil.isNotNull(modelEntityDto.getModelTypeName()), ModelEntity::getModelTypeName, modelEntityDto.getModelTypeName())
                .like(StrUtil.isNotEmpty(modelEntityDto.getTableName()), ModelEntity::getTableName, modelEntityDto.getTableName())
                .like(StrUtil.isNotEmpty(modelEntityDto.getTableNameCh()), ModelEntity::getTableNameCh, modelEntityDto.getTableNameCh())
                .orderByDesc(ModelEntity::getCreateTime));
    }

    @Override
    @Transactional
    public boolean saveModelEntity(ModelEntityDto modelEntityDto) {
        this.validationModelEntityDto(modelEntityDto);
        //实体字段保存
        modelFieldService.saveModelFiled(modelEntityDto.getId(), modelEntityDto.getModuleName(), modelEntityDto.getFiledList());
        //保存实体
        save(dozerMapper.map(modelEntityDto, ModelEntity.class));
        return true;
    }

    @Override
    @Transactional
    public boolean upModelEntity(ModelEntityDto modelEntityDto) {
        this.validationModelEntityDto(modelEntityDto);
        Optional.ofNullable(getById(modelEntityDto.getId())).orElseThrow(() -> new IncloudException("未获取到实体数据"));
        modelFieldService.updateModelFiled(modelEntityDto.getId(), modelEntityDto.getModuleName(), modelEntityDto.getFiledList());
        //删除页面过来的数据和数据库数据不匹配的数据
        modelFieldService.deleteMismatchData(modelEntityDto.getFiledList(), modelEntityDto.getId());
        return updateById(dozerMapper.map(modelEntityDto, ModelEntity.class));
    }

    @Override
    public ModelEntityVo getModelEntity(Long id) {
        ModelEntity modelEntity = Optional.ofNullable(getById(id)).orElseThrow(() -> new IncloudException("未获取到实体数据"));
        return Optional.of(modelEntity).map(x -> {
            ModelEntityVo vo = dozerMapper.map(modelEntity, ModelEntityVo.class);
            vo.setFiledList(modelFieldService.queryModelFieldList(x.getId()));
            return vo;
        }).get();
    }

    @Override
    @Transactional
    public boolean delModelEntity(Long id) {
        //删除实体字段
        modelFieldService.delModelFile(Long.valueOf(id));
        //删除表数据
        return removeById(id);
    }

    @Override
    public List<ModelFieldVo> queryFieldList(String entityId) {
        return modelFieldService.list(Wrappers.<ModelField>lambdaQuery().eq(StrUtil.isNotEmpty(entityId), ModelField::getEntityId, entityId))
                .stream().map(x -> dozerMapper.map(x, ModelFieldVo.class)).collect(Collectors.toList());
    }

    public void validationModelEntityDto(ModelEntityDto modelEntityDto) {
        Optional.ofNullable(modelEntityDto).orElseThrow(() -> new IncloudException("参数错误"));
        Long isKetCount = modelEntityDto.getFiledList().stream().filter(x -> x.getIsKey() == YesNo.YES.getCode() && !"del".equals(x.getOperateType())).count();
        if (isKetCount > 1) {
            throw new IncloudException("不支持复合主键");
        }
        for (ModelFieldDto fieldDto : modelEntityDto.getFiledList()) {
            if ("del".equals(fieldDto.getOperateType())) {
                continue;
            }
            if (StrUtil.isEmpty(fieldDto.getName())) {
                throw new IncloudException("名称为空");
            }
            if (StrUtil.isEmpty(fieldDto.getDbType())) {
                throw new IncloudException("数据类型为空");
            }
            if (ObjectUtil.isNotNull(fieldDto.getFkTableId()) && modelEntityDto.getId().equals(fieldDto.getFkTableId())) {
                throw new IncloudException("外键关联错误");
            }
            if (StrUtil.isNotEmpty(fieldDto.getFkTableName()) && StrUtil.isEmpty(fieldDto.getFkFieldName())) {
                throw new IncloudException("外键关联错误");
            }
        }
        //可以使用stream的match来判断，先走filter过滤完，然后走allMatch
        List<String> nameList = modelEntityDto.getFiledList().stream()
                .filter(x -> StrUtil.isNotEmpty(x.getName()))
                .map(ModelFieldDto::getName)
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()))
                .entrySet().stream().filter(e -> e.getValue() > 1)
                .map(Map.Entry::getKey).collect(Collectors.toList());
        if (nameList.size() >= 1) {
            throw new IncloudException("请检查是否有重复的列名");
        }
    }
}
