package com.netwisd.base.model.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.model.dto.ModelingEntityDto;
import com.netwisd.base.model.entity.ModelingField;
import com.netwisd.base.common.model.vo.ModelingFieldVo;

import java.util.List;

public interface ModelingFieldService extends IService<ModelingField> {

    void saveModelingField(Long modelingId, List<ModelingEntityDto> modelingEntityList);

    List<ModelingFieldVo> queryModelingFieldList(Long modelingId);
}