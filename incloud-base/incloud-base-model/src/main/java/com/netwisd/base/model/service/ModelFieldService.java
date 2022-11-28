package com.netwisd.base.model.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.model.dto.ModelFieldDto;
import com.netwisd.base.model.entity.ModelField;
import com.netwisd.base.model.vo.ModelFieldVo;

import java.util.List;

public interface ModelFieldService extends IService<ModelField> {

    void saveModelFiled(Long entityId, String entityName, List<ModelFieldDto> list);

    void updateModelFiled(Long entityId, String entityName, List<ModelFieldDto> list);

    List<ModelFieldVo> queryModelFieldList(Long entityId);

    boolean delModelFile(Long entityId);

    boolean deleteMismatchData(List<ModelFieldDto> list, Long entityId);
}