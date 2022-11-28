package com.netwisd.base.model.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.model.entity.ModelTest;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.model.dto.ModelTestDto;
import com.netwisd.base.model.vo.ModelTestVo;
/**
 * @Description 数据建模测试表 功能描述...
 * @author 云数网讯 sunzhenxi@netwisd.com
 * @date 2021-12-21 10:21:27
 */
public interface ModelTestService extends IService<ModelTest> {
    Page list(ModelTestDto modelTestDto);
    Page lists(ModelTestDto modelTestDto);
    ModelTestVo get(Long id);
    Boolean save(ModelTestDto modelTestDto);
    Boolean update(ModelTestDto modelTestDto);
    Boolean delete(Long id);
}
