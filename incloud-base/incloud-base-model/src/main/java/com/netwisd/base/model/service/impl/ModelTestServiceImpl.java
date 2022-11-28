package com.netwisd.base.model.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.model.entity.ModelTest;
import com.netwisd.base.model.mapper.ModelTestMapper;
import com.netwisd.base.model.service.ModelTestService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.model.dto.ModelTestDto;
import com.netwisd.base.model.vo.ModelTestVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
/**
 * @Description 数据建模测试表 功能描述...
 * @author 云数网讯 sunzhenxi@netwisd.com
 * @date 2021-12-21 10:21:27
 */
@Service
@Slf4j
public class ModelTestServiceImpl extends ServiceImpl<ModelTestMapper, ModelTest> implements ModelTestService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private ModelTestMapper modelTestMapper;

    /**
    * 单表简单查询操作
    * @param modelTestDto
    * @return
    */
    @Override
    public Page list(ModelTestDto modelTestDto) {
        LambdaQueryWrapper<ModelTest> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<ModelTest> page = modelTestMapper.selectPage(modelTestDto.getPage(),queryWrapper);
        Page<ModelTestVo> pageVo = DozerUtils.mapPage(dozerMapper, page, ModelTestVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param modelTestDto
    * @return
    */
    @Override
    public Page lists(ModelTestDto modelTestDto) {
        Page<ModelTestVo> pageVo = modelTestMapper.getPageList(modelTestDto.getPage(),modelTestDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public ModelTestVo get(Long id) {
        ModelTest modelTest = super.getById(id);
        ModelTestVo modelTestVo = null;
        if(modelTest !=null){
            modelTestVo = dozerMapper.map(modelTest,ModelTestVo.class);
        }
        log.debug("查询成功");
        return modelTestVo;
    }

    /**
    * 保存实体
    * @param modelTestDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(ModelTestDto modelTestDto) {
        ModelTest modelTest = dozerMapper.map(modelTestDto,ModelTest.class);
        boolean result = super.save(modelTest);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param modelTestDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(ModelTestDto modelTestDto) {
        modelTestDto.setUpdateTime(LocalDateTime.now());
        ModelTest modelTest = dozerMapper.map(modelTestDto,ModelTest.class);
        boolean result = super.updateById(modelTest);
        if(result){
            log.debug("修改成功");
        }
        return result;
    }

    /**
    * 通过ID删除
    * @param id
    * @return
    */
    @Transactional
    @Override
    public Boolean delete(Long id) {
        boolean result = super.removeById(id);
        if(result){
            log.debug("删除成功");
        }
        return result;
    }
}
