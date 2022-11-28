package com.netwisd.base.model.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.model.dto.ModelingDto;
import com.netwisd.base.model.dto.ModelingEntityDto;
import com.netwisd.base.model.entity.ModelingEntity;

import java.util.List;

public interface ModelingEntityService extends IService<ModelingEntity> {

    void saveModelingEntity(ModelingDto modeling, List<ModelingEntityDto> modelingEntityList);

    void upModelingEntity(List<ModelingEntityDto> list);
}