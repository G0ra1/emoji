package com.netwisd.base.model.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.model.dto.ModelingEntityDto;
import com.netwisd.base.model.dto.ModelingFieldDto;
import com.netwisd.base.model.entity.ModelingEntity;
import com.netwisd.base.model.entity.ModelingField;
import com.netwisd.base.model.mapper.ModelingEntityMapper;
import com.netwisd.base.model.mapper.ModelingFieldMapper;
import com.netwisd.base.model.service.ModelingFieldService;
import com.netwisd.base.common.model.vo.ModelingFieldVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class ModelingFieldServiceImpl extends ServiceImpl<ModelingFieldMapper, ModelingField> implements ModelingFieldService {

    @Autowired
    private Mapper dozerMapper;

    private final ModelingEntityMapper modelingEntityMapper;

    @Override
    public void saveModelingField(Long modelingId, List<ModelingEntityDto> modelingEntityList) {
        //保存模型实体字段
        for (ModelingEntityDto modelingEntityDto : modelingEntityList) {
            for (int i = 0; i < modelingEntityDto.getModelingFieldList().size(); i++) {
                ModelingFieldDto modelingFieldDto = modelingEntityDto.getModelingFieldList().get(i);
                ModelingField modelingField = dozerMapper.map(modelingFieldDto, ModelingField.class);
                modelingField.setModelingId(modelingId);
                modelingField.setEntityId(modelingEntityDto.getEntityId());
                modelingField.setModelingEntityId(modelingEntityDto.getId());
                modelingField.setModelingEntityName(modelingEntityDto.getTableName());
                modelingField.setSort(i);
                save(modelingField);
            }
        }
    }

    @Override
    public List<ModelingFieldVo> queryModelingFieldList(Long modelingId) {
        Map<Long, String> childTableSetKeyMap = modelingEntityMapper
                .selectList(Wrappers.<ModelingEntity>lambdaQuery().eq(ModelingEntity::getModelId, modelingId))
                .stream()
                .filter(x -> StrUtil.isNotEmpty(x.getChildTableSetKey()))
                .collect(Collectors.toMap(ModelingEntity::getId, ModelingEntity::getChildTableSetKey));
        return list(Wrappers.<ModelingField>lambdaQuery().eq(ModelingField::getModelingId, modelingId))
                .stream()
                .map(x -> {
                    ModelingFieldVo modelingFieldVo = dozerMapper.map(x, ModelingFieldVo.class);
                    if (MapUtil.isNotEmpty(childTableSetKeyMap)) {
                        modelingFieldVo.setChildTableSetKey(childTableSetKeyMap.get(x.getModelingEntityId()));
                    }
                    return modelingFieldVo;
                }).collect(Collectors.toList());
    }
}
