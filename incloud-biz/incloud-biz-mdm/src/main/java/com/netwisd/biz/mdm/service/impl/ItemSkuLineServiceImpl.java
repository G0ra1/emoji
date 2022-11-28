package com.netwisd.biz.mdm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.biz.mdm.entity.ItemSkuColumn;
import com.netwisd.biz.mdm.entity.ItemSkuLine;
import com.netwisd.biz.mdm.mapper.ItemSkuLineMapper;
import com.netwisd.biz.mdm.service.ItemSkuColumnService;
import com.netwisd.biz.mdm.service.ItemSkuLineService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.mdm.dto.ItemSkuLineDto;
import com.netwisd.biz.mdm.vo.ItemSkuLineVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
/**
 * @Description 物资sku行 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-05-25 19:52:49
 */
@Service
@Slf4j
public class ItemSkuLineServiceImpl extends ServiceImpl<ItemSkuLineMapper, ItemSkuLine> implements ItemSkuLineService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private ItemSkuLineMapper itemSkuLineMapper;
    @Autowired
    private ItemSkuColumnService columnService;

    /**
    * 单表简单查询操作
    * @param itemSkuLineDto
    * @return
    */
    @Override
    public Page list(ItemSkuLineDto itemSkuLineDto) {
        LambdaQueryWrapper<ItemSkuLine> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<ItemSkuLine> page = itemSkuLineMapper.selectPage(itemSkuLineDto.getPage(),queryWrapper);
        Page<ItemSkuLineVo> pageVo = DozerUtils.mapPage(dozerMapper, page, ItemSkuLineVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param itemSkuLineDto
    * @return
    */
    @Override
    public Page lists(ItemSkuLineDto itemSkuLineDto) {
        Page<ItemSkuLineVo> pageVo = itemSkuLineMapper.getPageList(itemSkuLineDto.getPage(),itemSkuLineDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public ItemSkuLineVo get(Long id) {
        ItemSkuLine itemSkuLine = super.getById(id);
        ItemSkuLineVo itemSkuLineVo = null;
        if(itemSkuLine !=null){
            itemSkuLineVo = dozerMapper.map(itemSkuLine,ItemSkuLineVo.class);
        }
        log.debug("查询成功");
        return itemSkuLineVo;
    }

    /**
    * 保存实体
    * @param itemSkuLineDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(ItemSkuLineDto itemSkuLineDto) {
        ItemSkuLine itemSkuLine = dozerMapper.map(itemSkuLineDto,ItemSkuLine.class);
        boolean result = super.save(itemSkuLine);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param itemSkuLineDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(ItemSkuLineDto itemSkuLineDto) {
        itemSkuLineDto.setUpdateTime(LocalDateTime.now());
        ItemSkuLine itemSkuLine = dozerMapper.map(itemSkuLineDto,ItemSkuLine.class);
        boolean result = super.updateById(itemSkuLine);
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
        columnService.remove(Wrappers.<ItemSkuColumn>lambdaQuery().eq(ItemSkuColumn::getLineId,id));
        if(result){
            log.debug("删除成功");
        }
        return result;
    }

}
