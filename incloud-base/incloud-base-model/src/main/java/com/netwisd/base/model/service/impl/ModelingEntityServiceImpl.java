package com.netwisd.base.model.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.model.dto.ModelingDto;
import com.netwisd.base.model.dto.ModelingEntityDto;
import com.netwisd.base.model.entity.ModelEntity;
import com.netwisd.base.model.entity.ModelingEntity;
import com.netwisd.base.model.mapper.ModelEntityMapper;
import com.netwisd.base.model.mapper.ModelingEntityMapper;
import com.netwisd.base.model.service.ModelingEntityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ModelingEntityServiceImpl extends ServiceImpl<ModelingEntityMapper, ModelingEntity> implements ModelingEntityService {

    private final ModelEntityMapper modelEntityMapper;

    @Autowired
    private Mapper dozerMapper;

    @Override
    @Transactional
    public void saveModelingEntity(ModelingDto modeling, List<ModelingEntityDto> modelingEntityList) {
        if (CollUtil.isEmpty(modelingEntityList)) {
            return;
        }
        for (ModelingEntityDto x : modelingEntityList) {
            x.setModelId(modeling.getId());
            x.setModelName(modeling.getModelName());
            x.setModelNameCh(modeling.getModelNameCh());
            x.setModelTypeId(modeling.getModelTypeId());
            x.setModelTypeCode(modeling.getModelTypeCode());
            x.setModelTypeName(modeling.getModelName());
            if (0L != x.getParentEntityId()) {//父级不用拼接对应的子表JSON使用key;
                ModelEntity modelEntity = modelEntityMapper.selectById(x.getEntityId());
                String childTableSetKey = StringUtils.uncapitalize(tableToJava(modelEntity.getTableName(), modelEntity.getTablePrefix()) + "List");
                x.setChildTableSetKey(childTableSetKey);
            }
            save(dozerMapper.map(x, ModelingEntity.class));
        }
    }

    @Override
    @Transactional
    public void upModelingEntity(List<ModelingEntityDto> list) {
        if (CollUtil.isEmpty(list)) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            updateById(dozerMapper.map(list.get(i), ModelingEntity.class));
        }
    }

    private String tableToJava(String tableName, String tablePrefix) {
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replaceFirst(tablePrefix, "");
        }
        return WordUtils.capitalizeFully(tableName, new char[]{'_'}).replace("_", "");
    }
}
