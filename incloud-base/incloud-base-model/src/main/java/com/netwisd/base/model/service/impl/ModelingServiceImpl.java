package com.netwisd.base.model.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.dozermapper.core.Mapper;
import com.google.common.collect.Lists;
import com.netwisd.base.common.constants.ModelPropertyEnum;
import com.netwisd.base.model.dto.ModelingDto;
import com.netwisd.base.model.dto.ModelingEntityDto;
import com.netwisd.base.model.entity.*;
import com.netwisd.base.model.mapper.ModelEntityMapper;
import com.netwisd.base.model.mapper.ModelFieldMapper;
import com.netwisd.base.model.mapper.ModelingMapper;
import com.netwisd.base.model.service.ModelingEntityService;
import com.netwisd.base.model.service.ModelingFieldService;
import com.netwisd.base.model.service.ModelingService;
import com.netwisd.base.model.vo.ModelingEntityVo;
import com.netwisd.base.common.model.vo.ModelingFieldVo;
import com.netwisd.base.model.vo.ModelingVo;
import com.netwisd.common.code.entity.EntityConfig;
import com.netwisd.common.code.entity.FieldConfig;
import com.netwisd.common.code.entity.ModelConfig;
import com.netwisd.common.core.exception.IncloudException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@AllArgsConstructor
public class ModelingServiceImpl extends ServiceImpl<ModelingMapper, Modeling> implements ModelingService {

    @Autowired
    private Mapper dozerMapper;

    private final ModelingEntityService modelingEntityService;

    private final ModelingFieldService modelingFieldService;

    private final ModelEntityMapper modelEntityMapper;

    private final ModelFieldMapper modelFieldMapper;

    @Override
    public List<ModelingEntityVo> getEntityTree(Long entityId, Long modelingId, Integer modelProperty) {
        ModelEntity modelEntity = Optional.ofNullable(modelEntityMapper.selectById(entityId)).orElseThrow(() -> new IncloudException("????????????????????????"));
        List<ModelingEntityVo> allChildModelEntityList = new ArrayList<>();
        allChildModelEntityList.add(modelEntity.toModelingEntityVo(0L, 1));
        //?????????????????????????????????????????????????????????????????????????????????????????????????????????
        if (ModelPropertyEnum.SINGLE.getCode() != modelProperty) {
            this.getModelingChildTree(entityId, 2, allChildModelEntityList);
        }
        //???????????????????????????Id????????????
        if (ObjectUtil.isNotNull(modelingId)) {
            List<Long> modelingEntityIdList = modelingEntityService.list(Wrappers.<ModelingEntity>lambdaQuery()
                            .select(ModelingEntity::getEntityId).eq(ModelingEntity::getModelId, modelingId))
                    .stream()
                    .map(ModelingEntity::getEntityId)
                    .collect(Collectors.toList());
            for (ModelingEntityVo modelingEntityVo : allChildModelEntityList) {
                if (modelingEntityIdList.contains(modelingEntityVo.getEntityId())) {
                    modelingEntityVo.setIsSelect(Boolean.TRUE);
                }
            }
        } else {//????????????????????????????????????
            for (ModelingEntityVo modelingEntityVo : allChildModelEntityList) {
                modelingEntityVo.setIsSelect(Boolean.TRUE);
            }
        }
        //set???????????????????????????
        allChildModelEntityList.forEach(x -> x.setModelingFieldList(queryEntityFieldList(x.getEntityId(), modelingId)));
        return ModelPropertyEnum.SINGLE.getCode() == modelProperty
                ? allChildModelEntityList
                : this.handleTreeModeling(allChildModelEntityList);
    }

