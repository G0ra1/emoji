package com.netwisd.biz.mdm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.biz.mdm.entity.ItemUnit;
import com.netwisd.biz.mdm.mapper.ItemUnitMapper;
import com.netwisd.biz.mdm.service.ItemUnitService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.mdm.dto.ItemUnitDto;
import com.netwisd.biz.mdm.vo.ItemUnitVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
/**
 * @Description 物资辅计量单位 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-05-31 20:23:36
 */
@Service
@Slf4j
public class ItemUnitServiceImpl extends ServiceImpl<ItemUnitMapper, ItemUnit> implements ItemUnitService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private ItemUnitMapper itemUnitMapper;

    /**
    * 单表简单查询操作
    * @param itemUnitDto
    * @return
    */
    @Override
    public Page list(ItemUnitDto itemUnitDto) {
        LambdaQueryWrapper<ItemUnit> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<ItemUnit> page = itemUnitMapper.selectPage(itemUnitDto.getPage(),queryWrapper);
        Page<ItemUnitVo> pageVo = DozerUtils.mapPage(dozerMapper, page, ItemUnitVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param itemUnitDto
    * @return
    */
    /* @Override
   public Page lists(ItemUnitDto itemUnitDto) {
        Page<ItemUnitVo> pageVo = itemUnitMapper.getPageList(itemUnitDto.getPage(),itemUnitDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }*/

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public ItemUnitVo get(Long id) {
        ItemUnit itemUnit = super.getById(id);
        ItemUnitVo itemUnitVo = null;
        if(itemUnit !=null){
            itemUnitVo = dozerMapper.map(itemUnit,ItemUnitVo.class);
        }
        log.debug("查询成功");
        return itemUnitVo;
    }

    /**
    * 保存实体
    * @param itemUnitDto
    * @return
    */
   /* @Transactional
    @Override
    public Boolean save(ItemUnitDto itemUnitDto) {
        ItemUnit itemUnit = dozerMapper.map(itemUnitDto,ItemUnit.class);
        boolean result = super.save(itemUnit);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }*/

    /**
    * 修改实体
    * @param itemUnitDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(ItemUnitDto itemUnitDto) {
        itemUnitDto.setUpdateTime(LocalDateTime.now());
        ItemUnit itemUnit = dozerMapper.map(itemUnitDto,ItemUnit.class);
        boolean result = super.updateById(itemUnit);
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
