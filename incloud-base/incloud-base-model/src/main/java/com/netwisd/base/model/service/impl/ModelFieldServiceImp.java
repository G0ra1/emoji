package com.netwisd.base.model.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.model.dto.ModelFieldDto;
import com.netwisd.base.model.entity.ModelField;
import com.netwisd.base.model.mapper.ModelFieldMapper;
import com.netwisd.base.model.service.ModelFieldService;
import com.netwisd.base.model.vo.ModelFieldVo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class ModelFieldServiceImp extends ServiceImpl<ModelFieldMapper, ModelField> implements ModelFieldService {

    @Autowired
    private Mapper dozerMapper;

    @Override
    @Transactional
    public void saveModelFiled(Long entityId, String entityName, List<ModelFieldDto> list) {
        if (CollectionUtil.isEmpty(list)) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            ModelFieldDto modelFieldDto = list.get(i);
            if ("del".equals(modelFieldDto.getOperateType())) {
                continue;
            }
            modelFieldDto.setEntityId(entityId);
            modelFieldDto.setEntityName(entityName);
            modelFieldDto.setSort(i);
            save(dozerMapper.map(modelFieldDto, ModelField.class));
        }
    }

    @Override
    @Transactional
    public void updateModelFiled(Long entityId, String entityName, List<ModelFieldDto> list) {
        if (CollectionUtil.isEmpty(list)) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            ModelFieldDto modelFieldDto = list.get(i);
            modelFieldDto.setEntityId(entityId);
            modelFieldDto.setEntityName(entityName);
            modelFieldDto.setSort(i);
            if ("del".equals(modelFieldDto.getOperateType())) {
                removeById(modelFieldDto.getId());
            } else {
                saveOrUpdate(dozerMapper.map(modelFieldDto, ModelField.class),
                        Wrappers.<ModelField>lambdaQuery()
                                .eq(ModelField::getName, modelFieldDto.getName())
                                .eq(ModelField::getEntityId, entityId)
                );
            }
        }
    }

    @Override
    public List<ModelFieldVo> queryModelFieldList(Long entityId) {
        return list(Wrappers.<ModelField>lambdaQuery().eq(ObjectUtil.isNotNull(entityId), ModelField::getEntityId, entityId)
                .orderByAsc(ModelField::getSort))
                .stream().map(x -> dozerMapper.map(x, ModelFieldVo.class)).collect(Collectors.toList());
    }

    @Override
    public boolean delModelFile(Long entityId) {
        return remove(Wrappers.<ModelField>lambdaQuery().eq(ModelField::getEntityId, entityId));
    }

    @Override
    public boolean deleteMismatchData(List<ModelFieldDto> list, Long entityId) {
        List<String> newDataList = list.stream().map(ModelFieldDto::getName).collect(Collectors.toList());
        List<ModelField> collect = list(Wrappers.<ModelField>lambdaQuery().eq(ModelField::getEntityId, entityId))
                .stream()
                .filter(field -> !newDataList.contains(field.getName()))
                .collect(Collectors.toList());
        return removeByIds(collect.stream().map(ModelField::getId).collect(Collectors.toList()));
    }
}
