package com.netwisd.base.model.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.model.dto.ModelFormDto;
import com.netwisd.base.model.entity.ModelForm;
import com.netwisd.base.common.model.vo.ModelFormVo;

import java.io.IOException;

public interface ModelFormService extends IService<ModelForm> {

    Page queryModelFormPage(ModelFormDto modelFormDto);

    boolean saveModelForm(ModelFormDto modelFormDto);

    boolean upModelForm(ModelFormDto modelFormDto);

    boolean delModelForm(Long id);

    boolean bindButton(ModelFormDto modelFormDto);

    ModelFormVo getModelFormDetail(Long id);

    ModelFormVo getModelFormDetailByEasy(String id);

    ModelFormVo getModelFormDetailByFormName(String formName);

    byte[] download(Long id) throws IOException;
}