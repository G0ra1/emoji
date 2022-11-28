package com.netwisd.biz.mdm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.biz.mdm.entity.ItemSkuColumn;
import com.netwisd.biz.mdm.mapper.ItemSkuColumnMapper;
import com.netwisd.biz.mdm.service.ItemSkuColumnService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.mdm.dto.ItemSkuColumnDto;
import com.netwisd.biz.mdm.vo.ItemSkuColumnVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
/**
 * @Description 物资sku列 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-05-25 19:54:39
 */
@Service
@Slf4j
public class ItemSkuColumnServiceImpl extends ServiceImpl<ItemSkuColumnMapper, ItemSkuColumn> implements ItemSkuColumnService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private ItemSkuColumnMapper itemSkuColumnMapper;

    /**
    * 单表简单查询操作
    * @param itemSkuColumnDto
    * @return
    */
    @Override
    public Page list(ItemSkuColumnDto itemSkuColumnDto) {
        LambdaQueryWrapper<ItemSkuColumn> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<ItemSkuColumn> page = itemSkuColumnMapper.selectPage(itemSkuColumnDto.getPage(),queryWrapper);
        Page<ItemSkuColumnVo> pageVo = DozerUtils.mapPage(dozerMapper, page, ItemSkuColumnVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param itemSkuColumnDto
    * @return
    */
    @Override
    public Page lists(ItemSkuColumnDto itemSkuColumnDto) {
        Page<ItemSkuColumnVo> pageVo = itemSkuColumnMapper.getPageList(itemSkuColumnDto.getPage(),itemSkuColumnDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public ItemSkuColumnVo get(Long id) {
        ItemSkuColumn itemSkuColumn = super.getById(id);
        ItemSkuColumnVo itemSkuColumnVo = null;
        if(itemSkuColumn !=null){
            itemSkuColumnVo = dozerMapper.map(itemSkuColumn,ItemSkuColumnVo.class);
        }
        log.debug("查询成功");
        return itemSkuColumnVo;
    }

    /**
    * 保存实体
    * @param itemSkuColumnDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(ItemSkuColumnDto itemSkuColumnDto) {
        ItemSkuColumn itemSkuColumn = dozerMapper.map(itemSkuColumnDto,ItemSkuColumn.class);
        boolean result = super.save(itemSkuColumn);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param itemSkuColumnDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(ItemSkuColumnDto itemSkuColumnDto) {
        itemSkuColumnDto.setUpdateTime(LocalDateTime.now());
        ItemSkuColumn itemSkuColumn = dozerMapper.map(itemSkuColumnDto,ItemSkuColumn.class);
        boolean result = super.updateById(itemSkuColumn);
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
