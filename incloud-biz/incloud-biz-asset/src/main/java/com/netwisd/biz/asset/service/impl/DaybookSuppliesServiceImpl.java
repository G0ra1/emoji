package com.netwisd.biz.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.dozermapper.core.Mapper;
import com.netwisd.biz.asset.constants.DayBookType;
import com.netwisd.biz.asset.dto.AssetsDetailDto;
import com.netwisd.biz.asset.dto.DaybookSuppliesDto;
import com.netwisd.biz.asset.dto.SuppliesDetailDto;
import com.netwisd.biz.asset.entity.DaybookSupplies;
import com.netwisd.biz.asset.mapper.DaybookSuppliesMapper;
import com.netwisd.biz.asset.service.DaybookSuppliesService;
import com.netwisd.biz.asset.vo.DaybookSuppliesVo;
import com.netwisd.biz.asset.vo.DaybookVo;
import com.netwisd.common.core.util.DozerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
/**
 * @Description 耗材流水表 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-25 17:20:30
 */
@Service
@Slf4j
public class DaybookSuppliesServiceImpl extends ServiceImpl<DaybookSuppliesMapper, DaybookSupplies> implements DaybookSuppliesService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private DaybookSuppliesMapper daybookSuppliesMapper;

    /**
    * 单表简单查询操作
    * @param daybookSuppliesDto
    * @return
    */
    @Override
    public Page list(DaybookSuppliesDto daybookSuppliesDto) {
        LambdaQueryWrapper<DaybookSupplies> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<DaybookSupplies> page = daybookSuppliesMapper.selectPage(daybookSuppliesDto.getPage(),queryWrapper);
        Page<DaybookSuppliesVo> pageVo = DozerUtils.mapPage(dozerMapper, page, DaybookSuppliesVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param daybookSuppliesDto
    * @return
    */
    @Override
    public Page lists(DaybookSuppliesDto daybookSuppliesDto) {
        Page<DaybookSuppliesVo> pageVo = daybookSuppliesMapper.getPageList(daybookSuppliesDto.getPage(),daybookSuppliesDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public DaybookSuppliesVo get(Long id) {
        DaybookSupplies daybookSupplies = super.getById(id);
        DaybookSuppliesVo daybookSuppliesVo = null;
        if(daybookSupplies !=null){
            daybookSuppliesVo = dozerMapper.map(daybookSupplies,DaybookSuppliesVo.class);
        }
        log.debug("查询成功");
        return daybookSuppliesVo;
    }

    /**
    * 保存实体
    * @param daybookSuppliesDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(DaybookSuppliesDto daybookSuppliesDto) {
        DaybookSupplies daybookSupplies = dozerMapper.map(daybookSuppliesDto,DaybookSupplies.class);
        boolean result = super.save(daybookSupplies);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param daybookSuppliesDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(DaybookSuppliesDto daybookSuppliesDto) {
        daybookSuppliesDto.setUpdateTime(LocalDateTime.now());
        DaybookSupplies daybookSupplies = dozerMapper.map(daybookSuppliesDto,DaybookSupplies.class);
        boolean result = super.updateById(daybookSupplies);
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

    @Override
    public Boolean log(Long formId, String procId, SuppliesDetailDto suppliesDetailDto, DayBookType operation) {
        DaybookSuppliesDto daybookSuppliesDto = new DaybookSuppliesDto();
        daybookSuppliesDto.setFormId(formId);
        daybookSuppliesDto.setCamundaProcinsId(procId);
        daybookSuppliesDto.setAssetsId(suppliesDetailDto.getAssetsId());
        daybookSuppliesDto.setAssetsDetailId(suppliesDetailDto.getId());
        daybookSuppliesDto.setItemId(suppliesDetailDto.getItemId());
        daybookSuppliesDto.setItemCode(suppliesDetailDto.getItemCode());
        daybookSuppliesDto.setItemName(suppliesDetailDto.getItemName());
        daybookSuppliesDto.setType(String.valueOf(operation.code));
        return this.save(daybookSuppliesDto);
    }


    @Override
    public Page getByAssets(AssetsDetailDto assetsDetailDto) {
        LambdaQueryWrapper<DaybookSupplies> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(DaybookSupplies::getCreateTime);
        Page<DaybookSupplies> page = daybookSuppliesMapper.selectPage(assetsDetailDto.getPage(),queryWrapper);
        Page<DaybookVo> pageVo = DozerUtils.mapPage(dozerMapper, page, DaybookVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }
}
