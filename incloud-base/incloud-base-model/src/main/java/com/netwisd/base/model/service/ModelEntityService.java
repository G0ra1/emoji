package com.netwisd.base.model.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.model.dto.ModelEntityDto;
import com.netwisd.base.model.entity.ModelEntity;
import com.netwisd.base.model.vo.ModelEntityVo;
import com.netwisd.base.model.vo.ModelFieldVo;

import java.util.List;

public interface ModelEntityService extends IService<ModelEntity> {

    Page queryModelEntityPage(ModelEntityDto modelEntityDto);

    boolean saveModelEntity(ModelEntityDto modelEntityDto);

    boolean upModelEntity(ModelEntityDto modelEntityDto);

    ModelEntityVo getModelEntity(Long id);

    boolean delModelEntity(Long id);

    List<ModelFieldVo> queryFieldList(String entityId);
}