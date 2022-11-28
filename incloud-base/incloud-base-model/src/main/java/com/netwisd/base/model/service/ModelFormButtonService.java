package com.netwisd.base.model.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.model.dto.ModelFormButtonDto;
import com.netwisd.base.model.dto.ModelFormDto;
import com.netwisd.base.model.entity.ModelFormButton;
import com.netwisd.base.common.model.vo.ModelFormButtonVo;

import java.util.List;

public interface ModelFormButtonService extends IService<ModelFormButton> {

    boolean delModelFormButton(Long modelFormId);

    void saveModelFormButton(ModelFormDto modelFormDto, List<ModelFormButtonDto> buttonDtoList);

    List<ModelFormButtonVo> queryModelFormButtonList(Long modelFormId);
}