    @Override
    public List<ModelingFieldVo> queryEntityFieldList(Long entityId, Long modelingId) {
        //???????????????????????????????????????????????????????????????????????????????????????
        //1?????????????????????????????????????????????
        List<ModelingFieldVo> existFieldVoList = modelingFieldService.list(Wrappers.<ModelingField>lambdaQuery()
                        .eq(ModelingField::getEntityId, entityId).eq(ModelingField::getModelingId, modelingId))
                .stream().map(x -> {
                    ModelingFieldVo modelingFieldVo = dozerMapper.map(x, ModelingFieldVo.class);
                    modelingFieldVo.setIsSelect(Boolean.TRUE);
                    return modelingFieldVo;
                }).collect(Collectors.toList());
        List<String> existFieldList = existFieldVoList.stream().map(ModelingFieldVo::getName).collect(Collectors.toList());
        //2?????????????????????????????????????????????????????????
        List<ModelingFieldVo> noExistFieldList = modelFieldMapper.selectList(Wrappers.<ModelField>lambdaQuery().eq(ModelField::getEntityId, entityId)
                        .notIn(CollectionUtil.isNotEmpty(existFieldList), ModelField::getName, existFieldList))
                .stream().map(x -> {
                    ModelingFieldVo modelingFieldVo = dozerMapper.map(x, ModelingFieldVo.class);
                    modelingFieldVo.setEntityFieldId(x.getId());
                    modelingFieldVo.setEntityId(x.getEntityId());
                    //????????????????????????????????????????????????modelingId?????????
                    modelingFieldVo.setIsSelect(ObjectUtil.isNull(modelingId) ? true : false);
                    modelingFieldVo.setJavaName(StrUtil.toCamelCase(modelingFieldVo.getName()));
                    return modelingFieldVo;
                }).collect(Collectors.toList());
        return Stream.concat(existFieldVoList.stream(), noExistFieldList.stream()).map(x -> {
            x.setId(null);
            return x;
        }).collect(Collectors.toList());
    }

    @Override
    public Page queryModelingPage(ModelingDto modelingDto) {
        return page(modelingDto.getPage(), Wrappers.<Modeling>lambdaQuery()
                .eq(ObjectUtil.isNotNull(modelingDto.getModelTypeId()), Modeling::getModelTypeId, modelingDto.getModelTypeId())
                .like(ObjectUtil.isNotNull(modelingDto.getModelName()), Modeling::getModelName, modelingDto.getModelName())
                .like(StrUtil.isNotEmpty(modelingDto.getModelNameCh()), Modeling::getModelNameCh, modelingDto.getModelNameCh())
                .like(StrUtil.isNotEmpty(modelingDto.getEntityNameCh()), Modeling::getEntityNameCh, modelingDto.getEntityNameCh())
                .eq(ObjectUtil.isNotEmpty(modelingDto.getModelProperty()), Modeling::getModelProperty, modelingDto.getModelProperty())
                .orderByDesc(Modeling::getCreateTime));
    }

    @Override
    @Transactional
    public ModelingVo saveModeling(ModelingDto modelingDto) {
        this.validate(modelingDto);
        //??????????????????
        modelingEntityService.saveModelingEntity(modelingDto, modelingDto.getModelingEntityList());
        //????????????????????????
        modelingFieldService.saveModelingField(modelingDto.getId(), modelingDto.getModelingEntityList());
        //????????????????????????????????????
        Modeling modeling = dozerMapper.map(modelingDto, Modeling.class);
        save(modeling);
        return modeling.toModelingVo();
    }

    @Override
    @Transactional
    public ModelingVo upModeling(ModelingDto modelingDto) {
        Optional.ofNullable(getById(modelingDto.getId())).orElseThrow(() -> new IncloudException("????????????????????????"));
        this.validate(modelingDto);
        //?????????????????????????????????????????????????????????
        modelingEntityService.remove(Wrappers.<ModelingEntity>lambdaQuery().eq(ModelingEntity::getModelId, modelingDto.getId()));
        modelingFieldService.remove(Wrappers.<ModelingField>lambdaQuery().eq(ModelingField::getModelingId, modelingDto.getId()));
        //??????????????????
        modelingEntityService.saveModelingEntity(modelingDto, modelingDto.getModelingEntityList());
        //????????????????????????
        modelingFieldService.saveModelingField(modelingDto.getId(), modelingDto.getModelingEntityList());
        Modeling modeling = dozerMapper.map(modelingDto, Modeling.class);
        updateById(modeling);
        return modeling.toModelingVo();
    }

