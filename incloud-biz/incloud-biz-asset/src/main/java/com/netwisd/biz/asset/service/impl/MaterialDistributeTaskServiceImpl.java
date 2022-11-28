package com.netwisd.biz.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.dozermapper.core.Mapper;
import com.netwisd.biz.asset.dto.MaterialDistributeTaskDto;
import com.netwisd.biz.asset.entity.MaterialDistributeTask;
import com.netwisd.biz.asset.mapper.MaterialDistributeTaskMapper;
import com.netwisd.biz.asset.service.MaterialDistributeTaskService;
import com.netwisd.biz.asset.vo.MaterialDistributeTaskVo;
import com.netwisd.common.core.util.DozerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
/**
 * @Description 资产派发任务表 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-17 16:55:47
 */
@Service
@Slf4j
public class MaterialDistributeTaskServiceImpl extends ServiceImpl<MaterialDistributeTaskMapper, MaterialDistributeTask> implements MaterialDistributeTaskService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MaterialDistributeTaskMapper materialDistributeTaskMapper;

    /**
    * 单表简单查询操作
    * @param materialDistributeTaskDto
    * @return
    */
    @Override
    public Page list(MaterialDistributeTaskDto materialDistributeTaskDto) {
        LambdaQueryWrapper<MaterialDistributeTask> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<MaterialDistributeTask> page = materialDistributeTaskMapper.selectPage(materialDistributeTaskDto.getPage(),queryWrapper);
        Page<MaterialDistributeTaskVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MaterialDistributeTaskVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param materialDistributeTaskDto
    * @return
    */
    @Override
    public Page lists(MaterialDistributeTaskDto materialDistributeTaskDto) {
        Page<MaterialDistributeTaskVo> pageVo = materialDistributeTaskMapper.getPageList(materialDistributeTaskDto.getPage(),materialDistributeTaskDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public MaterialDistributeTaskVo get(Long id) {
        MaterialDistributeTask materialDistributeTask = super.getById(id);
        MaterialDistributeTaskVo materialDistributeTaskVo = null;
        if(materialDistributeTask !=null){
            materialDistributeTaskVo = dozerMapper.map(materialDistributeTask,MaterialDistributeTaskVo.class);
        }
        log.debug("查询成功");
        return materialDistributeTaskVo;
    }

    /**
    * 保存实体
    * @param materialDistributeTaskDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(MaterialDistributeTaskDto materialDistributeTaskDto) {
        MaterialDistributeTask materialDistributeTask = dozerMapper.map(materialDistributeTaskDto,MaterialDistributeTask.class);
        boolean result = super.save(materialDistributeTask);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param materialDistributeTaskDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(MaterialDistributeTaskDto materialDistributeTaskDto) {
        materialDistributeTaskDto.setUpdateTime(LocalDateTime.now());
        MaterialDistributeTask materialDistributeTask = dozerMapper.map(materialDistributeTaskDto,MaterialDistributeTask.class);
        boolean result = super.updateById(materialDistributeTask);
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
