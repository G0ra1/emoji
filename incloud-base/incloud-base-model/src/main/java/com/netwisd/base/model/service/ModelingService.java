package com.netwisd.base.model.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.model.dto.ModelingDto;
import com.netwisd.base.model.entity.Modeling;
import com.netwisd.base.model.vo.ModelingEntityVo;
import com.netwisd.base.common.model.vo.ModelingFieldVo;
import com.netwisd.base.model.vo.ModelingVo;
import com.netwisd.common.code.entity.ModelConfig;

import java.util.List;

public interface ModelingService extends IService<Modeling> {

    List<ModelingFieldVo> queryEntityFieldList(Long entityId, Long modelingId);

    List<ModelingEntityVo> getEntityTree(Long entityId, Long modelingId, Integer modelProperty);

    Page queryModelingPage(ModelingDto modelingDto);

    ModelingVo saveModeling(ModelingDto modelingDto);

    ModelingVo upModeling(ModelingDto modelingDto);

    ModelConfig getModelConfig(Long id, String codeType);

    boolean delModeling(Long id);

    List<ModelingFieldVo> fieldLists(Long modelingId);

    List<ModelingVo> queryModelingList(ModelingDto modelingDto);

    ModelingVo getModelingDetail(Long id);
}