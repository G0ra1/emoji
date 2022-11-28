package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.dozermapper.core.Mapper;
import com.netwisd.biz.asset.constants.DayBookType;
import com.netwisd.biz.asset.constants.ItemTypeEnum;
import com.netwisd.biz.asset.dto.AssetsDetailDto;
import com.netwisd.biz.asset.dto.DaybookDto;
import com.netwisd.biz.asset.entity.Daybook;
import com.netwisd.biz.asset.mapper.DaybookMapper;
import com.netwisd.biz.asset.service.DaybookService;
import com.netwisd.biz.asset.service.DaybookSuppliesService;
import com.netwisd.biz.asset.vo.DaybookVo;
import com.netwisd.common.core.util.DozerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
/**
 * @Description 资产流水表 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-17 17:19:59
 */
@Service
@Slf4j
public class DaybookServiceImpl extends ServiceImpl<DaybookMapper, Daybook> implements DaybookService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private DaybookMapper daybookMapper;

    @Autowired
    private DaybookSuppliesService daybookSuppliesService;

    /**
    * 单表简单查询操作
    * @param daybookDto
    * @return
    */
    @Override
    public Page list(DaybookDto daybookDto) {
        LambdaQueryWrapper<Daybook> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<Daybook> page = daybookMapper.selectPage(daybookDto.getPage(),queryWrapper);
        Page<DaybookVo> pageVo = DozerUtils.mapPage(dozerMapper, page, DaybookVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param daybookDto
    * @return
    */
    @Override
    public Page lists(DaybookDto daybookDto) {
        Page<DaybookVo> pageVo = daybookMapper.getPageList(daybookDto.getPage(),daybookDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public DaybookVo get(Long id) {
        Daybook daybook = super.getById(id);
        DaybookVo daybookVo = null;
        if(daybook !=null){
            daybookVo = dozerMapper.map(daybook,DaybookVo.class);
        }
        log.debug("查询成功");
        return daybookVo;
    }

    /**
    * 保存实体
    * @param daybookDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(DaybookDto daybookDto) {
        Daybook daybook = dozerMapper.map(daybookDto,Daybook.class);
        boolean result = super.save(daybook);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param daybookDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(DaybookDto daybookDto) {
        daybookDto.setUpdateTime(LocalDateTime.now());
        Daybook daybook = dozerMapper.map(daybookDto,Daybook.class);
        boolean result = super.updateById(daybook);
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
    public Boolean log(Long formId, String procId, AssetsDetailDto assetsDetailDto, DayBookType operation) {
        DaybookDto daybookDto = new DaybookDto();
        daybookDto.setFormId(formId);
        daybookDto.setCamundaProcinsId(procId);
        daybookDto.setAssetsId(assetsDetailDto.getAssetsId());
        daybookDto.setAssetsDetailId(assetsDetailDto.getId());
        daybookDto.setItemId(assetsDetailDto.getItemId());
        daybookDto.setItemCode(assetsDetailDto.getItemCode());
        daybookDto.setItemName(assetsDetailDto.getItemName());
        daybookDto.setType(operation.code);
        return this.save(daybookDto);
    }

    @Override
    public Page getByAssets(AssetsDetailDto assetsDetailDto) {

        Page aa = assetsDetailDto.getPage();
        //根据实际业务构建具体的参数做查询

        String itemType = assetsDetailDto.getItemType();
        if (ObjectUtil.isNotEmpty(itemType)){

            if(itemType.equals(ItemTypeEnum.ASSET.getCode()) || itemType.equals(ItemTypeEnum.INVENTORY.getCode())){
                LambdaQueryWrapper<Daybook> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.orderByDesc(Daybook::getCreateTime);
                Page<Daybook> page = daybookMapper.selectPage(assetsDetailDto.getPage(),queryWrapper);
                Page<DaybookVo> pageVo = DozerUtils.mapPage(dozerMapper, page, DaybookVo.class);
                log.debug("查询条数:"+pageVo.getTotal());
                return pageVo;
            }

            if(itemType.equals(ItemTypeEnum.ARTICLES.getCode()) || itemType.equals(ItemTypeEnum.MATERIAL.getCode())){
                return daybookSuppliesService.getByAssets(assetsDetailDto);
            }
        }

        return aa;
    }
}
