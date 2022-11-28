package com.netwisd.base.model.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.model.dto.ModelFormButtonDto;
import com.netwisd.base.model.dto.ModelFormDto;
import com.netwisd.base.model.entity.ModelFormButton;
import com.netwisd.base.model.mapper.ModelFormButtonMapper;
import com.netwisd.base.model.service.ModelFormButtonService;
import com.netwisd.base.common.model.vo.ModelFormButtonVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class ModelFormButtonServiceImpl extends ServiceImpl<ModelFormButtonMapper, ModelFormButton> implements ModelFormButtonService {

    @Autowired
    private Mapper dozerMapper;

    @Override
    public boolean delModelFormButton(Long modelFormId) {
        return remove(Wrappers.<ModelFormButton>lambdaQuery().eq(ModelFormButton::getModelFormId, modelFormId));
    }

    @Override
    public void saveModelFormButton(ModelFormDto modelFormDto, List<ModelFormButtonDto> buttonDtoList) {
        if (CollUtil.isEmpty(buttonDtoList)) {
            return;
        }
        for (ModelFormButtonDto modelFormButtonDto : buttonDtoList) {
            modelFormButtonDto.setModelFormId(modelFormDto.getId());
            modelFormButtonDto.setModelFormName(modelFormDto.getFormName());
            modelFormButtonDto.setModelFormNameCh(modelFormDto.getFormNameCh());
            save(dozerMapper.map(modelFormButtonDto, ModelFormButton.class));
        }
    }

    @Override
    public List<ModelFormButtonVo> queryModelFormButtonList(Long modelFormId) {
        return list(Wrappers.<ModelFormButton>lambdaQuery().eq(ModelFormButton::getModelFormId, modelFormId))
                .stream()
                .map(x -> dozerMapper.map(x, ModelFormButtonVo.class))
                .collect(Collectors.toList());
    }
}