    @Override
    public ModelConfig getModelConfig(Long id, String codeType) {
        Modeling modeling = Optional.ofNullable(getById(id)).orElseThrow(() -> new IncloudException("????????????????????????"));

        ModelConfig modelConfig = new ModelConfig();
        //????????????????????????
        if (modeling.getModelProperty() == ModelPropertyEnum.SINGLE.getCode()) {
            modelConfig.setModelPropertyEnum(com.netwisd.common.code.constants.ModelPropertyEnum.SINGLE);
            String templatePacket = codeType.equals("wf") ? "single-wf" : "single";
            modelConfig.setTemplatePacket(templatePacket);
        } else if (modeling.getModelProperty() == ModelPropertyEnum.MASTER_SLAVER.getCode()) {
            modelConfig.setModelPropertyEnum(com.netwisd.common.code.constants.ModelPropertyEnum.MASTER_SLAVER);
            String templatePacket = codeType.equals("wf") ? "multi-wf" : "multi";
            modelConfig.setTemplatePacket(templatePacket);
        } else {
            modelConfig.setModelPropertyEnum(com.netwisd.common.code.constants.ModelPropertyEnum.ASSOCIATION);
            String templatePacket = codeType.equals("wf") ? "multi-wf" : "multi";
            modelConfig.setTemplatePacket(templatePacket);
        }

        //?????????????????????
        EntityConfig entityConfig = this.getEntityConfig(modeling.getEntityId(), id);

        //????????????????????????
        if (ObjectUtil.isNotNull(entityConfig) && modeling.getModelProperty() != ModelPropertyEnum.SINGLE.getCode()) {
            List<ModelingEntity> allChildList = new ArrayList<>();
            this.getChildTree(modeling.getEntityId(), id, allChildList);
            entityConfig.setEntityConfigList(handleChild(entityConfig.getTableName(), id, allChildList));
        }
        modelConfig.setEntityConfig(entityConfig);
        return modelConfig;
    }

    @Override
    @Transactional
    public boolean delModeling(Long id) {
        modelingEntityService.remove(Wrappers.<ModelingEntity>lambdaQuery().eq(ModelingEntity::getModelId, id));
        modelingFieldService.remove(Wrappers.<ModelingField>lambdaQuery().eq(ModelingField::getModelingId, id));
        return removeById(id);
    }

