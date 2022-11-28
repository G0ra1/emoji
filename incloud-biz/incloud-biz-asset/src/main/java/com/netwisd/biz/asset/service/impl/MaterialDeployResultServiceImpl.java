package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.dozermapper.core.Mapper;
import com.netwisd.biz.asset.dto.MaterialDeployResultDto;
import com.netwisd.biz.asset.entity.MaterialDeployResult;
import com.netwisd.biz.asset.mapper.MaterialDeployResultMapper;
import com.netwisd.biz.asset.service.MaterialDeployResultService;
import com.netwisd.biz.asset.vo.MaterialDeployResultVo;
import com.netwisd.biz.asset.vo.MaterialDeployVo;
import com.netwisd.common.core.util.DozerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @Description 资产调配结果数据 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-09-08 11:05:25
 */
@Service
@Slf4j
public class MaterialDeployResultServiceImpl extends ServiceImpl<MaterialDeployResultMapper, MaterialDeployResult> implements MaterialDeployResultService {
    @Autowired
    private Mapper dozerMapper;

    //@Autowired
    //private MaterialDeployResultMapper materialDeployResultMapper;

    /**
    * 单表简单查询操作
    * @param materialDeployResultDto
    * @return
    */
    @Override
    public Page<MaterialDeployResultVo> page(MaterialDeployResultDto materialDeployResultDto) {
        LambdaQueryWrapper<MaterialDeployResult> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        Page<MaterialDeployResultVo> pageVo = DozerUtils.mapPage(dozerMapper, super.page(materialDeployResultDto.getPage(),queryWrapper), MaterialDeployResultVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param materialDeployResultDto
    * @return
    */
    @Override
    public List<MaterialDeployResultVo> list(MaterialDeployResultDto materialDeployResultDto) {
        LambdaQueryWrapper<MaterialDeployResult> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        List<MaterialDeployResultVo> materialDeployResultVos = DozerUtils.mapList(dozerMapper, super.list(queryWrapper), MaterialDeployResultVo.class);
        log.debug("查询条数:"+materialDeployResultVos.size());
        return materialDeployResultVos;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public MaterialDeployResultVo get(Long id) {
        MaterialDeployResult materialDeployResult = Optional.ofNullable(super.getById(id)).orElse(null);
        return ObjectUtil.isNotEmpty(materialDeployResult) ? dozerMapper.map(materialDeployResult,MaterialDeployResultVo.class) : null;
    }

    /**
    * 保存实体
    * @param materialDeployResultDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(MaterialDeployResultDto materialDeployResultDto) {
        return super.save(dozerMapper.map(materialDeployResultDto,MaterialDeployResult.class));
    }

    /**
    * 修改实体
    * @param materialDeployResultDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(MaterialDeployResultDto materialDeployResultDto) {
        materialDeployResultDto.setUpdateTime(LocalDateTime.now());
        return super.updateById(dozerMapper.map(materialDeployResultDto,MaterialDeployResult.class));
    }

    /**
    * 通过ID删除
    * @param id
    * @return
    */
    @Transactional
    @Override
    public Boolean delete(Long id) {
        return super.removeById(id);
    }

    @Override
    public Page<MaterialDeployResultVo> getResultByDeploy(MaterialDeployResultDto materialDeployResultDto) {
        LambdaQueryWrapper<MaterialDeployResult> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MaterialDeployResult::getDeployId,materialDeployResultDto.getDeployId());
        queryWrapper.gt(MaterialDeployResult::getNotDeliveryNumber, BigDecimal.ZERO);
        queryWrapper.gt(MaterialDeployResult::getNotDeliveryAmount, BigDecimal.ZERO);
        return DozerUtils.mapPage(dozerMapper, super.page(materialDeployResultDto.getPage(),queryWrapper), MaterialDeployVo.class);
    }
}