    @Override
    public List<ModelingFieldVo> fieldLists(Long modelingId) {
        return modelingFieldService.list(Wrappers.<ModelingField>lambdaQuery().eq(ModelingField::getModelingId, modelingId))
                .stream()
                .map(x -> dozerMapper.map(x, ModelingFieldVo.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ModelingVo> queryModelingList(ModelingDto modelingDto) {
        return list(Wrappers.<Modeling>lambdaQuery()
                .eq(ObjectUtil.isNotNull(modelingDto.getModelTypeId()), Modeling::getModelTypeId, modelingDto.getModelTypeId())
                .like(ObjectUtil.isNotNull(modelingDto.getModelName()), Modeling::getModelName, modelingDto.getModelName())
                .like(StrUtil.isNotEmpty(modelingDto.getModelNameCh()), Modeling::getModelNameCh, modelingDto.getModelNameCh())
                .like(StrUtil.isNotEmpty(modelingDto.getEntityNameCh()), Modeling::getEntityNameCh, modelingDto.getEntityNameCh())
                .eq(ObjectUtil.isNotEmpty(modelingDto.getModelProperty()), Modeling::getModelProperty, modelingDto.getModelProperty())
                .orderByDesc(Modeling::getCreateTime))
                .stream()
                .map(x -> dozerMapper.map(x, ModelingVo.class))
                .collect(Collectors.toList());
    }

    @Override
    public ModelingVo getModelingDetail(Long id) {
        Modeling modeling = Optional.ofNullable(getById(id)).orElseThrow(() -> new IncloudException("????????????????????????"));
        ModelingVo modelingVo = dozerMapper.map(modeling, ModelingVo.class);
        //????????????
        modelingVo.setModelingFieldList(fieldLists(id));
        return modelingVo;
    }

    private List<ModelingEntityVo> getModelingChildTree(Long parentId, int level, List<ModelingEntityVo> allChildList) {
        List<Long> childIdList = modelFieldMapper.selectList(Wrappers.<ModelField>lambdaQuery().select(ModelField::getEntityId).eq(ModelField::getFkTableId, parentId))
                .stream()
                .map(ModelField::getEntityId)
                .distinct()
                .collect(Collectors.toList());
        for (Long entityId : childIdList) {
            ModelEntity modelEntity = modelEntityMapper.selectById(entityId);
            allChildList.add(modelEntity.toModelingEntityVo(parentId, level));
            level++;
            getModelingChildTree(entityId, level, allChildList);
        }
        return allChildList;
    }

    private List<ModelingEntityVo> handleTreeModeling(List<ModelingEntityVo> childlist) {
        //??????????????????
        List<ModelingEntityVo> allChildList = childlist.stream().sorted(Comparator.comparing(ModelingEntityVo::getLevel).reversed()).collect(Collectors.toList());
        Map<Long, ModelingEntityVo> groupMap = allChildList.stream().collect(Collectors.toMap(ModelingEntityVo::getEntityId, Function.identity(), (key1, key2) -> key2));
        for (ModelingEntityVo modelingEntityVo : allChildList) {
            Long parentId = modelingEntityVo.getParentEntityId();
            ModelingEntityVo parentObj = groupMap.get(parentId);
            //?????????map??????????????????????????????????????????list???????????????
            if (ObjectUtil.isNotEmpty(parentObj)) {
                //???????????????????????????????????????getKids???add???????????????????????????????????????
                parentObj.getModelingEntityList().add(modelingEntityVo);
                //???map???????????????????????????????????????????????????????????????????????????level??????????????????????????????????????????????????????
                groupMap.remove(modelingEntityVo.getEntityId());
            }
        }
        //??????????????????map??????????????????????????????
        List<ModelingEntityVo> kids = Lists.newArrayList();
        for (Map.Entry<Long, ModelingEntityVo> entry : groupMap.entrySet()) {
            kids.add(entry.getValue());
        }
        return kids;
    }

    private List<ModelingEntity> getChildTree(Long parentId, Long modelId, List<ModelingEntity> allChildList) {
        List<ModelingEntity> childIdList = modelingEntityService.list(Wrappers.<ModelingEntity>lambdaQuery()
                .eq(ModelingEntity::getParentEntityId, parentId).eq(ModelingEntity::getModelId, modelId));
        for (ModelingEntity childModeingEntity : childIdList) {
            ModelingEntity modelingEntity = modelingEntityService.getById(childModeingEntity.getId());
            allChildList.add(modelingEntity);
            getChildTree(childModeingEntity.getEntityId(), modelId, allChildList);
        }
        return allChildList;
    }

    private List<EntityConfig> handleChild(String tableName, Long modelId, List<ModelingEntity> childlist) {
        List<ModelingEntity> allChildList = CollectionUtil.reverse(childlist);
        Map<Long, ModelingEntity> groupMap = allChildList.stream().collect(Collectors.toMap(ModelingEntity::getEntityId, Function.identity(), (key1, key2) -> key2));
        Map<String, EntityConfig> entityConfigMap = new HashMap<>();
        for (ModelingEntity modelingEntity : childlist) {
            Long parentId = modelingEntity.getParentEntityId();
            //???????????????
            EntityConfig dbParentEntityConfig = this.getEntityConfig(parentId, modelId);
            //???????????????parentEntityConfig.getEntityConfigList().add(childEntityConfig);???????????????????????????
            EntityConfig parentEntityConfig = entityConfigMap.getOrDefault(dbParentEntityConfig.getTableName(), dbParentEntityConfig);
            //?????????map??????????????????????????????????????????list???????????????
            if (ObjectUtil.isNotNull(parentEntityConfig)) {
                //???????????????????????????????????????
                EntityConfig childEntityConfig = this.getEntityConfig(modelingEntity.getEntityId(), modelId);
                parentEntityConfig.getEntityConfigList().add(childEntityConfig);
                //???map???????????????????????????????????????????????????????????????????????????level??????????????????????????????????????????????????????
                groupMap.remove(modelingEntity.getEntityId());
            }
            entityConfigMap.put(parentEntityConfig.getTableName(), parentEntityConfig);
        }
        //??????????????????map??????????????????????????????
        Set<EntityConfig> kids = new HashSet<>();
        for (Map.Entry<String, EntityConfig> entry : entityConfigMap.entrySet()) {
            kids.addAll(entry.getValue().getEntityConfigList());
        }
        return kids.stream().collect(Collectors.toList());
    }

    private EntityConfig getEntityConfig(Long entityId, Long modelingId) {
        ModelEntity modelEntity = modelEntityMapper.selectById(entityId);
        if (ObjectUtil.isEmpty(modelEntity)) {
            return null;
        }
        //?????????????????????
        EntityConfig entityConfig = modelEntity.toEntityConfig();
        //??????????????????????????????
        List<FieldConfig> fieldConfigList = modelingFieldService.list(Wrappers.<ModelingField>lambdaQuery()
                        .eq(ModelingField::getModelingId, modelingId)
                        .eq(ModelingField::getEntityId, modelEntity.getId()))
                .stream()
                .map(ModelingField::toFieldConfigVo)
                .collect(Collectors.toList());
        entityConfig.setFieldConfigList(fieldConfigList);
        return entityConfig;
    }

    private void validate(ModelingDto modelingDto) {
        for (ModelingEntityDto modelingEntityDto : modelingDto.getModelingEntityList()) {
            if (CollectionUtil.isEmpty(modelingEntityDto.getModelingFieldList())) {
                throw new IncloudException("{}?????????????????????", modelingDto.getEntityName());
            }
        }
    }
}
